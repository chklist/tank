package com.mega.tank.pd.factorymethod;

public class PlaneFactory extends Factory {
    @Override
    public Movable create() {
        return new Plane();
    }
}
