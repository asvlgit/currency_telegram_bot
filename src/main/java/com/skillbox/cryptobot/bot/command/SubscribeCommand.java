package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.model.dto.SubscribersDTO;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscribersService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class SubscribeCommand implements IBotCommand {

    private final SubscribersService subscribersService;
    private final CryptoCurrencyService service;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        try {
            answer.setText("Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD");
            absSender.execute(answer);
        } catch (Exception e) {
            log.error("Ошибка возникла в /subscribe методе", e);
        }

        try {
            BigDecimal cost = getCost(arguments);
            subscribe(message.getChatId(), cost);
            answer.setText("Новая подписка создана на стоимость " + TextUtil.toString(cost.doubleValue()) + " USD");
            absSender.execute(answer);
        } catch (Exception ex) {
            try {
                answer.setText("Некорректный ввод команды");
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                log.error("Ошибка возникла в /subscribe методе", e);
            }
        }

    }

    private BigDecimal getCost(String[] arguments) throws IllegalArgumentException {
        AtomicReference<BigDecimal> cost =
                new AtomicReference<>(TextUtil.toDecimal(arguments[0].replace(",", ".")));
        return cost.get();
    }

    private void subscribe(Long id, BigDecimal cost) {
        if(cost.compareTo(BigDecimal.ZERO) > 0) {
            SubscribersDTO subscribers = subscribersService.getSubscriberByTelegramId(id);
            subscribers.setCost(cost);
            subscribersService.saveSubscriber(subscribers);
        }
    }
}