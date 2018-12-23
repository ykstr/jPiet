package de.ykstr.jpiet.models;

import de.ykstr.jpiet.enums.CodedColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PietProgram {
    private BufferedImage sourceImage;
    private int scalar;

    private CodedColor[][] codels;
    private ArrayList<Codel> blocks;

    public PietProgram(BufferedImage sourceImage, int scalar){
        if(sourceImage.getHeight()%scalar != 0 || sourceImage.getWidth()%scalar != 0)throw new IllegalArgumentException("Wrong scalar!");

        this.sourceImage = sourceImage;
        this.scalar = scalar;
        codels = new CodedColor[sourceImage.getWidth()/scalar][sourceImage.getHeight()/scalar];

        for(int x = 0; x < codels.length; x++){
            for(int y = 0; y < codels[x].length; y++){
                codels[x][y] = CodedColor.transformToNearest(sourceImage.getRGB(x*scalar, y*scalar));
            }
        }
    }

    public BufferedImage toImage(){
        BufferedImage result = new BufferedImage(codels.length, codels[0].length, BufferedImage.TYPE_INT_RGB);
        Graphics painter = result.createGraphics();
        for(int x = 0; x < codels.length; x++){
            for(int y = 0; y < codels[x].length; y++){
                painter.setColor(codels[x][y].color);
                painter.fillRect(x,y,1,1);
            }
        }
        painter.dispose();
        return result;
    }

    public static int findScalar(BufferedImage image){
        int scalar = image.getWidth();
        int counter = image.getWidth();
        int currentColor = -1;

        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                if(y == 0 || image.getRGB(x,y) != currentColor){
                    currentColor = image.getRGB(x,y);
                    scalar = Math.min(counter, scalar);
                    counter = 0;
                }
                counter++;
            }
        }

        //Reversed go-through loop
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                if(x == 0 || image.getRGB(x,y) != currentColor){
                    currentColor = image.getRGB(x,y);
                    scalar = Math.min(counter, scalar);
                    counter = 0;
                }
                counter++;
            }
        }

        return Math.min(scalar, counter);
    }


}
