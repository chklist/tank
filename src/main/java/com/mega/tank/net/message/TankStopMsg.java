package com.mega.tank.net.message;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.Tank;
import com.mega.tank.util.CloseUtil;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg {

    private UUID uuid;
    private int x, y;
    private Direction dir;

    public TankStopMsg() {
    }

    public TankStopMsg(Tank tank) {
        this(tank.getUuid(), tank.getX(), tank.getY(), tank.getDir());
    }

    public TankStopMsg(UUID uuid, int x, int y, Direction dir) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public void handle() {
        if (uuid.equals(GameModel.INSTANCE.getMainTank().getUuid())) return;
        Tank t = (Tank) GameModel.INSTANCE.getGameRoleByUuid(uuid);
        if (t != null) {
            t.setMoving(false);
            t.setX(x);
            t.setY(y);
            t.setDir(dir);
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
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
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
            x = dis.readInt();
            y = dis.readInt();
            dir = Direction.values()[dis.readInt()];
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
        return MsgType.TankStop;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "TankStopMsg{" +
                "uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                '}';
    }
}
