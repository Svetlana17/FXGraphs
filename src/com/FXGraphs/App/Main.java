package com.FXGraphs.App;

import com.FXGraphs.AppDrawings.*;
import com.FXGraphs.AppExceptions.IllegalValue;
import com.FXGraphs.AppImageIO.IOManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.jexl3.JexlException;

import java.io.File;

public class Main extends Application {

    private IOManager mIOManager = new IOManager();
    private MenuBar menuBar;
    private StackPane layout;
    private Axes axes = new Axes(
            -11, 11, 1,
            -11, 11, 1
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        initMenuBar();

        layout = new StackPane(axes);
        layout.setStyle("-fx-background-color: rgb(35, 39, 50);");
        layout.setPadding(new Insets(20));

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: rgb(35, 39, 50);");
        vBox.getChildren().addAll(menuBar, layout);

        primaryStage.setTitle("FXGraphs");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

    }

    private void initMenuBar() {

        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Add Function", new ImageView(new Image("/function.png")));
        add.setOnAction(event -> new AddFunctionStage());
        MenuItem save = new MenuItem("Save", new ImageView(new Image("/save.png")));
        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            Stage stage = new Stage();

            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                mIOManager.saveAsPNG(layout, file);
            }

        });
        MenuItem load = new MenuItem("Load", new ImageView(new Image("/save.png")));
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(t -> System.exit(0));
        menuFile.getItems().addAll(add, save, load, new SeparatorMenuItem(), exit);

        Menu menuEdit = new Menu("Edit");
        MenuItem config = new MenuItem("Config axes");
        config.setOnAction(event -> new ConfigStage());
        menuEdit.getItems().add(config);

        Menu menuView = new Menu("View");
        MenuItem reset = new MenuItem("Reset View", new ImageView(new Image("refresh.png")));
        reset.setOnAction(event -> {
            for (int i = layout.getChildren().size() - 1; i > 0; i--) {
                layout.getChildren().remove(i);
            }
        });
        MenuItem undo = new MenuItem("Undo");
        undo.setOnAction(event -> {
            if (layout.getChildren().size() > 1)
                layout.getChildren().remove(layout.getChildren().size() - 1);
        });
        menuView.getItems().addAll(reset, undo);

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
    }

    private class AddFunctionStage extends Stage{

        private Color graphColor = Color.YELLOW;

        public AddFunctionStage() {

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            grid.setStyle("-fx-background-color: rgb(35, 39, 50);");

            Label functionName = new Label("Insert function:");
            functionName.setTextFill(Color.WHITE);
            grid.add(functionName, 0, 1);

            TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);

            Label stroke = new Label("Set stroke (default 2.0)");
            stroke.setTextFill(Color.WHITE);
            grid.add(stroke, 0, 2);

            TextField strokeWidth = new TextField();
            grid.add(strokeWidth, 1, 2);

            HBox box = new HBox();
            final ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(Color.YELLOW);
            box.getChildren().add(colorPicker);
            grid.add(box, 0,3);

            Button btn = new Button("Add");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 6);

            colorPicker.setOnAction(event -> graphColor = colorPicker.getValue());

            btn.setOnAction(e -> {
                Graph graph;
                try {
                    graph = new Graph(
                            userTextField.getText(),
                            axes.getxLow(), axes.getxHi(), 0.1,
                            axes
                    );
                    if (!strokeWidth.getText().isEmpty()) {
                        double nr = new Double(strokeWidth.getText());
                        graph.setStrokeWidth(nr);
                    }
                    graph.setColor(graphColor);
                    layout.getChildren().add(graph);
                    layout.requestLayout();
                    getScene().getWindow().hide();
                } catch (JexlException jexlException) {
                    System.out.println("There is a problem with parsing the equation");
                } catch (NumberFormatException exception) {
                    System.out.println("Wrong input at set graph stroke");
                } catch (NullPointerException exception) {
                    System.out.println("Missing information");
                }
            });

            setTitle("Add Function");
            setScene(new Scene(grid, 450, 350));
            setMinWidth(450);
            setMinHeight(350);
            show();

        }

    }

    private class ConfigStage extends Stage {

        public ConfigStage() {

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            grid.setStyle("-fx-background-color: rgb(35, 39, 50);");

            Label setXHi = new Label("Maximum value of x ");
            setXHi.setTextFill(Color.WHITE);
            grid.add(setXHi, 0, 1);

            TextField xHiText = new TextField();
            grid.add(xHiText, 1, 1);

            Label setXLow = new Label("Minimum value of x ");
            setXLow.setTextFill(Color.WHITE);
            grid.add(setXLow, 0, 2);

            TextField xLowText = new TextField();
            grid.add(xLowText, 1, 2);

            Label setYHi = new Label("Maximum value of y ");
            setYHi.setTextFill(Color.WHITE);
            grid.add(setYHi, 0, 3);

            TextField yHiText = new TextField();
            grid.add(yHiText, 1, 3);

            Label setYLow = new Label("Minimum value of y ");
            setYLow.setTextFill(Color.WHITE);
            grid.add(setYLow, 0, 4);

            TextField yLowText = new TextField();
            grid.add(yLowText, 1, 4);

            Label setTick = new Label("Set tick unit for X and Y axes");
            setTick.setTextFill(Color.WHITE);
            grid.add(setTick, 0, 5);

            TextField xTick = new TextField();
            grid.add(xTick, 1, 5);

            TextField yTick = new TextField();
            grid.add(yTick, 2, 5);

            Button btn = new Button("Set");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 7);

            btn.setOnAction(event -> {

                try {
                    if (!xHiText.getText().isEmpty())
                        axes.setxHi(Double.valueOf(xHiText.getText()));
                    if (!xLowText.getText().isEmpty())
                        axes.setxLow(Double.valueOf(xLowText.getText()));
                    if (!yHiText.getText().isEmpty())
                        axes.setyHi(Double.valueOf(yHiText.getText()));
                    if (!yLowText.getText().isEmpty())
                        axes.setyLow(Double.valueOf(yLowText.getText()));
                    if (!xTick.getText().isEmpty())
                        axes.setxTickUnit(Double.valueOf(xTick.getText()));
                    if (!yTick.getText().isEmpty())
                        axes.setyTickUnit(Double.valueOf(yTick.getText()));
                    axes.repaint();
                    for (int i = 1; i < layout.getChildren().size(); i++) {
                        ((Graph) layout.getChildren().get(i)).setxMin(axes.getxLow());
                        ((Graph) layout.getChildren().get(i)).setxMax(axes.getxHi());
                        ((Graph) layout.getChildren().get(i)).repaint();
                    }
                    getScene().getWindow().hide();
                } catch (NumberFormatException exception) {
                    System.out.println("Input is not number");
                    exception.printStackTrace();
                } catch (NullPointerException exception) {
                    System.out.println("Missing information");
                } catch (IllegalValue exception) {
                    System.out.println("Wrong value");
                    exception.printStackTrace();
                }

            });

            setTitle("Configure axes");
            setScene(new Scene(grid, 550, 350));
            setMinWidth(450);
            setMaxHeight(450);
            show();

        }

    }

}
