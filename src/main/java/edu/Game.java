package edu;
import java.util.HashSet;
import java.util.Set;

class Game {
	int id;
	Board gameBoard;
	Set<Player> players;

    Game(int id) {
    	this.id = id;
    	this.gameBoard = new Board();
    	this.players = new HashSet<>();
    }
    
	int getId() {
		return id;
	}
	
	Board getBoard() {
		return gameBoard;
	}
	
	Set<Player> getPlayers() {
		return players;
    }
	
    void addPlayer(Player player) {
        players.add(player);
    }
    
    boolean hasPlayer(Player target) {
    	for (Player player : players) {
    		if (player.getName().equals(target.getName())) {
    			return true;
    		}
    	}
    	return false;
    }
    
}
