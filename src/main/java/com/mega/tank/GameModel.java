package com.mega.tank;

import com.mega.tank.collider.ColliderChain;
import com.mega.tank.role.*;
import com.mega.tank.role.simple.SimpleTank;
import com.mega.tank.strategy.FourDirFireStrategy;
import com.mega.tank.util.PropertyMgr;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 调停者模式
 */
public class GameModel {

    private static GameModel INSTANCE = new GameModel();

    private final static String BAD_TANK_CNT_KEY = "tank.bad.count";
    private static final String GAME_ROLE_FACTORY = "game.role.factory";

    private Tank mainTank;
    private ColliderChain colliderChain;

    private List<GameRole> gameRoles = new LinkedList<>();

    boolean bL = false, bU = false, bD = false, bR = false;

    public final GameRoleFactory factory = getFactory();

    private GameModel() {
    }

    GameModel init() {
        Random random = new Random();
        // 初始化红军坦克
        mainTank = factory.createTank(Direction.UP);
        // 初始化碰撞责任链
        colliderChain = new ColliderChain();
        // 初始化敌军坦克
        int initBadTankCnt = PropertyMgr.getInstance().getInt(BAD_TANK_CNT_KEY);

        for (int i = 0; i < initBadTankCnt; i++) {
            Tank badTank = factory.createTank(Direction.DOWN, Group.BAD, random.nextInt(800), random.nextInt(300));
            badTank.setSpeed(3);
            badTank.setMoving(true);
            gameRoles.add(badTank);
        }
        return this;
    }

    public static GameModel getInstance() {
        return INSTANCE;
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
        // 获取代理对象
        // Fireable fireable = (Fireable) TimeHandler.newInstance(mainTank);
        mainTank.fire(new FourDirFireStrategy(factory));
    }

    void setMainTank() {
        if (!bL && !bU && !bD && !bR) {
            mainTank.setMoving(false);
        } else {
            if (bL) {
                mainTank.setDir(Direction.LEFT);
            }
            if (bU) {
                mainTank.setDir(Direction.UP);
            }
            if (bD) {
                mainTank.setDir(Direction.DOWN);
            }
            if (bR) {
                mainTank.setDir(Direction.RIGHT);
            }
            mainTank.setMoving(true);
        }
    }

    public List<GameRole> getGameRoles() {
        return gameRoles;
    }

    private List<SimpleTank> getBadTanks() {
        List<SimpleTank> badTanks = new LinkedList<>();
        for (int i = 0; i < gameRoles.size(); i++) {
            if (gameRoles.get(i) instanceof SimpleTank) {
                badTanks.add((SimpleTank) gameRoles.get(i));
            }
        }
        return badTanks;
    }

    public void add(GameRole gameRole) {
        gameRoles.add(gameRole);
    }

    public void remove(GameRole gameRole) {
        gameRoles.remove(gameRole);
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

    private GameRoleFactory getFactory() {
        GameRoleFactory factory = null;
        String factoryName = PropertyMgr.getInstance().getString(GAME_ROLE_FACTORY);
        try {
            Class<?> clz = Class.forName(factoryName);
            Method method = clz.getMethod("getInstance");
            factory = (GameRoleFactory) method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }
}
