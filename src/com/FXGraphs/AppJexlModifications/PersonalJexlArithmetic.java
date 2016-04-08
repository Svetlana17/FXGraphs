package com.FXGraphs.AppJexlModifications;

import org.apache.commons.jexl3.JexlArithmetic;

public class PersonalJexlArithmetic extends JexlArithmetic {


    public PersonalJexlArithmetic(boolean astrict) {
        super(astrict);
    }

    /**
     * Overrides the xor symbol from JEXL arithmetic class
     * Instead of doing XOR operation for a ^ b, it will do pow(a,b)
     * @param left
     * @param right
     * @return
     */
    @Override
    public Object xor(Object left, Object right) {

        double l = this.toDouble(left);
        double r = this.toDouble(right);

        return Math.pow(l, r);

    }
}
