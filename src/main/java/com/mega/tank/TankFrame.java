package com.mega.tank;

import com.mega.tank.net.NettyClient;
import com.mega.tank.net.message.TankDieMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    /**
     * 门面（Facade）模式
     */
    private GameModel gm;

    TankFrame(GameModel gm) {
        setTitle("Tank War");
        setResizable(false);

        this.gm = gm;

        // 设置窗口位置屏幕居中显示，设置窗口大小
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setBounds((screenWidth - WIDTH) >> 1, (screenHeight - HEIGHT) >> 1, WIDTH, HEIGHT);

        // 设置键盘按键事件
        this.addKeyListener(new MyKeyListener());

        // 设置关闭窗口监听事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gm.getMainTank().die();
                NettyClient.INSTANCE.send(new TankDieMsg(gm.getMainTank()));
                System.exit(0);
            }
        });
    }

    private Image offScreenImage;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(WIDTH, HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    gm.bL = true;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_UP:
                    gm.bU = true;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_DOWN:
                    gm.bD = true;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_RIGHT:
                    gm.bR = true;
                    gm.setMainTank();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    gm.bL = false;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_UP:
                    gm.bU = false;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_DOWN:
                    gm.bD = false;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_RIGHT:
                    gm.bR = false;
                    gm.setMainTank();
                    break;
                case KeyEvent.VK_CONTROL:
                    gm.fire();
                    break;
                default:
                    break;
            }

        }
    }
}
