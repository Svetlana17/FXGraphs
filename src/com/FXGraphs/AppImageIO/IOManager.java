package com.FXGraphs.AppImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Handles load and save of pictures
 */
public class IOManager {

    public void saveAsPNG(Pane pane, File file) {

        WritableImage writableImage = pane.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (IOException e) {
            System.out.println("Error at writing image");
        }

    }

}
