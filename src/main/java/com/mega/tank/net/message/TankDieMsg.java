package com.mega.tank.net.message;

import com.mega.tank.GameModel;
import com.mega.tank.Tank;
import com.mega.tank.util.CloseUtil;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg {

    private UUID uuid;

    private boolean living;

    public TankDieMsg() {
    }

    public TankDieMsg(Tank tank) {
        this(tank.getUuid(), tank.isLiving());
    }

    public TankDieMsg(UUID uuid, boolean living) {
        this.uuid = uuid;
        this.living = living;
    }

    @Override
    public void handle() {
        if (uuid.equals(GameModel.INSTANCE.getMainTank().getUuid())) {
            GameModel.INSTANCE.getMainTank().die();
        }
        Tank t = (Tank) GameModel.INSTANCE.getGameRoleByUuid(uuid);
        if (t != null) {
            t.die();
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
            dos.writeBoolean(living);
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
            living = dis.readBoolean();
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
        return MsgType.TankDie;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isLiving() {
        return living;
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "uuid=" + uuid +
                ", living=" + living +
                '}';
    }
}
