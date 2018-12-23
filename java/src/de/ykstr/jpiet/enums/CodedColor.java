package de.ykstr.jpiet.enums;

import java.awt.*;

public enum CodedColor {

    LIGHT_RED("#FFC0C0"), RED("#FF0000"), DARK_RED("#C00000"),
    LIGHT_YELLOW("#FFFFC0"), YELLOW("#FFFF00"), DARK_YELLOW("#C0C000"),
    LIGHT_GREEN("#C0FFC0"), GREEN("#00FF00"), DARK_GREEN("#00C000"),
    LIGHT_CYAN("#C0FFFF"), CYAN("#00FFFF"), DARK_CYAN("#00C0C0"),
    LIGHT_BLUE("#C0C0FF"), BLUE("#0000FF"), DARK_BLUE("#0000C0"),
    LIGHT_MAGENTA("#FFC0FF"), MAGENTA("#FF00FF"), DARK_MAGENTA("#C000C0"),
    WHITE("#FFFFFF"), BLACK("#000000");

    public final Color color;

    CodedColor(String colorHexCode){
        this.color = Color.decode(colorHexCode);
    }

    public static CodedColor transform(Color other){
        for(CodedColor coded : values()){
            if(coded.color.equals(other)){
                return coded;
            }
        }
        return WHITE;
    }

    public static CodedColor transform(int rgbValue){
        Color color = new Color(rgbValue);
        for(CodedColor coded : values()){
            if(coded.color.equals(color)){
                return coded;
            }
        }
        return WHITE;
    }

    public static CodedColor transformToNearest(int rgbValue){
        Color color = new Color(rgbValue);
        for(CodedColor coded : values()){
            if(coded.color.equals(color)){
                return coded;
            }
        }

        CodedColor min = null;
        int distanceSum = 255*3;
        for(CodedColor coded : values()){
            int currentDistance = Math.abs(coded.color.getRed()-color.getRed())+Math.abs(coded.color.getGreen()-color.getGreen())+Math.abs(coded.color.getBlue()-color.getBlue());
            if(currentDistance < distanceSum){
                distanceSum = currentDistance;
                min = coded;
            }
        }

        return min;
    }
}
