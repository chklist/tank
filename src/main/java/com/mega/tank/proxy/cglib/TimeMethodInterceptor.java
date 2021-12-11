package com.mega.tank.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TimeMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        long start = System.currentTimeMillis();

        // Object proxy = method.invoke(target, objects);
        Object proxy = methodProxy.invokeSuper(o, objects);

        long end = System.currentTimeMillis();
        System.out.println(method.getName() + "方法运行时间: " + (end - start) + "毫秒");
        return proxy;
    }

    public <T> T newInstance(T target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    public <T> T newInstance(T target, Class[] argsClz, Object[] argsValue) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create(argsClz, argsValue);
    }
}
