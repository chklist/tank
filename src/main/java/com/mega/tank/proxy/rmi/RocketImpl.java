package com.mega.tank.proxy.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RocketImpl extends UnicastRemoteObject implements Rocket {
    private double price;
    private double apogee;

    public RocketImpl(double price, double apogee) throws RemoteException {
        this.price = price;
        this.apogee = apogee;
    }

    @Override
    public void boost(double factor) {
        apogee *= factor;
    }

    @Override
    public double getApogee() {
        return apogee;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
