package client.util;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    private Configs configs;

    public ConfigLoader(String configName) {
        configs = new Configs();
        try {
            configs.load(new FileReader(configName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String name) {
        return configs.readInt(name);
    }

    public String getString(String name) {
        return configs.getProperty(name);
    }

    public Dimension getSize(String name) {
        return configs.readDimension(name);
    }

    public Rectangle getBounds(String name) {
        return configs.readRectangle(name);
    }

    public Point getPoint(String name) {
        return configs.readPoint(name);
    }

    public Color getColor(String name) {
        return configs.readColor(name);
    }

    public Font getFont(String name) {
        return configs.readFont(name);
    }
}
