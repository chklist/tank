package com.mega.tank.strategy;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.role.Tank;
import com.mega.tank.role.GameRoleFactory;

/**
 * 四个方向开火
 */
public class FourDirFireStrategy implements FireStrategy {

    private GameRoleFactory factory;

    public FourDirFireStrategy(GameRoleFactory factory) {
        this.factory = factory;
    }

    @Override
    public void fire(Tank tank) {
        Direction[] values = Direction.values();
        GameModel gm = GameModel.getInstance();
        for (Direction value : values) {
            gm.add(factory.createBullet(value, tank));
        }
    }
}
