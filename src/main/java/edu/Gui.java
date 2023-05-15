package edu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Gui extends JFrame {
	private Player current;
    private JButton createGameButton;
    private JButton joinGameButton;
    private JButton startGameButton;
    private Client client;
    private JList<Game> gameList;
    private JList<Player> playerList;
    private DefaultListModel<Player> playerListModel;
    private DefaultListModel<Game> gameListModel;
    private static List<Gui> guiInstances = new ArrayList<>();
    
    // Components for two column window display games + players
    private JLabel label = new JLabel();
    private JPanel panel = new JPanel();
    private JSplitPane splitPane = new JSplitPane();

    public Gui(Player currentPlayer) {
    	// Current player interacting with GUI
    	this.current = currentPlayer;
    	System.out.println("Constructing GUI for " + current.getName());
        setTitle("SET Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // GUI Buttons
        createGameButton = new JButton("Create Game");
        joinGameButton = new JButton("Join Game");
        startGameButton = new JButton("Start Game");
        
        // List of games
        gameListModel = new DefaultListModel<>();
        gameList = new JList<>(gameListModel);
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // List of players
        playerListModel = new DefaultListModel<>();
        playerList = new JList<>(playerListModel);
        
        // Allow display of player names as sublist
        gameList.getSelectionModel().addListSelectionListener(e -> {
        	// Gets selected game
            Game currentGame = gameList.getSelectedValue();
            if (currentGame != null && currentGame.getPlayers() != null) {
                Set<Player> currentPlayers = currentGame.getPlayers();
                StringBuilder sb = new StringBuilder("<html><body><b>Players:</b><br>");
                // Display players in selected game
                for (Player player : currentPlayers) {
                	if (player != null) {
                		sb.append(player.getName()).append("<br>");
                	}
                }
                sb.append("</body></html>");
                label.setText(sb.toString());
            } else { // Display nothing if no game selected or no players in game
                label.setText("");
            }
        });
        // Creates two column window 
        splitPane.setLeftComponent(new JScrollPane(gameList));
        panel.add(label);
        splitPane.setRightComponent(panel);
        
        // Add elements to main panel for display
        JPanel mainPanel = new JPanel();
        mainPanel.add(createGameButton);
        mainPanel.add(joinGameButton);
        mainPanel.add(startGameButton);
        mainPanel.add(splitPane);
        
        // Global list of players
        mainPanel.add(new JScrollPane(playerList));
        add(mainPanel);
        
        // Client for current GUI
        client = new Client();
        
        // Send message to server to create game
        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.createGame();
            }
        });
        
        // Send message to server to join game
        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game selectedGame = gameList.getSelectedValue();
                if (selectedGame != null) {
                    if (currentPlayer != null) {
                        client.joinGame(selectedGame, currentPlayer);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error joining game");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a game to join.");
                }
            }
        });
        
        // Send message to start selected game
        startGameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Game selectedGame = gameList.getSelectedValue();
        		if (selectedGame != null) {
        			if (selectedGame.hasPlayer(currentPlayer)) { // Checks if player joined selected game
        				client.startGame(selectedGame, currentPlayer);
        			}
        			else {
        				JOptionPane.showMessageDialog(null, "Please join the game before intiating start.");
        			}
        		}
        	}
        });
        guiInstances.add(this);
        setVisible(true);
    }

    public static void main(String[] args) {
    }
    public void updateBoard(Game game, Board board) {
    	System.out.println("Running Gui.updateBoard");
    	
    }
    // Updates GUI with list of players
    public void updatePlayers(List<Player> players) {
    	System.out.println("Running Gui.updatePlayers");
    	playerListModel.clear();
    	for (Player player : players) {
    		playerListModel.addElement(player);
    	}
    }
    // Updates GUI when new game is created
    public void update(List<Game> games) {
    	System.out.println("Running Gui.update");
        gameListModel.clear();
        
        for (Game game : games) {
            gameListModel.addElement(game);
        } 

    }
}