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

public class Main extends Application {

    private MenuBar menuBar;
    private StackPane layout;
    private Axes axes = new Axes(
            500, 500,
            -8, 8, 1,
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
                -8, 8, 0.1,
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

        public AddFunctionStage() {

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            Label userName = new Label("Insert function:");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);

            Button btn = new Button("Add");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);

            btn.setOnAction(e -> {
                Graph graph = new Graph(
                        userTextField.getText(),
                        -8, 8, 0.1,
                        axes
                );
                graph.setColor(Color.YELLOW);
                layout.getChildren().add(graph);
                layout.requestLayout();
                getScene().getWindow().hide();
            });

            setTitle("Add Function");
            setScene(new Scene(grid, 300, 250));
            show();

        }

    }

}
