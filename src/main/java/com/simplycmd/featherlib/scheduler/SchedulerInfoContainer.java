package com.simplycmd.featherlib.scheduler;

import lombok.Getter;

import java.util.UUID;
import java.util.function.Consumer;

public class SchedulerInfoContainer {
    @Getter
    private final int maxTicks;

    @Getter
    private final Consumer<UUID> action;

    int currentTick = 0;

    public SchedulerInfoContainer(int maxTicks, Consumer<UUID> action) {
        this.maxTicks = maxTicks;
        this.action = action;
    }
}
