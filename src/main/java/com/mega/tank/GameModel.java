package com.mega.tank;

import com.mega.tank.collider.ColliderChain;
import com.mega.tank.proxy.cglib.TimeMethodInterceptor;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 调停者模式
 */
class GameModel {

    static GameModel INSTANCE = new GameModel();

    private Tank mainTank;
    private ColliderChain colliderChain;

    private List<GameRole> gameRoles = new LinkedList<>();

    boolean bL = false, bU = false, bD = false, bR = false;

    private GameModel() {
        // 初始化红军坦克
        mainTank = new Tank(Direction.UP, this);
        // 初始化碰撞责任链
        colliderChain = new ColliderChain();
        // 初始化敌军坦克
        for (int i = 0; i < 5; i++) {
            Tank badTank = new Tank(Direction.DOWN, this, Group.BAD, (i + 2) * 100, 50);
            badTank.speed = 3;
            badTank.setMoving(true);
            gameRoles.add(badTank);
        }
    }

    void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Bad Tank Num: " + getBadTanks().size(), 10, 60);
        g.drawString("Bullet Num: " + getBullets().size(), 10, 80);
        g.drawString("Explode Num: " + getExplodes().size(), 10, 100);
        g.setColor(c);

        mainTank.paint(g);

        for (int i = 0; i < gameRoles.size(); i++) {
            gameRoles.get(i).paint(g);
        }

        for (int i = 0; i < gameRoles.size(); i++) {
            for (int j = i + 1; j < gameRoles.size(); j++) {
                colliderChain.collide(gameRoles.get(i), gameRoles.get(j));
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
                gameRoles.add(new Bullet(value, this, this.mainTank));
            }
            //gameRoles.add(new Bullet(tank.getDir(), tank.tf, tank));
        });
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

    List<GameRole> getGameRoles() {
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
