package com.mega.tank;

import com.mega.tank.net.NettyClient;

public class TankMain {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame(GameModel.INSTANCE);
        tf.setVisible(true);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        NettyClient.INSTANCE.connect("localhost", 8888);

    }
}
