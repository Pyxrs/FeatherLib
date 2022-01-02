package io.github.simplycmd.featherlib.scheduler;

import lombok.Getter;
import java.util.function.Consumer;

public class SchedulerInfoContainer {
    @Getter
    private final int maxTicks;

    @Getter
    private final Consumer<Integer> action;

    int currentTick = 0;

    public SchedulerInfoContainer(int maxTicks, Consumer<Integer> action) {
        this.maxTicks = maxTicks;
        this.action = action;
    }
}
