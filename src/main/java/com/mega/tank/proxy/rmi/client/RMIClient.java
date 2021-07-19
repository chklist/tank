package com.mega.tank.proxy.rmi.client;

import com.mega.tank.proxy.rmi.Rocket;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Rocket rocket = (Rocket) Naming.lookup("rmi://127.0.0.1:5000/biggie");

        rocket.boost(100);

        double apogee = rocket.getApogee();
        System.out.println(apogee);
    }
}
