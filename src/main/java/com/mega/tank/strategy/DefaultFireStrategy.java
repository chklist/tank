package com.mega.tank.strategy;

import com.mega.tank.GameModel;
import com.mega.tank.role.Tank;
import com.mega.tank.role.GameRoleFactory;

/**
 * 单个方向开火
 */
public class DefaultFireStrategy implements FireStrategy {
    private GameRoleFactory factory;

    public DefaultFireStrategy(GameRoleFactory factory) {
        this.factory = factory;
    }

    @Override
    public void fire(Tank tank) {
        GameModel.getInstance().add(factory.createBullet(tank));
    }
}
