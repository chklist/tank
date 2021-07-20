package com.mega.tank.net.message;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.Tank;
import com.mega.tank.util.CloseUtil;

import java.io.*;
import java.util.UUID;

public class TankDirMsg extends Msg {

    private UUID uuid;

    private Direction dir;

    public TankDirMsg() {
    }

    public TankDirMsg(Tank tank) {
        this(tank.getUuid(), tank.getDir());
    }

    public TankDirMsg(UUID uuid, Direction dir) {
        this.uuid = uuid;
        this.dir = dir;
    }

    @Override
    public void handle() {
        if (uuid.equals(GameModel.INSTANCE.getMainTank().getUuid())) return;
        Tank t = (Tank) GameModel.INSTANCE.getGameRoleByUuid(uuid);
        if (t != null) {
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
        return MsgType.TankDir;
    }

    public UUID getUuid() {
        return uuid;
    }


    public Direction getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "TankDirMsg{" +
                "uuid=" + uuid +
                ", dir=" + dir +
                '}';
    }
}
