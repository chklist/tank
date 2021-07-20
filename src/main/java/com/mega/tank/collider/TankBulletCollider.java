package com.mega.tank.collider;

import com.mega.tank.Bullet;
import com.mega.tank.GameRole;
import com.mega.tank.Tank;
import com.mega.tank.net.NettyClient;
import com.mega.tank.net.message.TankDieMsg;

public class TankBulletCollider extends Collider {
    @Override
    public void collide(GameRole gr1, GameRole gr2) {
        if (gr1 instanceof Tank && gr2 instanceof Bullet) {
            Tank tank = (Tank) gr1;
            Bullet bullet = (Bullet) gr2;
            if (tank.getGroup() == bullet.getTank().getGroup()) return;
            if (tank.isLiving() && bullet.isLiving() && tank.getRect().intersects(bullet.getRect())) {
                tank.die();
                bullet.die();
                NettyClient.INSTANCE.send(new TankDieMsg(tank));
                isContinue = false;
            }
        } else if (gr1 instanceof Bullet && gr2 instanceof Tank) {
            collide(gr2, gr1);
        }
    }
}
