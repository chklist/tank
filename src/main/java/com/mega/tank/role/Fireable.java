package com.mega.tank.role;

import com.mega.tank.strategy.FireStrategy;

public interface Fireable {
    void fire(FireStrategy fireStrategy);
}
