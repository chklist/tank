package com.mega.tank.pd.abstractfactory;

public abstract class AbstractFactory {
    public abstract Food createFood();

    public abstract Vehicle createVehicle();

    public abstract Weapon createWeapon();
}
