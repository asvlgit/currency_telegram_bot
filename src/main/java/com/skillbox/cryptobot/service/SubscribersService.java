package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.model.dto.Mapper;
import com.skillbox.cryptobot.model.dto.SubscribersDTO;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SubscribersService {

    private final SubscribersRepository subscribersRepository;

    public void createSubscriber(Long telegramId) {
        Subscribers subscribers = new Subscribers();
        subscribers.setTelegramId(telegramId);
        subscribersRepository.save(subscribers);
    }

    public SubscribersDTO saveSubscriber(SubscribersDTO subscribersDTO) {
        Subscribers subscribers = subscribersRepository.save(Mapper.toSubscribers(subscribersDTO));
        return Mapper.toSubscribersDTO(subscribers);
    }

    public SubscribersDTO getSubscriberByTelegramId(Long id) {
        return Mapper.toSubscribersDTO(subscribersRepository.findByTelegramId(id));
    }

}
