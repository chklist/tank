package com.mega.tank;

public class TankMain {
    public static void main(String[] args) {
        GameModel gm = GameModel.getInstance().init();
        TankFrame tf = new TankFrame(gm);
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

        // 添加背景音乐
        // new Thread(() -> new Audio("audio/bg_music.wav").loop()).start();
    }
}
