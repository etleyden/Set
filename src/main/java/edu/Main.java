package edu;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    	// Creates new server
        Server server = new Server();
        // Create thread to handle multiple client requests
        Thread serverThread = new Thread(() -> server.main(null));
        serverThread.start(); // <-- Could this be moved into the try block? --Ethan
        try {
            Thread.sleep(1000); // Wait for server to start
        } catch (InterruptedException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        // Creates and construct two new players
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1");
        players.add(player1);
        Player player2 = new Player("Player2");
        players.add(player2);
        // Adds players to server list of players
        for (Player player : players) {
            server.addPlayer(player);
            // Allow multiple players to connect to server simultaneously 
            Thread playerThread = new Thread(() -> player.main(player.getName()));
            playerThread.start();
        }
    }
}
