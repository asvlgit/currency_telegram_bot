package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.model.dto.SubscribersDTO;
import com.skillbox.cryptobot.service.SubscribersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {

    private final SubscribersService subscribersService;

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        String answerText = generateMessage(subscribersService.getSubscriberByTelegramId(message.getChatId()));
        answer.setText(answerText);

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Ошибка возникла в /get_subscription методе", e);
        }
    }

    private String generateMessage(SubscribersDTO subscribersDTO){
        BigDecimal decimal = subscribersDTO.getCost();
        String text;
        if(decimal == null){
            text = "Активные подписки отсутствуют";
        } else {
            text = "Вы подписаны на стоимость биткоина " + decimal + " USD";
        }
        return text;
    }
}