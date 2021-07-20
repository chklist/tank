package com.mega.tank;

import com.mega.tank.net.message.TankJoinMsg;
import com.mega.tank.strategy.FireStrategy;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank extends GameRole implements Fireable, Removable {

    static final int WIDTH = ResourceMgr.tankUp.getWidth();
    static final int HEIGHT = ResourceMgr.tankUp.getHeight();

    private boolean moving = false;

    private Group group;

    private Rectangle rect;

    private boolean living = true;

    private Random random = new Random();

    Tank(Direction dir) {
        this(dir, GameModel.INSTANCE);
    }

    public Tank(Direction dir, Group group, int x, int y) {
        this(dir, GameModel.INSTANCE, group, x, y);
    }

    public Tank(Direction dir, GameModel gm) {
        this(dir, gm, Group.GOOD, (TankFrame.WIDTH - 50) >> 1, 400);
    }

    public Tank(Direction dir, GameModel gm, Group group, int x, int y) {
        this.dir = dir;
        this.gm = gm;
        this.group = group;
        this.x = x;
        this.y = y;

        uuid = UUID.randomUUID();
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public Tank(TankJoinMsg msg) {
        this.dir = msg.getDir();
        this.gm = GameModel.INSTANCE;
        this.group = msg.getGroup();
        this.x = msg.getX();
        this.y = msg.getY();

        uuid = msg.getUuid();
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public Group getGroup() {
        return group;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isLiving() {
        return living;
    }

    public void die() {
        living = false;
        int eX = x + Tank.WIDTH / 2 - Explode.WIDTH / 2;
        int eY = y + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
        Explode explode = new Explode(gm, eX, eY);
        System.out.println(explode);
        gm.getGameRoles().put(explode.uuid, explode);
    }

    public Rectangle getRect() {
        return rect;
    }

    @Override
    public void move() {
        if (!moving) {
            return;
        }
        super.move();

        rect.x = this.x;
        rect.y = this.y;

        /*if (group == Group.BAD && random.nextInt(100) > 95) {
            this.fire(() -> {
                Bullet bullet = new Bullet(dir, this);
                gm.getGameRoles().put(bullet.uuid, bullet);
            });
            randomDir();
        }*/
        // 边界检测
        boundsCheck();
    }

    @Override
    public void paint(Graphics g) {
        // 如果主坦克已死，退出画图
        if (!living && uuid.equals(GameModel.INSTANCE.getMainTank().getUuid())) return;

        if (!living) {
            gm.getGameRoles().remove(this.getUuid());
        }

        Color c = g.getColor();
        g.setColor(group == Group.GOOD ? Color.GREEN : Color.RED);
        g.drawString(this.uuid.toString(), x - WIDTH / 2, y);
        g.setColor(c);

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.tankLeft, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankUp, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankDown, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankRight, x, y, null);
                break;
        }
        move();
    }

    @Override
    public void fire(FireStrategy fireStrategy) {
        fireStrategy.fire();
    }

    private void randomDir() {
        int len = Direction.values().length;
        this.dir = Direction.values()[random.nextInt(len)];
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 25) y = 25;
        if (this.x > TankFrame.WIDTH - Tank.WIDTH - 2) x = TankFrame.WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.HEIGHT - Tank.HEIGHT - 2) y = TankFrame.HEIGHT - Tank.HEIGHT - 2;
    }
}
