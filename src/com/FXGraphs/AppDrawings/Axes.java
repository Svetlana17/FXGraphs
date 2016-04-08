package com.FXGraphs.AppDrawings;

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
    private int width, height;
    private double xTickUnit, yTickUnit;

    /**
     * Axes class constructor
     * @param width pane width
     * @param height pane height
     * @param xLow lowest value of x on the x axe
     * @param xHi highest value of x on the x axe
     * @param xTickUnit x axe tick unit
     * @param yLow lowest value of y on the y axe
     * @param yHi highest value of y on the y axe
     * @param yTickUnit y axe tick unit
     */
    public Axes(
            int width, int height,
            double xLow, double xHi, double xTickUnit,
            double yLow, double yHi, double yTickUnit
    ) {

        this.width = width;
        this.height = height;
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

        xAxis = new NumberAxis(xLow, xHi, xTickUnit);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(width);
        xAxis.setLayoutY(height / 2);
    }

    private void initYAxis() {

        yAxis = new NumberAxis(yLow, yHi, yTickUnit);
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
     */
    public void setxHi(double xHi) {
        this.xHi = xHi;
    }

    /**
     * Sets the lowest value of x on the axes
     * @param xLow
     */
    public void setxLow(double xLow) {
        this.xLow = xLow;
    }

    /**
     * Sets the tick unit of the x axe
     * @param xTickUnit
     */
    public void setxTickUnit(double xTickUnit) {
        this.xTickUnit = xTickUnit;
    }

    /**
     * Sets the lowest value of y on the axes
     * @param yLow
     */
    public void setyLow(double yLow) {
        this.yLow = yLow;
    }

    /**
     * Sets the highest value of x on the axes
     * @param yHi
     */
    public void setyHi(double yHi) {
        this.yHi = yHi;
    }

    /**
     * Sets the tick unit of the y axe
     * @param yTickUnit
     */
    public void setyTickUnit(double yTickUnit) {
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

}
