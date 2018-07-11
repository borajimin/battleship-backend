package com.battleship.takehome.controller;

import com.battleship.takehome.game.Game;
import com.battleship.takehome.Player;
import com.battleship.takehome.battle.Battle;
import com.battleship.takehome.game.GameWrapper;
import com.battleship.takehome.ship.*;
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

    @RequestMapping(path="/createGame")
    public GameWrapper createGame() {
        GameWrapper gw = new GameWrapper();
        gw.setGame(new Game());
        gw.setSuccess(true);
        return gw;
    }

    @RequestMapping(path="/join/{playerId}", method=RequestMethod.POST)
    public GameWrapper joinGame(@PathVariable("playerId") int playerId, @RequestBody Game game) {
        GameWrapper gw = new GameWrapper();
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
        Battle battle = new Battle();
        battle.setMap(newMap);
        Player player = new Player();
        player.setBattle(battle);
        player.setPlayerId(playerId);
        if(playerId == 1) {
            game.setPlayer1(player);
        } else {
            game.setPlayer2(player);
        }

        game.setJoinedPlayers(game.getJoinedPlayers() + 1);
        if(game.getJoinedPlayers() > 2) {
            gw.setSuccess(false);
            gw.setGame(game);
            gw.setErrorMessage("More than two players joined");
        } else {
            gw.setSuccess(true);
            gw.setGame(game);
        }

        return gw;
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

        gw.setGame(game);
        gw.setSuccess(true);

        return gw;
    }

    @RequestMapping(path="/ready/{playerId}", method=RequestMethod.POST)
    public GameWrapper playerReady(@PathVariable("playerId") int playerId, @RequestBody Game game) {

        GameWrapper gw = new GameWrapper();
        Player player = (playerId == 1) ? game.getPlayer1() : game.getPlayer2();

        if(player.getBattle().getShipPositions().size() < 5) {
            gw.setGame(game);
            gw.setSuccess(false);
            gw.setErrorMessage("need to add more ships.");
        }
        if(player.getBattle().isValidMap()) {
            player.setReady(true);
            gw.setSuccess(true);
            gw.setGame(game);
        }
        return gw;
    }

    @RequestMapping(path="/start", method=RequestMethod.POST)
    public GameWrapper gameStart(@RequestBody Game game) {
        GameWrapper gw = new GameWrapper();
        if(game.getPlayer1().isReady() && game.getPlayer2().isReady()){
            Random rn = new Random();
            int turn = rn.nextInt(1);
            if(turn == 0) {
                game.setTurn(1);
            } else {
                game.setTurn(2);
            }
        } else {
            gw.setSuccess(false);
            gw.setErrorMessage("Not all users are ready.");
            gw.setGame(game);
        }
        gw.setGame(game);
        gw.setSuccess(true);
        return gw;
    }
}
