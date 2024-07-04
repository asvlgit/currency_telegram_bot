package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.model.Subscribers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscribersRepository extends CrudRepository<Subscribers, UUID> {

    Subscribers findByTelegramId(Long id);
}
