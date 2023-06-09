package edu;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridLayout;
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
    private JList<Game> gameList; //List of ongoing games
    private JList<Player> playerList;
    private DefaultListModel<Player> playerListModel;
    private DefaultListModel<Game> gameListModel;
    private static List<Gui> guiInstances = new ArrayList<>();
    
    // Components for two column window display games + players
    private JLabel label = new JLabel();
    private JPanel panel = new JPanel();
    //Will contain Create/Join/Start Buttons
    private JPanel controlPanel = new JPanel(new GridLayout(0, 1, 0, 20));
    private JSplitPane splitPane = new JSplitPane();

    public Gui(Player currentPlayer) {
    	// Current player interacting with GUI
    	this.current = currentPlayer;
    	
    	//Window Settings
    	System.out.println("Constructing GUI for " + current.getName());
        setTitle("SET Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Build GUI
        JPanel mainPanel = new JPanel();
        
        //Create/Join/Start Buttons
        createGameButton = new JButton("Create Game");
        joinGameButton = new JButton("Join Game");
        startGameButton = new JButton("Start Game");
        controlPanel.add(createGameButton);
        controlPanel.add(joinGameButton);
        controlPanel.add(startGameButton);
        mainPanel.add(controlPanel);
        
        // Set up GUI for list of ongoing games
        gameListModel = new DefaultListModel<>();
        gameList = new JList<>(gameListModel);
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // GUI for list of players participating in a selected game
        playerListModel = new DefaultListModel<>();
        playerList = new JList<>(playerListModel);
        
        // Displays games and players sublist
        splitPane.setLeftComponent(new JScrollPane(gameList));
        splitPane.setRightComponent(panel);
        gameList.setPreferredSize(new Dimension(400, 200));
        panel.setPreferredSize(new Dimension(300, 200));
        
        panel.add(label);
        
        mainPanel.add(splitPane);
        
        //Fit window to GUI
        add(mainPanel);
        pack();
        
        // Client for current GUI
        client = new Client();
        
        // Player interaction with GUI -> message to client -> server perform action
        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.createGame();
            }
        });
        
        //Event Listeners for control buttons
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
        
        
        startGameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Game selectedGame = gameList.getSelectedValue();
        		if (selectedGame != null) {
        			if (selectedGame.hasPlayer(currentPlayer)) {
        				client.startGame(selectedGame, currentPlayer);
        			}
        			else {
        				JOptionPane.showMessageDialog(null, "Please join the game before intiating start.");
        			}
        		}
        	}
        });
        
        // JList that display players in selected game
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
        guiInstances.add(this);
        setVisible(true);
    }
    // Updates players' GUI with new Board when game is started
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