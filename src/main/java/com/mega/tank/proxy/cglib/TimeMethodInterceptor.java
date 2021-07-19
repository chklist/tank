package com.mega.tank.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TimeMethodInterceptor implements MethodInterceptor {
    private Object target;

    private TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        long start = System.currentTimeMillis();

        Object proxy = method.invoke(target, objects);

        long end = System.currentTimeMillis();
        System.out.println(method.getName() + "方法运行时间: " + (end - start) + "毫秒");
        return proxy;
    }

    public static Object newInstance(Object target) {
        return Enhancer.create(target.getClass(), new TimeMethodInterceptor(target));
    }
}
