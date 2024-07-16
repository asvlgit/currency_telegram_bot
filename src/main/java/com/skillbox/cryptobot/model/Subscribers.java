package com.skillbox.cryptobot.model;

import jakarta.persistence.*;
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id_subscriber")
    private Notification notification;
}
