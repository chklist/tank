package com.mega.tank.role;

import java.awt.*;

public abstract class GameRole {
    protected int x, y;

    public abstract void paint(Graphics graphics);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
