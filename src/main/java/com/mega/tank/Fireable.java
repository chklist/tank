package com.mega.tank;

import com.mega.tank.strategy.FireStrategy;

public interface Fireable {
    void fire(FireStrategy fireStrategy);
}
