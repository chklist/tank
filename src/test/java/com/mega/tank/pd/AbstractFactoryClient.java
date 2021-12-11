package com.mega.tank.pd;

import com.mega.tank.pd.abstractfactory.*;
import org.junit.Test;

public class AbstractFactoryClient {
    @Test
    public void testAbstractFactory() {
        AbstractFactory factory = new MagicFactory();
        Food food = factory.createFood();
        food.name();
        Weapon weapon = factory.createWeapon();
        weapon.shoot();
        Vehicle vehicle = factory.createVehicle();
        vehicle.go();
    }
}
