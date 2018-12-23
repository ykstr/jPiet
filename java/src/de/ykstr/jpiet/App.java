package de.ykstr.jpiet;

import de.ykstr.jpiet.enums.CodedColor;
import de.ykstr.jpiet.models.PietProgram;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        File f = new File(args[0]);
        BufferedImage image = null;
        try {
            image = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PietProgram p = new PietProgram(image,PietProgram.findScalar(image));
    }

    public static void writeImage(String path, BufferedImage image){
        File f = new File(path+".gif");
        try {
            ImageIO.write(image, "gif",f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
