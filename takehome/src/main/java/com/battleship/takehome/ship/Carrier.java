package com.battleship.takehome.ship;

import com.battleship.takehome.ship.coordinates.Coord;

import java.util.ArrayList;
import java.util.List;

public class Carrier implements Ship {
    private static int size = 5;
    private List<Coord> position = new ArrayList<>();
    public int getSize(){
        return size;
    }

    public static void setSize(int size) {
        Carrier.size = size;
    }

    @Override
    public List<Coord> getPosition() {
        return position;
    }

    @Override
    public void setPosition(List<Coord> position) {
        this.position = position;
    }

    @Override
    public void updatePosition(int[][] map) {
        for(Coord c : position) {
            map[c.getX()][c.getY()] = size;
        }
    }
}
