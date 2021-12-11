package com.mega.tank.pd.simplefactory;

/**
 * 简单工厂 也叫静态工厂
 */
public class SimpleVehicleFactory {
    public static Movable createVehicle(int type) {
        Movable res = null;
        switch (type) {
            case 1:
                res = new Car();
                break;
            case 2:
                res = new Plane();
                break;
            case 3:
                res = new Broom();
                break;
        }
        return res;
    }
}
