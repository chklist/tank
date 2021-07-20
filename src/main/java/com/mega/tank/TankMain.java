package com.mega.tank;

import com.mega.tank.net.NettyClient;

import java.util.concurrent.TimeUnit;

public class TankMain {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame(GameModel.INSTANCE);
        tf.setVisible(true);

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        NettyClient.INSTANCE.connect("localhost", 8888);

    }
}
