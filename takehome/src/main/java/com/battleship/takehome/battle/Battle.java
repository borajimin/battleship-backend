package com.battleship.takehome.battle;

import com.battleship.takehome.ship.Carrier;
import com.battleship.takehome.ship.Ship;
import com.battleship.takehome.ship.coordinates.Coord;
import org.springframework.beans.factory.annotation.Autowired;

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

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
    public boolean isValidMap() {
        int carrier = 0;
        int battleship = 0;
        int submarine = 0;
        int cruiser = 0;
        int patrol = 0;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map.length; j++) {
                switch (map[i][j]) {
                    case 1:
                        patrol++;
                        break;
                    case 2:
                        cruiser++;
                        break;
                    case 3:
                        submarine++;
                        break;
                    case 4:
                        battleship++;
                        break;
                    case 5:
                        carrier++;
                        break;
                    default:
                        break;
                }
            }
        }
        if(carrier == 5 && battleship == 4 && submarine == 3 && cruiser == 2 && patrol == 1) {
            return true;
        } else {
            return false;
        }
    }
    //generate random board with ships already placed
    public void createRandomBoard() {
        for(Ship s : shipPositions) {
            List<Coord> position = new ArrayList<>();
            do{
                Random rn = new Random();
                int index = rn.nextInt(10);
                int rowOrCol = rn.nextInt(1);
                int startIndex = rn.nextInt(10);

                if(9 - startIndex < s.getSize()){
                    startIndex = startIndex - s.getSize();
                }

                for(int i = 0; i < s.getSize(); i++){
                    Coord coord = new Coord();
                    if(rowOrCol == 0) {
                        coord.setX(index);
                        coord.setY(startIndex);
                    } else {
                        coord.setY(index);
                        coord.setX(startIndex);
                    }
                    position.add(coord);
                }
            }while(!isValidCoords(position));
            s.setPosition(position);
            updateMap();
        }
    }

    public boolean isValidCoords(List<Coord> shipPosition) {
        if(shipPosition.size() == 0){
            return false;
        }
        for(Coord c : shipPosition) {
            if(map[c.getX()][c.getY()] != 0) {
                return false;
            }
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
