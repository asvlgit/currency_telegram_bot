package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, UUID> {
}
