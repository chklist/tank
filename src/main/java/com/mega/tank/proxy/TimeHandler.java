package com.mega.tank.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TimeHandler implements InvocationHandler {
    private Object target;

    private TimeHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Object invoke = method.invoke(target, args);
        long end = System.currentTimeMillis();
        System.out.println(method.getName() + "方法运行时间: " + (end - start) + "毫秒");
        return invoke;
    }

    public static Object newInstance(Object target) {
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, new TimeHandler(target));
    }
}
