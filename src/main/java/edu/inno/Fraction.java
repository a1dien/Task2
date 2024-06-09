package edu.inno;

import edu.inno.interfaces.Cache;
import edu.inno.interfaces.Fractionable;
import edu.inno.interfaces.Mutator;
public class Fraction implements Fractionable {
    private int num;
    private int denum;
    private int count;

    public int getCount() {
        return count;
    }

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }
    @Mutator
    public void setNum(int num) {
        this.num = num;
    }
    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache
    public double doubleValue() {
//        System.out.println("Invoke Double Value!");
        count++;
        return (double) num/denum;
    }

}
