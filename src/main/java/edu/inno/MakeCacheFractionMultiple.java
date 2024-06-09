package edu.inno;

import edu.inno.interfaces.Cache;
import edu.inno.interfaces.Mutator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MakeCacheFractionMultiple<T> implements InvocationHandler {
    @Getter @Setter @AllArgsConstructor
    private class Results {
        private int x;
        private int y;
        private double res;
    }
    private Double cache;
    private List<Results> results= new ArrayList();
    private T obj;

    public MakeCacheFractionMultiple(T obj) {
        this.obj = obj;
    }
    //Попытка сделать более умную версию кеша, где бы запоминались рез-ты прошлых вычислений
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Не нашел варианта, как взять значения переданных в метод, поэтому делаю через переменные объекта
        Method cMethod = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        int num = (int)getField(obj, "num");
        int denum = (int)getField(obj, "denum");

        //Если метод Cache и рез-т Cache пустой, то вызываем из кеша
        if (cMethod.isAnnotationPresent(Cache.class)) {
            for (Results res : results) {
                //Если был вызов уже с такими значениями, то возвращаем уже подсчитанный рез-т
                if (res.getX() == num && res.getY() == denum) return res.getRes();
            }
            //Иначе считаем результат и заносим в список рассчитанных
            double dbl = (double) method.invoke(obj, args);
            Results results1 = new Results(num, denum, dbl);
            results.add(results1);
            return dbl;
        }
        //Если метод Mutator, то просто вызываем метод
        //В данной реализации это бессмысленно
        if (cMethod.isAnnotationPresent(Mutator.class)) {
            return method.invoke(obj, args);
        }
        return method.invoke(obj, args);
    }
    //Поиск значения поля
    private Object getField(Object obj, String nameField) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(nameField);
        field.setAccessible(true);
        return field.get(obj);
    }
}






