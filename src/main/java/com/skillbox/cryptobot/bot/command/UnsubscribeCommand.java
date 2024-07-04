package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.model.dto.SubscribersDTO;
import com.skillbox.cryptobot.service.SubscribersService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;

/**
 * Обработка команды отмены подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class UnsubscribeCommand implements IBotCommand {

    private final SubscribersService subscribersService;

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        String answerText = "Подписка отменена";
        try {
            unsubscribe(message.getChatId());
        } catch (NotFoundException e) {
            answerText = e.getMessage();
        }
        answer.setText(answerText);

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Ошибка возникла в /get_subscription методе", e);
        }
    }

    private void unsubscribe(Long id) throws NotFoundException {
        SubscribersDTO subscribers = getSubscribers(id);
        subscribers.setCost(null);
        subscribersService.saveSubscriber(subscribers);
    }

    private SubscribersDTO getSubscribers(Long id) throws NotFoundException {
        SubscribersDTO subscribers = subscribersService.getSubscriberByTelegramId(id);
        BigDecimal decimal = subscribers.getCost();
        if (decimal == null) {
            throw new NotFoundException("Активные подписки отсутствуют");
        }
        return subscribers;
    }
}