package com.mega.tank;

import com.mega.tank.collider.ColliderChain;
import com.mega.tank.net.NettyClient;
import com.mega.tank.net.message.TankDirMsg;
import com.mega.tank.net.message.TankMovingMsg;
import com.mega.tank.net.message.TankStopMsg;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调停者模式
 */
public class GameModel {

    public static GameModel INSTANCE = new GameModel();

    private Tank mainTank;
    private ColliderChain colliderChain;

    private Map<UUID, GameRole> gameRoles = new ConcurrentHashMap<>();

    boolean bL = false, bU = false, bD = false, bR = false;

    Random random = new Random();

    private GameModel() {
        // 初始化主坦克
        mainTank = new Tank(Direction.UP);
        // 初始化碰撞责任链
        colliderChain = new ColliderChain();
    }

    void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Bad Tank Num: " + getBadTanks().size(), 10, 60);
        g.drawString("Bullet Num: " + getBullets().size(), 10, 80);
        g.drawString("Explode Num: " + getExplodes().size(), 10, 100);
        g.setColor(c);

        mainTank.paint(g);

        Collection<GameRole> values = gameRoles.values();
        for (GameRole gameRole : values) {
            gameRole.paint(g);
        }

        for (GameRole gr1 : values) {
            for (GameRole gr2 : values) {
                if (gr1.uuid != gr2.uuid) {
                    colliderChain.collide(gr1, gr2);
                }
            }
        }
    }

    void fire() {
        // 获取代理对象 JDK
        // Fireable fireable = (Fireable) TimeHandler.newInstance(mainTank);
        // 获取代理对象 CGLIB
        //Tank proxy = (Tank) TimeMethodInterceptor.newInstance(mainTank);
        mainTank.fire(() -> {
            Direction[] values = Direction.values();
            for (Direction value : values) {
                Bullet bullet = new Bullet(value, this.mainTank);
                gameRoles.put(bullet.uuid, bullet);
            }
            //gameRoles.add(new Bullet(tank.getDir(), tank.tf, tank));
        });
    }

    public Tank getMainTank() {
        return mainTank;
    }

    void setMainTank() {
        // 获取坦克旧的方向
        Direction dir = mainTank.getDir();

        if (!bL && !bU && !bD && !bR) {
            mainTank.setMoving(false);
            NettyClient.INSTANCE.send(new TankStopMsg(mainTank));
        } else {
            if (bL) {
                mainTank.dir = Direction.LEFT;
            }
            if (bU) {
                mainTank.dir = Direction.UP;
            }
            if (bD) {
                mainTank.dir = Direction.DOWN;
            }
            if (bR) {
                mainTank.dir = Direction.RIGHT;
            }
            if (!mainTank.isMoving()) {
                NettyClient.INSTANCE.send(new TankMovingMsg(mainTank));
            }
            mainTank.setMoving(true);

            // 如果坦克方向发生改变，则发生TankDir消息
            if (dir != mainTank.getDir()) {
                NettyClient.INSTANCE.send(new TankDirMsg(mainTank));
            }
        }
    }

    public Map<UUID, GameRole> getGameRoles() {
        return gameRoles;
    }

    public GameRole getGameRoleByUuid(UUID uuid) {
        return gameRoles.get(uuid);
    }

    private List<Tank> getBadTanks() {
        List<Tank> badTanks = new LinkedList<>();
        Collection<GameRole> values = gameRoles.values();
        for (GameRole value : values) {
            if (value instanceof Tank) {
                badTanks.add((Tank) value);
            }
        }
        return badTanks;
    }

    private List<Bullet> getBullets() {
        List<Bullet> bullets = new LinkedList<>();
        Collection<GameRole> values = gameRoles.values();
        for (GameRole value : values) {
            if (value instanceof Bullet) {
                bullets.add((Bullet) value);
            }
        }
        return bullets;
    }

    private List<Explode> getExplodes() {
        List<Explode> explodes = new LinkedList<>();
        Collection<GameRole> values = gameRoles.values();
        for (GameRole value : values) {
            if (value instanceof Explode) {
                explodes.add((Explode) value);
            }
        }
        return explodes;
    }
}
