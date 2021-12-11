package com.mega.tank.pd;

import com.mega.tank.pd.factorymethod.CarFactory;
import com.mega.tank.pd.factorymethod.Movable;
import org.junit.Test;

public class FactoryMethodClient {
    @Test
    public void testFactoryMethod() {
        Movable vehicle = new CarFactory().create();
        vehicle.go();
    }
}
