package com.skillbox.cryptobot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Subscribers {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long telegramId;
    private BigDecimal cost;
}
