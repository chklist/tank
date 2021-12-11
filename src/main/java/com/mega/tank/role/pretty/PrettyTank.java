package com.mega.tank.role.pretty;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.Group;
import com.mega.tank.TankFrame;
import com.mega.tank.role.Tank;
import com.mega.tank.strategy.DefaultFireStrategy;
import com.mega.tank.strategy.FireStrategy;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;

public class PrettyTank extends Tank {

    static final int WIDTH = ResourceMgr.tank2Up.getWidth();
    static final int HEIGHT = ResourceMgr.tank2Up.getHeight();

    private Random random = new Random();

    PrettyTank(Direction dir) {
        this(dir, Group.GOOD, (TankFrame.WIDTH - 50) >> 1, 400);
    }

    PrettyTank(Direction dir, Group group, int x, int y) {
        this.dir = dir;
        this.group = group;
        this.x = x;
        this.y = y;

        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            GameModel.getInstance().remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.tank2Left, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tank2Up, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tank2Down, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tank2Right, x, y, null);
                break;
        }
        move();
    }

    @Override
    public void fire(FireStrategy fireStrategy) {
        fireStrategy.fire(this);
    }

    @Override
    public void move() {
        super.move();
        if (group == Group.BAD && random.nextInt(100) > 95) {
            this.fire(new DefaultFireStrategy(GameModel.getInstance().factory));
            randomDir();
        }
        boundsCheck();
    }

    @Override
    public void die() {
        living = false;
        int eX = x + PrettyTank.WIDTH / 2 - PrettyExplode.WIDTH / 2;
        int eY = y + PrettyTank.HEIGHT / 2 - PrettyExplode.HEIGHT / 2;
        GameModel.getInstance().add(GameModel.getInstance().factory.createExplode(eX, eY));
    }

    private void randomDir() {
        this.dir = Direction.values()[random.nextInt(4)];
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 25) y = 25;
        if (this.x > TankFrame.WIDTH - PrettyTank.WIDTH - 2) x = TankFrame.WIDTH - PrettyTank.WIDTH - 2;
        if (this.y > TankFrame.HEIGHT - PrettyTank.HEIGHT - 2) y = TankFrame.HEIGHT - PrettyTank.HEIGHT - 2;
    }
}
