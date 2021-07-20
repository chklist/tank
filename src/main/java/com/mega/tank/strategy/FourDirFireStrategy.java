package com.mega.tank.strategy;

import com.mega.tank.Bullet;
import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.Tank;

public class FourDirFireStrategy implements FireStrategy {

    private Tank tank;

    public FourDirFireStrategy(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void fire() {
        Direction[] values = Direction.values();
        for (Direction value : values) {
            Bullet bullet = new Bullet(value, tank);
            GameModel.INSTANCE.getGameRoles().put(bullet.getUuid(), bullet);
        }
    }
}
