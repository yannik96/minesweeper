package minesweeper.ui;

import minesweeper.enums.FieldImage;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private Map<FieldImage, ImageIcon> images = new HashMap<>();

    public ImageManager() {
        this.loadImages();
    }

    public ImageIcon getImage(FieldImage fieldImage) {
        return images.get(fieldImage);
    }

    private void loadImages() {
        ClassLoader classLoader = getClass().getClassLoader();
        for (FieldImage fieldImage : FieldImage.values()) {
            this.images.put(fieldImage, new ImageIcon(classLoader.getResource(fieldImage.getFileName())));
        }
    }

}
