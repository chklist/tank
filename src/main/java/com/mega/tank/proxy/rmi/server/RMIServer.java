package com.mega.tank.proxy.rmi.server;

import com.mega.tank.proxy.rmi.Rocket;
import com.mega.tank.proxy.rmi.RocketImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Rocket biggie = new RocketImpl(29.95, 820);
        // 开启rmiregistry程序
        LocateRegistry.createRegistry(5000);
        // 注册RMI对象
        Naming.rebind("rmi://127.0.0.1:5000/biggie", biggie);
        System.out.println("Server Start!");
    }
}
