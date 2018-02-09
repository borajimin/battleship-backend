package com.battleship.takehome.ship;

import com.battleship.takehome.ship.coordinates.Coord;

import java.util.List;

public interface Ship {
    public int getSize();
    public List<Coord> getPosition();
    public void setPosition(List<Coord> position);
    public void updatePosition(int[][] map);
}
