package edu;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8080;
    private static List<Player> players;
    private static List<Game> games;
    
    // Construct for Server 
    public Server() {
        players = new ArrayList<>();
        games = new ArrayList<>();
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
    
    void addPlayer(Player player) {
        players.add(player);
    }
 
    /* Creates a new game then update GUI */
    private static void createGame() {
    	// Generates random ID for game
    	int gameId = new Random().nextInt(1000000);
        Game game = new Game(gameId);
        games.add(game);
        broadcastGames();
    }

    private static void joinGame(int gameId, String playerName) {
    	Player requestedPlayer = findPlayer(playerName);
    	Game requestedGame = findGame(gameId);
    	requestedGame.addPlayer(requestedPlayer);
    	broadcastPlayers();
    	broadcastGames();
    }
    
    private static void startGame(int gameId, String playerName) {
        Game requestedGame = findGame(gameId);
        Board board = requestedGame.getBoard();
        
        for (Player player : requestedGame.getPlayers()) {
            ObjectOutputStream playerOutputStream = player.getOutputStream();
            try {
                playerOutputStream.writeObject(board);
                playerOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Error occurred while sending the Board to the client: " + e.getMessage());
            }
        }
    }
    
    /*  Updates GUI of all players on server 
     *  whenever a new player joins or a new game is created */
    private static void broadcastPlayers() {
    	for (Player player : players) {
    		player.getGui().updatePlayers(players);
    	}
    }
    
    private static void broadcastGames() {
        for (Player player : players) {
            player.getGui().update(games);
        }
    }
    
    private static Player findPlayer(String playerName) {
    	Player currentPlayer = null;
    	for (Player player: players) {
    		if (player.getName().equals(playerName)) {
    			currentPlayer = player;
    			return currentPlayer;
    		}
    	}
    	return currentPlayer;
    }
    
    private static Game findGame(int gameId) {
    	Game currentGame = null;
    	for (Game game: games) {
    		if (game.getId() == gameId) {
    			currentGame = game;
    			return currentGame;
    		}
    	}
    	return currentGame;
    }
    
    public List<Game> getGames() {
        return games;
    }
    
    /* ClientHandler can now send and receive objects from Client */
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
            	out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                Object message = in.readObject();
                while (message != null) {
                    if (message instanceof String) {
                        String command = (String) message;
                        if (command.equals("CREATE_GAME")) {
                            System.out.println("Received CREATE_GAME message from client");
                            createGame();
                        } else if (command.startsWith("JOIN_GAME")) {
                            System.out.println("Received JOIN_GAME message from client");
                            String[] parts = command.split(":", 3);
                            int gameId = Integer.parseInt(parts[1]);
                            String playerName = parts[2];
                            joinGame(gameId, playerName);
                        } else if (command.startsWith("START_GAME")) {
                            System.out.println("Received START_GAME message from client");
                            String[] parts = command.split(":", 3);
                            int gameId = Integer.parseInt(parts[1]);
                            String playerName = parts[2];
                            startGame(gameId, playerName);
                        } else {
                            System.out.println("Unknown message: " + command);
                        }
                    }
                    else {
                        System.out.println("Unknown object received from client");
                    }
                    message = in.readObject();
                }
            } catch (IOException e) {
                System.out.println("Error occurred: " + e.getMessage()); 
            } catch (ClassNotFoundException e) {
                System.out.println("Error occurred: " + e.getMessage());
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error occurred: " + e.getMessage());
                }
            }
        }
    }
}
