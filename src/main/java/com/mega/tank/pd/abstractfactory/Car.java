package com.mega.tank.pd.abstractfactory;

/**
 * 汽车
 */
public class Car extends Vehicle {
    @Override
    public void go() {
        System.out.println("Car go ...");
    }
}
