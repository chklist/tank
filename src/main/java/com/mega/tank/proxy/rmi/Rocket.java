package com.mega.tank.proxy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Rocket extends Remote {
    void boost(double factor) throws RemoteException;

    double getApogee() throws RemoteException;

    double getPrice() throws RemoteException;

}
