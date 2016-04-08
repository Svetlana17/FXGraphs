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

    /**
     * Compute sinus
     * @param exp
     * @return
     */
    public Object sin(Object exp) {
        double nr = this.toDouble(exp);
        return Math.sin(nr);
    }

    /**
     * Compute cosinus
     * @param exp
     * @return
     */
    public Object cos(Object exp) {
        double nr = this.toDouble(exp);
        return Math.cos(nr);
    }

    /**
     * Compute tangent
     * @param exp
     * @return
     */
    public Object tan(Object exp) {
        double nr = this.toDouble(exp);
        return Math.tan(nr);
    }

    /**
     * Compute cotangent
     * @param exp
     * @return
     */
    public Object ctan(Object exp) {
        double nr = this.toDouble(exp);
        return 1.0 / Math.tan(nr);
    }

    /**
     * Compute log in base e
     * @param exp
     * @return
     */
    public Object log(Object exp) {
        double nr = this.toDouble(exp);
        return Math.log(nr);
    }

}
