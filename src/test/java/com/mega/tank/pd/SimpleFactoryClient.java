package com.mega.tank.pd;

import com.mega.tank.pd.simplefactory.Movable;
import com.mega.tank.pd.simplefactory.SimpleVehicleFactory;
import org.junit.Test;

public class SimpleFactoryClient {
    @Test
    public void testSimpleFactory() {
        Movable vehicle = SimpleVehicleFactory.createVehicle(3);
        vehicle.go();
    }
}
