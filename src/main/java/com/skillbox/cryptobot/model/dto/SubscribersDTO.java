package com.skillbox.cryptobot.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SubscribersDTO {
    private UUID id;
    private Long telegramId;
    private BigDecimal cost;
}
