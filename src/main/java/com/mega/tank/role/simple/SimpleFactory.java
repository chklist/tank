package com.mega.tank.role.simple;

import com.mega.tank.Direction;
import com.mega.tank.Group;
import com.mega.tank.role.*;

public class SimpleFactory extends GameRoleFactory {

    private static final SimpleFactory INSTANCE = new SimpleFactory();

    private SimpleFactory() {
    }

    public static SimpleFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Bullet createBullet(Tank tank) {
        return new SimpleBullet(tank);
    }

    @Override
    public Bullet createBullet(Direction dir, Tank tank) {
        return new SimpleBullet(dir, tank);
    }

    @Override
    public Tank createTank(Direction dir) {
        return new SimpleTank(dir);
    }

    @Override
    public Tank createTank(Direction dir, Group group, int x, int y) {
        return new SimpleTank(dir, group, x, y);
    }

    @Override
    public Explode createExplode(int x, int y) {
        return new SimpleExplode(x, y);
    }
}
