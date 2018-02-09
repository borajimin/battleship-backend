package com.battleship.takehome;

import com.battleship.takehome.battle.Battle;
import com.battleship.takehome.ship.Ship;
import com.battleship.takehome.ship.coordinates.Coord;

import java.util.List;

public class Player {
    private Battle battle;
    private Ship newShip;
    private int playerId;

    public boolean isValidCoord(Ship ship) {
        int[][] map = battle.getMap();
        for(Coord p: ship.getPosition()){
            if(map[p.getX()][p.getY()] != 0 || map[p.getX()][p.getY()] != ship.getSize()) {
                return false;
            }
        }
        return true;
    }

    public Ship getNewShip() {
        return newShip;
    }

    public void setNewShip(Ship newShip) {
        this.newShip = newShip;
    }

    public Player(Battle battle, int playerId) {
        this.battle = battle;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
