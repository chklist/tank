package com.mega.tank;

import com.mega.tank.collider.ColliderChain;
import com.mega.tank.proxy.cglib.TimeMethodInterceptor;

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

    private GameModel() {
        // 初始化红军坦克
        mainTank = new Tank(Direction.UP, this);
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
        Tank proxy = (Tank) TimeMethodInterceptor.newInstance(mainTank);
        proxy.fire(() -> {
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
        if (!bL && !bU && !bD && !bR) {
            mainTank.setMoving(false);
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
            mainTank.setMoving(true);
        }
    }

    public Map<UUID, GameRole> getGameRoles() {
        return gameRoles;
    }

    private List<Tank> getBadTanks() {
        List<Tank> badTanks = new LinkedList<>();
        for (int i = 0; i < gameRoles.size(); i++) {
            if (gameRoles.get(i) instanceof Tank) {
                badTanks.add((Tank) gameRoles.get(i));
            }
        }
        return badTanks;
    }

    private List<Bullet> getBullets() {
        List<Bullet> bullets = new LinkedList<>();
        for (int i = 0; i < gameRoles.size(); i++) {
            if (gameRoles.get(i) instanceof Bullet) {
                bullets.add((Bullet) gameRoles.get(i));
            }
        }
        return bullets;
    }

    private List<Explode> getExplodes() {
        List<Explode> explodes = new LinkedList<>();
        for (int i = 0; i < gameRoles.size(); i++) {
            if (gameRoles.get(i) instanceof Explode) {
                explodes.add((Explode) gameRoles.get(i));
            }
        }
        return explodes;
    }
}
