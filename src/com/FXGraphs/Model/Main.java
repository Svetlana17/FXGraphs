package com.FXGraphs.Model;

import com.FXGraphs.View.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private MenuBar menuBar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        initMenuBar();

        Axes axes = new Axes(
                500, 500,
                -8, 8, 1,
                -11, 11, 1
        );

        Graph graph = new Graph(
                "0.25 * (x + 4) * (x + 1) * (x - 2)",
                -8, 8, 0.1,
                axes
        );

        StackPane layout = new StackPane(axes);
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

}
