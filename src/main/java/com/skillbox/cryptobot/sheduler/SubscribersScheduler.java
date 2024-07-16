package com.skillbox.cryptobot.sheduler;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.model.dto.SubscribersDTO;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.NotificationService;
import com.skillbox.cryptobot.service.SubscribersService;
import com.skillbox.cryptobot.utils.NotifyProperties;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableScheduling
public class SubscribersScheduler {

    private final CryptoCurrencyService cryptoCurrencyService;
    private final SubscribersService subscribersService;
    private final NotificationService notificationService;
    private final NotifyProperties notifyProperties;
    private final CryptoBot cryptoBot;


    @Scheduled(fixedDelayString = "${binance.update.frequency}")
    public void loadPrice() {

        try {
            double currentBitcoinPrice = cryptoCurrencyService.getBitcoinPrice();

            List<SubscribersDTO> subscribers =
                    subscribersService.getSubscribersByCostGreaterThan(currentBitcoinPrice);

            long delay = notifyProperties.getDelay();

            subscribers.forEach(subscribersDTO -> {

                if (subscribersDTO.getLastSend() == null ||
                        Duration.between(subscribersDTO.getLastSend(), LocalDateTime.now()).toSeconds() > delay) {
                    sendNotification(subscribersDTO, currentBitcoinPrice);
                }

            });

        } catch (IOException e) {
            log.error("Ошибка возникла при отправке уведомления", e);
        }
    }

    private void sendNotification(SubscribersDTO subscribersDTO, double currentCost) {

        SendMessage answer = new SendMessage();
        answer.setChatId(subscribersDTO.getTelegramId());
        answer.setText("Пора покупать, стоимость биткоина " + TextUtil.toString(currentCost));

        try {
            cryptoBot.execute(answer);
            notificationService.saveNotification(subscribersDTO.getId(), LocalDateTime.now());
        } catch (TelegramApiException e) {
            log.error("Error occurred in notify command", e);
        }

    }
}
