package com.mega.tank.strategy;

import com.mega.tank.Bullet;
import com.mega.tank.GameModel;
import com.mega.tank.Tank;

public class DefaultFireStrategy implements FireStrategy {

    private Tank tank;

    public DefaultFireStrategy(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void fire() {
        Bullet bullet = new Bullet(tank.getDir(), tank);
        GameModel.INSTANCE.getGameRoles().put(bullet.getUuid(), bullet);
    }
}
