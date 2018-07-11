package com.battleship.takehome;

import com.battleship.takehome.battle.Battle;
import com.battleship.takehome.ship.Ship;
import com.battleship.takehome.ship.coordinates.Coord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Player {
    private Battle battle;
    private Ship newShip;
    private int playerId;
    private boolean ready = false;

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

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
