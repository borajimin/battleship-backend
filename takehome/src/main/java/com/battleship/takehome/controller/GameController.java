package com.battleship.takehome.controller;

import com.battleship.takehome.game.Game;
import com.battleship.takehome.Player;
import com.battleship.takehome.battle.Battle;
import com.battleship.takehome.game.GameWrapper;
import com.battleship.takehome.ship.*;
import com.battleship.takehome.ship.coordinates.Coord;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class GameController {
    @RequestMapping("/")
    public String index() {
        return "Server working!";
    }

    @RequestMapping(path="/")
    public Game createGame() {
        return new Game();
    }

    @RequestMapping(path="/join/{playerId}", method=RequestMethod.POST)
    public Game joinGame(@PathVariable("playerId") int playerId, @RequestBody Game game) {
        int[][] newMap = new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
        Battle battle = new Battle(newMap);
        Player player = new Player(battle, playerId);

        if(playerId == 1) {
            game.setPlayer1(player);
        } else {
            game.setPlayer2(player);
        }

        game.setJoinedPlayers(game.getJoinedPlayers() + 1);
        //when adding gamewrapper, I can throw an exception when
        //I have more than two users;
        //with above statement;
        return game;
    }

    @RequestMapping(path="/setBattleShips/{playerId}", method=RequestMethod.POST)
    public GameWrapper setBattleShips(@PathVariable("playerId") int playerId, @RequestBody Game game) {

        GameWrapper gw = new GameWrapper();
        Player player = (playerId == 1) ? game.getPlayer1() : game.getPlayer2();

        if(player.isValidCoord(player.getNewShip())) {
           Battle battle = player.getBattle();
           List<Ship> ships = battle.getShipPositions();
           for(Ship s : ships) {
               if(s.getSize() == player.getNewShip().getSize()) {
                   s.setPosition(player.getNewShip().getPosition());
               }
           }
           battle.updateMap();
           gw.setGame(game);
           gw.setSuccess(true);
           return gw;
        }

        gw.setSuccess(false);
        gw.setGame(game);

        return gw;
    }

    @RequestMapping(path="/randomBoard/{playerId}", method=RequestMethod.POST)
    public GameWrapper setRandomBoard(@PathVariable("playerId") int playerId, @RequestBody Game game) {

        GameWrapper gw = new GameWrapper();
        Player player = (playerId == 1) ? game.getPlayer1() : game.getPlayer2();

        List<Ship> ships = new ArrayList<>();
        ships.add(new Carrier());
        ships.add(new Cruiser());
        ships.add(new Battleship());
        ships.add(new Patrol());
        ships.add(new Submarine());

        Battle battle = player.getBattle();
        battle.setShipPositions(ships);
        player.setBattle(battle);

        battle = player.getBattle();
        battle.createRandomBoard();

        int[][] map = player.getBattle().getMap();



        return gw;
    }

}
