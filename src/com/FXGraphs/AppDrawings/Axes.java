package com.FXGraphs.AppDrawings;

import com.FXGraphs.AppExceptions.IllegalValue;
import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

/**
 * A Pane object that represents a XY axes system
 */
public class Axes extends Pane {

    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private double xLow, xHi;
    private double yLow, yHi;
    private double xTickUnit, yTickUnit;
    private int width = 600;
    private int height = 600;

    /**
     * Axes class constructor
     * @param xLow lowest value of x on the x axe
     * @param xHi highest value of x on the x axe
     * @param xTickUnit x axe tick unit
     * @param yLow lowest value of y on the y axe
     * @param yHi highest value of y on the y axe
     * @param yTickUnit y axe tick unit
     */
    public Axes(
            double xLow, double xHi, double xTickUnit,
            double yLow, double yHi, double yTickUnit
    ) {

        this.xLow = xLow;
        this.xHi = xHi;
        this.yLow = yLow;
        this.yHi = yHi;
        this.xTickUnit = xTickUnit;
        this.yTickUnit = yTickUnit;

        setSize();
        initXAxis();
        initYAxis();

        getChildren().setAll(xAxis, yAxis);
    }

    private void setSize() {
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    private void initXAxis() {

        xAxis = new NumberAxis("X", xLow, xHi, xTickUnit);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(width);
        xAxis.setLayoutY(height / 2);
    }

    private void initYAxis() {

        yAxis = new NumberAxis("Y", yLow, yHi, yTickUnit);
        yAxis.setSide(Side.LEFT);
        yAxis.setMinorTickVisible(false);
        yAxis.setPrefHeight(height);
        yAxis.layoutXProperty().bind(
                Bindings.subtract(
                        (width / 2) + 1,
                        yAxis.widthProperty()
                )
        );

    }

    /**
     * Get XAxis
     * @return
     */
    public NumberAxis getXAxis() {
        return xAxis;
    }

    /**
     * Get YAxis
     * @return
     */
    public NumberAxis getYAxis() {
        return yAxis;
    }

    /**
     * Set the highest value of x on the axes
     * @param xHi
     * @throws IllegalValue
     */
    public void setxHi(double xHi) throws IllegalValue {
        if (xHi < 0)
            throw new IllegalValue("Value is lower than 0");
        this.xHi = xHi;
    }

    /**
     * Sets the lowest value of x on the axes
     * @param xLow
     * @throws IllegalValue
     */
    public void setxLow(double xLow) throws IllegalValue {
        if (xLow > 0) {
            throw new IllegalValue("Value is higher than 0");
        }
        this.xLow = xLow;
    }

    /**
     * Sets the tick unit of the x axe
     * @param xTickUnit
     * @throws IllegalValue
     */
    public void setxTickUnit(double xTickUnit) throws IllegalValue {
        if (xTickUnit < 0.1) {
            throw new IllegalValue("Value too low");
        }
        this.xTickUnit = xTickUnit;
    }

    /**
     * Sets the lowest value of y on the axes
     * @param yLow
     * @throws IllegalValue
     */
    public void setyLow(double yLow) throws IllegalValue {
        if (yLow > 0) {
            throw new IllegalValue("Value is higher than 0");
        }
        this.yLow = yLow;
    }

    /**
     * Sets the highest value of x on the axes
     * @param yHi
     * @throws IllegalValue
     */
    public void setyHi(double yHi) throws IllegalValue {
        if (yHi < 0) {
            throw new IllegalValue("Value is lower than 0");
        }
        this.yHi = yHi;
    }

    /**
     * Sets the tick unit of the y axe
     * @param yTickUnit
     */
    public void setyTickUnit(double yTickUnit) throws IllegalValue{
        if (yTickUnit < 0.1) {
            throw new IllegalValue("Value too low");
        }
        this.yTickUnit = yTickUnit;
    }

    /**
     * Get the lowest value of x
     * @return
     */
    public double getxLow() {
        return xLow;
    }

    /**
     * Get the highest value of x
     * @return
     */
    public double getxHi() {
        return xHi;
    }

    /**
     * Repaints the axes
     */
    public void repaint() {
        getChildren().removeAll(xAxis, yAxis);

        setSize();
        initXAxis();
        initYAxis();

        getChildren().setAll(xAxis, yAxis);

        requestLayout();
    }

}
