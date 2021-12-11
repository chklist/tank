package com.mega.tank.role;

import com.mega.tank.Direction;
import com.mega.tank.Group;

public abstract class GameRoleFactory {

    public abstract Bullet createBullet(Tank tank);

    public abstract Bullet createBullet(Direction dir, Tank tank);

    public abstract Tank createTank(Direction dir);

    public abstract Tank createTank(Direction dir, Group group, int x, int y);

    public abstract Explode createExplode(int x, int y);
}
