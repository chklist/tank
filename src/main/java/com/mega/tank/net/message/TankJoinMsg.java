package com.mega.tank.net.message;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.Group;
import com.mega.tank.Tank;
import com.mega.tank.net.NettyClient;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg {

    private UUID uuid;
    private int x, y;
    private Group group;
    private Direction dir;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Tank tank) {
        this(tank.getUuid(), tank.getX(), tank.getY(), tank.getGroup(), tank.getDir());
    }

    public TankJoinMsg(UUID uuid, int x, int y, Group group, Direction dir) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.group = group;
        this.dir = dir;
    }

    @Override
    public void handle() {
        GameModel gm = GameModel.INSTANCE;
        System.out.println(uuid.equals(gm.getMainTank().getUuid()) || gm.getGameRoles().containsKey(uuid));
        if (uuid.equals(gm.getMainTank().getUuid()) || gm.getGameRoles().containsKey(uuid)) {
            return;
        }
        Tank tank = new Tank(this);
        gm.getGameRoles().put(tank.getUuid(), tank);
        System.out.println(gm.getGameRoles().size());

        NettyClient.INSTANCE.send(new TankJoinMsg(GameModel.INSTANCE.getMainTank()));
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
            dos.writeInt(group.ordinal());
            dos.writeInt(dir.ordinal());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            group = Group.values()[dis.readInt()];
            dir = Direction.values()[dis.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 使用Netty的ByteBuf读取字节数组
        /*ByteBuf buf = Unpooled.copiedBuffer(bytes);
        x = buf.readInt();
        y = buf.readInt();*/
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
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

    public Group getGroup() {
        return group;
    }

    public Direction getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", group=" + group +
                ", dir=" + dir +
                '}';
    }
}
