package edu;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    
    private ObjectOutputStream out;
    private Socket socket;
    
    public Client() {
        System.out.println("Constructing Client");
        try {
            socket = new Socket("localhost", 8080);
            out = new ObjectOutputStream(socket.getOutputStream()); // assign to class-level variable
            System.out.println("Connected to server.");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }
    
    public void createGame() {
        System.out.println("Running Client.createGame");
        sendObject("CREATE_GAME");
    }
    
    public void joinGame(Game selectedGame, Player player) {
        System.out.println("Running Client.joinGame");
        String command = "JOIN_GAME:" + selectedGame.getId() + ":" + player.getName();
        sendObject(command);
    }
    
    public void startGame(Game selectedGame, Player player) {
        System.out.println("Running Client.startGame");
        String command = "START_GAME:" + selectedGame.getId() + ":" + player.getName();
        sendObject(command);
    }
    
    private void sendObject(Object object) {
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    
    public void closeConnection() {
        System.out.println("Running Client.closeConnection");
        try {
            out.close();
            socket.close();
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}