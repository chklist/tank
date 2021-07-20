package com.mega.tank;

import com.mega.tank.collider.ColliderChain;
import com.mega.tank.net.NettyClient;
import com.mega.tank.net.message.TankDirMsg;
import com.mega.tank.net.message.TankFireMsg;
import com.mega.tank.net.message.TankMovingMsg;
import com.mega.tank.net.message.TankStopMsg;
import com.mega.tank.strategy.FourDirFireStrategy;

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

    private Random random = new Random();

    boolean bL = false, bU = false, bD = false, bR = false;

    private GameModel() {
        // 初始化主坦克 随机Group，随机位置
        Group group = randomGroup();
        int x = Math.min(Math.max(random.nextInt(TankFrame.WIDTH), 2), TankFrame.WIDTH - Tank.WIDTH - 2);
        int y = Math.min(Math.max(random.nextInt(TankFrame.HEIGHT), 25), TankFrame.HEIGHT - Tank.HEIGHT - 2);
        mainTank = new Tank(Direction.UP, group, x, y);

        // 初始化碰撞责任链
        colliderChain = new ColliderChain();
    }

    void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Tank Num: " + getTanks().size(), 10, 60);
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
        if (!mainTank.isLiving()) return;
        // 获取代理对象 JDK
        // Fireable fireable = (Fireable) TimeHandler.newInstance(mainTank);
        // 获取代理对象 CGLIB
        //Tank proxy = (Tank) TimeMethodInterceptor.newInstance(mainTank);
        mainTank.fire(new FourDirFireStrategy(mainTank));
        NettyClient.INSTANCE.send(new TankFireMsg(mainTank));
    }

    public Tank getMainTank() {
        return mainTank;
    }

    void setMainTank() {
        if (!mainTank.isLiving()) {
            return;
        }
        // 获取坦克旧的方向
        Direction dir = mainTank.getDir();

        if (!bL && !bU && !bD && !bR) {
            mainTank.setMoving(false);
            // 如果坦克停止，则发送TankStop消息
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
            // 如果坦克从停止状态变成移动状态，则发送TankMoving消息
            if (!mainTank.isMoving()) {
                NettyClient.INSTANCE.send(new TankMovingMsg(mainTank));
            }
            mainTank.setMoving(true);

            // 如果坦克方向发生改变，则发送TankDir消息
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

    private Group randomGroup() {
        int len = Group.values().length;
        return Group.values()[random.nextInt(len)];
    }

    private List<Tank> getTanks() {
        List<Tank> tanks = new LinkedList<>();
        Collection<GameRole> values = gameRoles.values();
        for (GameRole value : values) {
            if (value instanceof Tank) {
                tanks.add((Tank) value);
            }
        }
        return tanks;
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
