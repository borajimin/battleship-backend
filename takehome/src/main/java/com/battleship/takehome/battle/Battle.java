package com.battleship.takehome.battle;

import com.battleship.takehome.ship.Carrier;
import com.battleship.takehome.ship.Ship;
import com.battleship.takehome.ship.coordinates.Coord;

import java.util.*;

public class Battle {
    private int[][] map;//board for the game
    private List<Ship> shipPositions = new ArrayList<>();//List of ships in the map;

    public List<Ship> getShipPositions() {
        return shipPositions;
    }

    public void setShipPositions(List<Ship> shipPositions) {
        this.shipPositions = shipPositions;
    }

    public Battle(int[][] map) {
        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
    //generate random board with ships already placed
    public void createRandomBoard() {
        for(Ship s : shipPositions) {
            Random rn = new Random();
            int index = rn.nextInt(10);
            int rowOrCol = rn.nextInt(1);
            int startIndex = rn.nextInt(10);

            List<Coord> position = new ArrayList<>();

            for(int i = 0; i < s.getSize(); i++){
                Coord coord = new Coord();
                if(rowOrCol == 0) {
                    coord.setX(index);
                    if(9 - startIndex < s.getSize()){
                        startIndex = startIndex - s.getSize();
                    }
                    coord.setY(startIndex);
                } else {
                    coord.setY(index);
                    if(9 - startIndex < s.getSize()){
                        startIndex = startIndex - s.getSize();
                    }
                    coord.setX(startIndex);
                }
                position.add(coord);
            }
            s.setPosition(position);

        }
        updateMap();
    }

    public boolean isValidCoords(List<Ship> ships) {
        for(Ship s : ships) {

        }
        return true;
    }
    //placing ship on the board. returns a bool to tell the user if
    //placing ship was valid or not. If not, it will not mutate map.
    //If it is, ship will be moved to the new position.
    public void updateMap() {
        for(Ship s : shipPositions) {
            s.updatePosition(map);
        }
    }
}
