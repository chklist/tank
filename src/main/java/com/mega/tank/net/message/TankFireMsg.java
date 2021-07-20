package com.mega.tank.net.message;

import com.mega.tank.Bullet;
import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.Tank;
import com.mega.tank.util.CloseUtil;

import java.io.*;
import java.util.UUID;

public class TankFireMsg extends Msg {

    private UUID uuid;

    public TankFireMsg() {
    }

    public TankFireMsg(Tank tank) {
        this(tank.getUuid());
    }

    public TankFireMsg(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void handle() {
        if (uuid.equals(GameModel.INSTANCE.getMainTank().getUuid())) return;
        Tank t = (Tank) GameModel.INSTANCE.getGameRoleByUuid(uuid);
        if (t != null) {
            t.fire(() -> {
                Direction[] values = Direction.values();
                for (Direction value : values) {
                    Bullet bullet = new Bullet(value, t);
                    GameModel.INSTANCE.getGameRoles().put(bullet.getUuid(), bullet);
                }
                //gameRoles.add(new Bullet(tank.getDir(), tank.tf, tank));
            });
            ;
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(uuid.getMostSignificantBits());
            dos.writeLong(uuid.getLeastSignificantBits());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(baos);
            CloseUtil.close(dos);
        }
        return baos.toByteArray();
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new ByteArrayInputStream(bytes));
            uuid = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(dis);
        }
        // 使用Netty的ByteBuf读取字节数组
        /*ByteBuf buf = Unpooled.copiedBuffer(bytes);
        x = buf.readInt();
        y = buf.readInt();*/
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankFire;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "TankFireMsg{" +
                "uuid=" + uuid +
                '}';
    }
}
