package com.mega.tank.pd.factorymethod;

public class BroomFactory extends Factory {
    @Override
    public Movable create() {
        return new Broom();
    }
}
