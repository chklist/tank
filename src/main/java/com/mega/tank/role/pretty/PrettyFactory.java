package com.mega.tank.role.pretty;

import com.mega.tank.Direction;
import com.mega.tank.Group;
import com.mega.tank.role.Bullet;
import com.mega.tank.role.Explode;
import com.mega.tank.role.GameRoleFactory;
import com.mega.tank.role.Tank;

public class PrettyFactory extends GameRoleFactory {

    private static final PrettyFactory INSTANCE = new PrettyFactory();

    private PrettyFactory() {
    }

    public static PrettyFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Bullet createBullet(Tank tank) {
        return new PrettyBullet(tank);
    }

    @Override
    public Bullet createBullet(Direction dir, Tank tank) {
        return new PrettyBullet(dir, tank);
    }

    @Override
    public Tank createTank(Direction dir) {
        return new PrettyTank(dir);
    }

    @Override
    public Tank createTank(Direction dir, Group group, int x, int y) {
        return new PrettyTank(dir, group, x, y);
    }

    @Override
    public Explode createExplode(int x, int y) {
        return new PrettyExplode(x, y);
    }
}
