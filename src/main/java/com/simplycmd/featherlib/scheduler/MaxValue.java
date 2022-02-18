package com.simplycmd.featherlib.scheduler;

import lombok.Getter;
import lombok.Setter;

public class MaxValue {
    @Getter
    final int max;

    @Getter
    @Setter
    int current = 0;

    public MaxValue(int max) {
        this.max = max;
    }
}
