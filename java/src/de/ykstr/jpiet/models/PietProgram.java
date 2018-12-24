package de.ykstr.jpiet.models;

import de.ykstr.jpiet.enums.CodedColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PietProgram {
    private BufferedImage sourceImage;
    private int scalar;

    private CodedColor[][] codels;
    private ArrayList<CodelBlock> blocks = new ArrayList<>();

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

        detectBlocks();
    }

    public void detectBlocks(){
        boolean[][] checkedPositions = new boolean[codels.length][codels[0].length];
        for(int x = 0; x < codels.length; x++){
            for(int y = 0; y < codels[x].length; y++){
                detectSameColorArea(checkedPositions, null, x,y);
            }
        }
    }

    public void detectSameColorArea(boolean[][] checkedPositions, CodelBlock current, int x, int y){
        if(!isValid(x, y) || checkedPositions[x][y])return;
        if(current == null){
            current = new CodelBlock(new Point(x,y), codels[x][y]);
            blocks.add(current);
        }
        if(current.color == codels[x][y]){
            checkedPositions[x][y] = true;
            current.codels.add(new Point(x,y));
            detectSameColorArea(checkedPositions, current, x+1, y);
            detectSameColorArea(checkedPositions, current, x-1, y);
            detectSameColorArea(checkedPositions, current, x, y+1);
            detectSameColorArea(checkedPositions, current, x, y-1);
        }
    }

    public CodelBlock getBlock(Point pos){
        if(!isValid(pos.x, pos.y))throw new IllegalArgumentException("Position is not valid for the program!");
        for(CodelBlock block : blocks){
            if(block.contains(pos))return block;
        }
        return null;
    }

    public boolean isValid(int x, int y){
        return !(x >= codels.length || y >= codels[0].length || x < 0 || y < 0);
    }

    public CodedColor getColor(Point pos){
        if(!isValid(pos.x, pos.y))return CodedColor.BLACK;
        return codels[pos.x][pos.y];
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
