package com.FXGraphs.Model;

import com.FXGraphs.View.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.jexl3.JexlException;

public class Main extends Application {

    private MenuBar menuBar;
    private StackPane layout;
    private Axes axes = new Axes(
            600, 600,
            -11, 11, 1,
            -11, 11, 1
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        initMenuBar();

        Graph graph = new Graph(
                "0.25 * (x + 4) * (x + 1) * (x - 2)",
                axes.getxLow(), axes.getxHi(), 0.1,
                axes
        );

        layout = new StackPane(axes);
        layout.getChildren().add(graph);
        layout.setPadding(new Insets(20));

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: rgb(35, 39, 50);");
        vBox.getChildren().addAll(menuBar, layout);

        primaryStage.setTitle("FXGraphs");
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

    }

    private void initMenuBar() {

        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Add Function");
        add.setOnAction(event -> {
            new AddFunctionStage();
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(t -> System.exit(0));
        menuFile.getItems().addAll(add, new SeparatorMenuItem(), exit);

        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
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
                    System.out.println("There is a problem parsing the equation");
                } catch (NumberFormatException exception) {
                    System.out.println("Wrong input at set graph stroke");
                }
            });

            setTitle("Add Function");
            setScene(new Scene(grid, 450, 350));
            setMinWidth(450);
            setMinHeight(350);
            show();

        }

    }

}
