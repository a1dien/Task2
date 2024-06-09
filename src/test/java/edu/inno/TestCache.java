package edu.inno;

import edu.inno.interfaces.Fractionable;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCache {
    final Logger log = getLogger(lookup().lookupClass());

    @Test
    @Order(1)
    void assertCallCacheFirst() {
        log.debug("Проверка осуществялется засчет инкремента переменной при вызове метода doubleValue в классе Fraction.");
        log.debug("Вызываем метод double value в 1й раз.");
        Fraction fr = new Fraction(3, 2);
        Fractionable num = Utils.cache(fr);
        assertEquals(num.doubleValue(), 1.5);
        assertEquals(1, fr.getCount());
    }
    @Test
    @Order(2)
    void assertCallCacheSecond() {
        log.debug("Вызываем метод double value 2й раз. 2й вызов производится из кеш.");
        Fraction fr = new Fraction(3, 2);
        Fractionable num = Utils.cache(fr);
        num.doubleValue();
        assertEquals(num.doubleValue(), 1.5);
        assertEquals(1, fr.getCount());
    }
    @Test
    @Order(3)
    void assertCallCacheThird() {
        log.debug("Вызываем метод double value 3 раза. После 2го, производим вызов метода с Mutator. Инкремент увеличился.");
        Fraction fr = new Fraction(3, 2);
        Fractionable num = Utils.cache(fr);
        assertEquals(num.doubleValue(), 1.5);
        assertEquals(1, fr.getCount());
        num.doubleValue();
        assertEquals(1, fr.getCount());
        num.setNum(5);
        assertEquals(1, fr.getCount());
        assertEquals(num.doubleValue(), 2.5);
        assertEquals(2, fr.getCount());
    }
}





