package com.skillbox.cryptobot.model.dto;

import com.skillbox.cryptobot.model.Subscribers;

public class Mapper {
    public static SubscribersDTO toSubscribersDTO(Subscribers subscribers) {
        SubscribersDTO subscribersDTO = new SubscribersDTO();
        subscribersDTO.setId(subscribers.getId());
        subscribersDTO.setTelegramId(subscribers.getTelegramId());
        subscribersDTO.setCost(subscribers.getCost());
        return subscribersDTO;
    }

    public static Subscribers toSubscribers(SubscribersDTO subscribersDTO) {
        Subscribers subscribers = new Subscribers();
        subscribers.setId(subscribersDTO.getId());
        subscribers.setTelegramId(subscribersDTO.getTelegramId());
        subscribers.setCost(subscribersDTO.getCost());
        return subscribers;
    }
}
