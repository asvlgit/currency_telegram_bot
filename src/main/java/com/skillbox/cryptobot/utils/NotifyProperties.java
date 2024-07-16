package com.skillbox.cryptobot.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConfigurationProperties(prefix = "telegram.bot.notify.delay")
@Getter
@Setter
public class NotifyProperties {

    public enum Unit {MINUTES, SECONDS}

    private long value;
    private Unit unit;

    public long getDelay() {
        if (Objects.requireNonNull(unit) == Unit.MINUTES) {
            return value * 60;
        }
        return value;
    }
}
