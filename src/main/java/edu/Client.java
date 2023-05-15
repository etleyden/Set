package edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    
    private PrintWriter out;
    private Socket socket;
    
    public Client() {
    	System.out.println("Constructing Client");
        try {
            socket = new Socket("localhost", 8080);
            out = new PrintWriter(socket.getOutputStream(), true); // assign to class-level variable
            System.out.println("Connected to server.");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
    	Client client = new Client();
    }
    // Sends message to server to create game
    public void createGame() {
    	System.out.println("Running Client.createGame");
        out.println("CREATE_GAME");
    }
    // Sends message to server to join selected game
    public void joinGame(Game selectedGame, Player player) {
    	System.out.println("Running Client.joinGame");
    	out.println("JOIN_GAME:" + selectedGame.getId() + ":" + player.getName());
    }
    
    public void startGame(Game selectedGame, Player player) {
    	System.out.println("Running Client.startGame");
    	out.println("START_GAME:" + selectedGame.getId() + ":" + player.getName());
    }
    
    public void closeConnection() {
    	System.out.println("Running Client.closeConnection");
        try {
            socket.close();
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}