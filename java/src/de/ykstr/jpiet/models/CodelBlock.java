package de.ykstr.jpiet.models;

import de.ykstr.jpiet.enums.CodedColor;

import java.awt.*;
import java.util.HashSet;

public class CodelBlock {
    CodedColor color;
    HashSet<Point> codels;

    public CodelBlock(Point position, CodedColor color){
        this.color = color;
        this.codels = new HashSet<>();
        codels.add(position);
    }

    public boolean contains(Point position){
        return codels.contains(position);
    }
}
