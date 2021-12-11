package com.mega.tank.strategy;

import com.mega.tank.role.Tank;

/**
 * 开火策略接口
 */
public interface FireStrategy {
    void fire(Tank tank);
}
