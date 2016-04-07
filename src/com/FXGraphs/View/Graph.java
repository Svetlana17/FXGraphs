package com.FXGraphs.View;

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
    private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
    private JexlExpression f;
    private MapContext mc = new MapContext();

    /**
     * Graph class constructor
     * Creates a Pane object that represents a drawing of a function graph
     * @param f the function
     * @param xMin minimum value for x so that f(x) = 0
     * @param xMax maximum value for x so that f(x) = 0
     * @param xInc incrementation of x
     * @param axes axes used to draw the graph
     */
    public Graph(
            String function,
            double xMin, double xMax, double xInc,
            Axes axes
    ) {

        f = jexl.createExpression(function);
        path = new Path();
        path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
        path.setStrokeWidth(2);

        path.setClip( new Rectangle(
                        0, 0,
                        axes.getPrefWidth(),
                        axes.getPrefHeight()));

        double x = xMin;
        mc.set("x", x);
        double y = (Double) f.evaluate(mc);


        path.getElements().add(new MoveTo(mapX(x, axes), mapY(y, axes)));

        x += xInc;
        mc.set("x", x);
        while (x < xMax) {
            y = (Double) f.evaluate(mc);

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
        path.setStroke(color);
    }
}
