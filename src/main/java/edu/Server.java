package edu;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    
    // Creates new game and broadcast 
    private static void createGame() {
    	// Generates random ID for game
    	int gameId = new Random().nextInt(1000000);
        Game game = new Game(gameId);
        games.add(game);
        broadcastGames();
    }
    // Add player to selected game
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
            player.getGui().updateBoard(requestedGame, board);
        }
    }
    // Updates GUI of all players with new list of players
    private static void broadcastPlayers() {
    	for (Player player : players) {
    		player.getGui().updatePlayers(players);
    	}
    }
    
    // Updates GUI of all players on server with updated list of games
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

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                String message = in.readLine();
                while (message != null) {
                    if (message.equals("CREATE_GAME")) {
                        System.out.println("Received CREATE_GAME message from client");
                        createGame();
                    }
                    else if (message.startsWith("JOIN_GAME")) {
                    	System.out.println("Received JOIN_GAME message from client");
                    	// Obtains gameID and name of player that requested to join game
                    	String[] parts = message.split(":", 3);
                    	int gameId = Integer.parseInt(parts[1]);
                    	String playerName = parts[2];
                    	joinGame(gameId, playerName);
                    }
                    else if (message.startsWith("START_GAME")) {
                    	System.out.println("Received START_GAME message from client");
                    	String[] parts = message.split(":", 3);
                    	int gameId = Integer.parseInt(parts[1]);
                    	String playerName = parts[2];
                    	startGame(gameId, playerName);
                    }
                    else {
                        System.out.println("Unknown message: " + message);
                    }
                    message = in.readLine();
                }
            } catch (IOException e) {
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
