package com.mega.tank.pd.factorymethod;

public class CarFactory extends Factory {
    @Override
    public Movable create() {
        return new Car();
    }
}
