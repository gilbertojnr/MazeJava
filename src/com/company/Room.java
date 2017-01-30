package com.company;

/**
 * Created by gilbertakpan on 1/18/17.
 */
public class Room {
    int row;
    int col;
    boolean wasVisited = false;
    boolean hasBottom = true;
    boolean hasRight = true;
    boolean isStart = false;
    boolean isEnd = false;

    public Room(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public String toString(){
        String points = row + "," + col;
        return points;
    }
}
