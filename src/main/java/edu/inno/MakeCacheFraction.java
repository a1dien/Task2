package edu.inno;

import edu.inno.interfaces.Cache;
import edu.inno.interfaces.Mutator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class MakeCacheFraction<T> implements InvocationHandler {
    private Double cache;
    private T obj;

    public MakeCacheFraction(T obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Если метод Cache и рез-т Cache не пустой, то вызываем из кеша
        if (obj.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Cache.class)
                && cache != null) {
            return cache;
        }
        //Если меняем значение, то рез-т сбрасываем
        if (obj.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Mutator.class)) {
            cache = null;
            return method.invoke(obj, args);
        }
        //Иначе заполняем рез-т кеша и вызываем метод.
        cache = (Double) method.invoke(obj, args);
        return cache;
    }


}






