package com.mega.tank.pd.abstractfactory;

public class ModernFactory extends AbstractFactory {
    @Override
    public Food createFood() {
        return new Bread();
    }

    @Override
    public Vehicle createVehicle() {
        return new Car();
    }

    @Override
    public Weapon createWeapon() {
        return new AK47();
    }
}
