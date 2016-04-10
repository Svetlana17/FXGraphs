package com.FXGraphs.AppImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Handles load and save of pictures
 */
public class IOManager {

    /**
     * Save a file as png
     * @param pane
     * @param file
     */
    public void saveAsPNG(Pane pane, File file) {

        WritableImage writableImage = pane.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (IOException e) {
            System.out.println("Error at writing image");
        }

    }

    /**
     * Load a png file
     * @param file
     * @return the ImageView object of the file
     */
    public ImageView loadPNG(File file) {

        Image image = new Image(file.toURI().toString());

        return new ImageView(image);

    }

}
