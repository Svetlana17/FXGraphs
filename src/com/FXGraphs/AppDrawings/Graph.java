package com.FXGraphs.AppDrawings;

import com.FXGraphs.AppJexlModifications.PersonalJexlArithmetic;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

/**
 * A Pane that represents a function graph
 */
public class Graph extends Pane {

    private Path path;
    private Color color;
    private Axes axes;
    private double strokeWidth;
    private double xMin, xMax, xInc;
    private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).
            arithmetic(new PersonalJexlArithmetic(true)).create();
    private JexlExpression f;
    private MapContext mc = new MapContext();

    /**
     * Graph class constructor
     * Creates a Pane object that represents a drawing of a function graph
     * @param xMin minimum value for x
     * @param xMax maximum value for x
     * @param xInc incrementation of x
     * @param axes axes used to draw the graph
     */
    public Graph(
            String function,
            double xMin, double xMax, double xInc,
            Axes axes
    ) {

        f = jexl.createExpression(function);
        this.color = Color.ORANGE.deriveColor(0, 1, 1, 0.6);
        this.strokeWidth = 2;
        this.axes = axes;
        this.xMin = xMin;
        this.xMax = xMax;
        this.xInc = xInc;
        path = new Path();
        path.setStroke(color);
        path.setStrokeWidth(strokeWidth);

        path.setClip( new Rectangle(
                        0, 0,
                        axes.getPrefWidth(),
                        axes.getPrefHeight()));

        double x = xMin;
        mc.set("x", x);
        double y = new Double(f.evaluate(mc).toString());


        path.getElements().add(new MoveTo(mapX(x, axes), mapY(y, axes)));

        x += xInc;
        mc.set("x", x);
        while (x < xMax) {
            y = new Double(f.evaluate(mc).toString());

            path.getElements().add(new LineTo(mapX(x, axes), mapY(y, axes)));

            x += xInc;
            mc.set("x", x);
        }

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        getChildren().setAll(path);
    }

    private double mapX(double x, Axes axes) {
        double tx = axes.getPrefWidth() / 2;
        double sx = axes.getPrefWidth() /
                (axes.getXAxis().getUpperBound() -
                        axes.getXAxis().getLowerBound());

        return x * sx + tx;
    }

    private double mapY(double y, Axes axes) {
        double ty = axes.getPrefHeight() / 2;
        double sy = axes.getPrefHeight() /
                (axes.getYAxis().getUpperBound() -
                        axes.getYAxis().getLowerBound());

        return -y * sy + ty;
    }

    /**
     * Sets the color of the graph
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
        path.setStroke(this.color);
    }

    /**
     * Sets the width of the stroke
     * @param width
     */
    public void setStrokeWidth(double width) {
        this.strokeWidth = width;
        path.setStrokeWidth(this.strokeWidth);
    }

    /**
     * Sets the minimum value for x
     * @param xMin
     */
    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    /**
     * Sets the maximum value for x
     * @param xMax
     */
    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    /**
     * Repaints the graph
     */
    public void repaint() {

        getChildren().removeAll(path);

        path = new Path();
        path.setStroke(color);
        path.setStrokeWidth(strokeWidth);

        path.setClip( new Rectangle(
                0, 0,
                axes.getPrefWidth(),
                axes.getPrefHeight()));

        double x = xMin;
        mc.set("x", x);
        double y = new Double(f.evaluate(mc).toString());


        path.getElements().add(new MoveTo(mapX(x, axes), mapY(y, axes)));

        x += xInc;
        mc.set("x", x);
        while (x < xMax) {
            y = new Double(f.evaluate(mc).toString());

            path.getElements().add(new LineTo(mapX(x, axes), mapY(y, axes)));

            x += xInc;
            mc.set("x", x);
        }

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        getChildren().setAll(path);

        requestLayout();
    }
}
