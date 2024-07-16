package com.skillbox.cryptobot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @Column(name = "id_subscriber")
    private UUID idSubscriber;
    private LocalDateTime lastSend;
}
