import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorListener;

public class App extends JFrame implements MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;

	// GUI Components
	private final int CENTER = GridBagConstraints.CENTER;
	private static int WIDTH = 1024, HEIGHT = 728;
	private final Dimension SIZE = new Dimension(WIDTH, HEIGHT);

	// Menu
	private JMenuBar menubar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem quit = new JMenuItem("Quit");
	private JMenu editMenu = new JMenu("Edit");
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem about = new JMenuItem("About");

	private JPanel mainPanel;
	private JPanel playerInfoPanel;
	private PlayerPanel[] playerPanel;
	private JPanel mapWrapper;
	private MapPanel mapPanel;
	private JPanel viewPanel;
	private JPanel actionPanel;
	private JPanel selectPanel;
	private JPanel commandPanel;
	private JLabel selectPanelTitle;

	private JLabel sourceCountryTitleLabel;
	private JLabel sourceCountryImageLabel;
	private JLabel sourceCountryNameLabel;
	private JLabel destCountryTitleLabel;
	private JLabel destCountryImageLabel;
	private JLabel destCountryNameLabel;
	private JLabel commandPanelTitle;
	private JButton chooseSourceButton;
	private JButton chooseDestButton;
	private JButton draftUnitsButton;
	private JButton attackButton;
	private JButton fortifyButton;
	private JButton endTurnButton;
	private JPanel cardListPanel;
	private JLabel cardPanelLabel;
	private JList<String> cardList;
	private JScrollPane cardListScroll;

	private Country sourceCountry;
	private Country destCountry;

	private Object lastButton;
	
	private ArrayList<Player> players;
	private Board gameboard;
	private Deck cards;
	private int turn;
	private boolean capturedTerritory = false;
	
	private Player currPlayer;
	
	private enum State {START, DRAFT, ATTACK, FORTIFY, END};
	private State gameState;

	// private Deck territories;

	public App() {
		gameState = State.START;
		lastButton = null;
		// Create Board
		this.gameboard = new Board();
		createPlayers();
		currPlayer = players.get(turn %2);
		dealCards();
		assignTerritories();
		//System.out.println(players);
		this.turn = 0;
		initGUI();
		initialArmyPlacement();
		
		while (turn*players.size() < 20) {
			capturedTerritory = false;
			if (gameState == State.DRAFT) {
				int territoryBonus = (int) Math.floor(currPlayer.getTerritories().size() / 3);
				int continentBonus = 0;
				for (Continent continent : gameboard.getWorld()) {
					boolean found = false;
					Iterator<Country> it = continent.getCountries().iterator();
					while (it.hasNext() && found == false) {
						Country country = it.next();
						if (currPlayer.getTerritories().contains(country)) {
							found = true;
						}
					}
					if (found == true) {
						territoryBonus += continent.getBonusArmies();
					}
				}
				currPlayer.setDraftedArmies(currPlayer.getDraftedArmies() + territoryBonus + continentBonus);
			}
			if (gameState == State.ATTACK) {
				
			}
			if (gameState == State.FORTIFY) {
				
			}
		}
		

		// Test update by removing 7 of player 1's territories
//		for (int i = 0; i < 7; i++) {
//			Player HUMAN = players.get(0);
//			Player AI = players.get(1);
//			Country country = HUMAN.getTerritories().get(0);
//			// Simulate losing a territory
//			HUMAN.loseBattle(country, AI, 3);
//		}
//		playerPanel[0].updatePanel();
//		playerPanel[1].updatePanel();
//		mapPanel.update();

		// newGame();
	}

	/** Create players */
	private void createPlayers() {
		// Create Players
		this.players = new ArrayList<Player>();
		players.add(new Player("Human", PlayerColors.GREEN, 80));
		players.add(new Player("Computer", PlayerColors.RED, 80));
		players.add(new Player("Neutral", PlayerColors.GRAY, 40));
	}

	/** Create a new deck and deal cards out to players */
	private void dealCards() {
		this.cards = new Deck();
		for (int i = 0; cards.deckSize() > 0; i++) {
			players.get(i % players.size()).getHand().add(cards.draw());
		}
	}

	/** Based on the cards that players have, assign them territories */
	private void assignTerritories() {
		for (Player player : players) {
			for (Continent continent : gameboard.getWorld()) {
				for (Country country : continent.getCountries()) {
					for (Card card : player.getHand()) {
						if (country.getName().equalsIgnoreCase(card.getCountryName())) {
							// Add a reference to player territories
							player.getTerritories().add(country);
							// Assign territory traits
							country.setPlayer(player);
							player.draftUnits(1, country);
						}
					}
				}
			}
		}
	}
	
	private void initialArmyPlacement() {
		if (gameState == State.START) {
			for (int i = 1; i < players.size(); i++) {
				while(players.get(i).getDraftedArmies() > 0) {
					int rand = (int)(Math.random()*players.get(i).getTerritories().size());
					players.get(i).draftUnits(1, players.get(i).getTerritories().get(rand));
				}
			}
			//System.out.println(players.get(1).getArmySize());
			while (players.get(0).getDraftedArmies() > 0) {
				// Allow player 1 to place remaining draftable armies.
			}
			JOptionPane.showMessageDialog(null, "Get Ready" + currPlayer.getName(), "RISK", JOptionPane.PLAIN_MESSAGE);
			gameState = State.DRAFT;
		}
	}
	
	private void nextPlayer() {
		gameState = State.DRAFT;
		turn++;
		currPlayer = players.get(turn % 2);
	}

	private void initGUI() {
		initMenu();
		initComponents();

		this.setTitle("RISK");
		this.setPreferredSize(SIZE);
		this.setMinimumSize(SIZE);
		this.setMaximumSize(SIZE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initComponents() {
		// Initialize components
		mainPanel = new JPanel(new BorderLayout());
		playerInfoPanel = new JPanel(new GridLayout(3, 1));
		playerPanel = new PlayerPanel[3];
		playerPanel[0] = new PlayerPanel();
		playerPanel[1] = new PlayerPanel();
		playerPanel[2] = new PlayerPanel();
		mapWrapper = new JPanel(new GridBagLayout());
		mapPanel = new MapPanel(gameboard);
		viewPanel = new JPanel(new BorderLayout());
		actionPanel = new JPanel(new GridBagLayout());
		selectPanel = new JPanel(new GridBagLayout());
		commandPanel = new JPanel(new BorderLayout());
		selectPanelTitle = new JLabel("Selected Territories");
		sourceCountryTitleLabel = new JLabel("Source");
		sourceCountryImageLabel = new JLabel(new ImageIcon(
				readImage("resources/default.png")));
		sourceCountryNameLabel = new JLabel("<SOURCE>");
		destCountryTitleLabel = new JLabel("Desination");
		destCountryImageLabel = new JLabel(new ImageIcon(
				readImage("resources/default.png")));
		destCountryNameLabel = new JLabel("<DESTINATION>");
		commandPanelTitle = new JLabel("Commands");
		chooseSourceButton = new JButton("Choose Source");
		chooseDestButton = new JButton("Choose Destination");
		draftUnitsButton = new JButton("Draft");
		attackButton = new JButton("Attack");
		fortifyButton = new JButton("Fortify");
		endTurnButton = new JButton("End Turn");
		cardListPanel = new JPanel(new GridBagLayout());
		cardPanelLabel = new JLabel("Hand");
		cardList = new JList<>(new String[] { "Card 1", "Card 2", "Card 3",
				"Card 4", "Card 5" });
		cardListScroll = new JScrollPane(cardList);

		// Add Listeners
		mainPanel.addMouseListener(this);
		chooseSourceButton.addActionListener(this);
		chooseDestButton.addActionListener(this);
		draftUnitsButton.addActionListener(this);
		attackButton.addActionListener(this);
		fortifyButton.addActionListener(this);
		endTurnButton.addActionListener(this);

		Font titleFont = new Font("Arial", Font.BOLD, 14);
		selectPanelTitle.setFont(titleFont);
		commandPanelTitle.setFont(titleFont);
		cardPanelLabel.setFont(titleFont);

		// Player Info panel
		// playerInfoPanel.setPreferredSize(new Dimension(150, 0));
		// rightToolbar.setLayout(new GridLayout(4,1,0,15));
		playerInfoPanel.setBorder(LineBorder.createGrayLineBorder());
		playerInfoPanel.setBackground(new Color(0x888888));
		mainPanel.setBackground(new Color(0x0));

		sourceCountryImageLabel.setBorder(LineBorder.createGrayLineBorder());
		destCountryImageLabel.setBorder(LineBorder.createGrayLineBorder());

		sourceCountryImageLabel.addMouseListener(this);

		// Add components
		this.add(mainPanel);
		mainPanel.add(playerInfoPanel, BorderLayout.WEST);
		playerInfoPanel.add(playerPanel[0]);
		playerInfoPanel.add(playerPanel[1]);
		playerInfoPanel.add(playerPanel[2]);
		mainPanel.add(viewPanel, BorderLayout.CENTER);
		viewPanel.add(mapWrapper, BorderLayout.CENTER);
		addComponent(mapWrapper, mapPanel, 0, 0, 1, 1, CENTER);
		viewPanel.add(commandPanel, BorderLayout.SOUTH);
		commandPanel.add(selectPanel, BorderLayout.WEST);
		selectPanel.setBorder(LineBorder.createBlackLineBorder());
		addComponent(selectPanel, selectPanelTitle, 0, 0, 2, 1,
				GridBagConstraints.NORTH);
		addComponent(selectPanel, sourceCountryTitleLabel, 0, 1, 1, 1, CENTER);
		addComponent(selectPanel, sourceCountryImageLabel, 0, 2, 1, 1, CENTER);
		addComponent(selectPanel, sourceCountryNameLabel, 0, 3, 1, 1, CENTER);
		addComponent(selectPanel, destCountryTitleLabel, 1, 1, 1, 1, CENTER);
		addComponent(selectPanel, destCountryImageLabel, 1, 2, 1, 1, CENTER);
		addComponent(selectPanel, destCountryNameLabel, 1, 3, 1, 1, CENTER);
		commandPanel.add(actionPanel, BorderLayout.CENTER);
		addComponent(actionPanel, commandPanelTitle, 0, 0, 1, 1, CENTER);
		addComponent(actionPanel, chooseSourceButton, 0, 1, 1, 1, CENTER);
		addComponent(actionPanel, chooseDestButton, 0, 2, 1, 1, CENTER);
		addComponent(actionPanel, draftUnitsButton, 0, 3, 1, 1, CENTER);
		addComponent(actionPanel, attackButton, 0, 4, 1, 1, CENTER);
		addComponent(actionPanel, fortifyButton, 0, 5, 1, 1, CENTER);
		addComponent(actionPanel, endTurnButton, 0, 6, 1, 1, CENTER);
		commandPanel.add(cardListPanel, BorderLayout.EAST);
		addComponent(cardListPanel, cardPanelLabel, 0, 0, 1, 1,
				GridBagConstraints.NORTH);
		addComponent(cardListPanel, cardListScroll, 0, 1, 1, 1, CENTER);

		mapPanel.addMouseListener(this);

		mapWrapper.setBackground(new Color(0xbbccff));
		// sourceCountryImage.setPreferredSize(new Dimension(20, 20));
		// destCountryImage.setPreferredSize(new Dimension(20, 20));
		
	// Add players to the playerPanel and update
			for (int i = 0; i < players.size(); i++) {
				playerPanel[i].addPlayer(players.get(i));
				playerPanel[i].updatePanel();
			}
	}

	private void initMenu() {
		menubar = new JMenuBar();
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(helpMenu);
		fileMenu.add(newGame);
		fileMenu.add(quit);
		helpMenu.add(about);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		newGame.setMnemonic(KeyEvent.VK_N);
		quit.setMnemonic(KeyEvent.VK_Q);
		about.setMnemonic(KeyEvent.VK_A);
		newGame.addActionListener(new NewGameMenuListener());
		quit.addActionListener(new QuitMenuListener());
		about.addActionListener(new AboutMenuListener());
		this.setJMenuBar(menubar);
	}
	
	private void updateGUI() {
		for (PlayerPanel p : playerPanel)
			p.updatePanel();
		mapPanel.update();
		this.repaint();		
	}

	/** Adds a component to a panel using the GridBagConstraints layout manager. */
	private void addComponent(JPanel panel, JComponent component, int xPos,
			int yPos, int width, int height, int align) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.gridx = xPos; // Column.
		grid.gridy = yPos; // Row.
		grid.gridwidth = width; // Width.
		grid.gridheight = height; // Height.
		grid.anchor = align; // Position
		grid.insets = new Insets(3, 3, 3, 3); // Add space between components.
		grid.fill = GridBagConstraints.NONE; // Resize component.
		panel.add(component, grid); // Adds component to the grid to the panel.
	}

	public BufferedImage readImage(String fileLocation) {
		System.out.println("LOADING: " + fileLocation);
		BufferedImage img = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(fileLocation);
			//System.out.println(url.getPath());
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private class NewGameMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "This button starts a new game.",
					"New Game", JOptionPane.PLAIN_MESSAGE);
		}
	}

	private class QuitMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	private class AboutMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "RISK v1.0\nCreated By: Ryan Kane",
					"About", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new App();
	}

	public void trace(Object obj) {
		System.err.println(obj);
	}

	public Country getSelectedCountry(int pixel) {
		if (pixel != 0) {
			Iterator<Country> it = gameboard.getTerritories().iterator();
			while (it.hasNext()) {
				Country country = (Country) it.next();
				if (country.getColorVal() == pixel) {
					return country;
				}
			}
		}
		return null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// JOptionPane.showMessageDialog(this, "Message", "Title",
		// JOptionPane.INFORMATION_MESSAGE);
		//players.get(0).setColor(PlayerColors.BLUE.color());
		System.err.print("Changed");
		
		if (gameState == State.START) {
			int p = mapPanel.getCurrPixel();
			Country c = getSelectedCountry(p);
			if (c != null) {
				if (c.getPlayer() == currPlayer) {
					currPlayer.draftUnits(1, c);
					updateGUI();
				}
			}
		}

		if (gameState == State.ATTACK) {	
			int p = mapPanel.getCurrPixel();
			Country c = getSelectedCountry(p);
			if (c != null) {
				if (lastButton != null) {	
					if (lastButton == chooseSourceButton) {
						if (c.getPlayer() == currPlayer) {
							sourceCountry = c;
							sourceCountryImageLabel.setIcon(c.getImage());
							sourceCountryNameLabel.setText(c.getName());
						} else {
							chooseCountryErr();
						}
					} else if (lastButton == chooseDestButton) {
						Set<Country> neightbors = null;
						try {
							neightbors = gameboard.getNeighbors(sourceCountry);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						if (neightbors.contains(c)) {	
							if (c.getPlayer() != currPlayer) {
								destCountry = c;
								destCountryImageLabel.setIcon(c.getImage());
								destCountryNameLabel.setText(c.getName());
							} else {
								chooseCountryErr();
							}
						} else {
							String title = "Destination Choice Error";
							String available = c.getName() + " is not an ajacent enemy country. \n\nAvailable countries to attack:";
							for (Country n : neightbors) {
								if (n.getPlayer() != currPlayer)
								available += String.format("\n - %s (%s)", n.getName(), n.getPlayer().getName());
							}
							
							JOptionPane.showMessageDialog(null, available, title,
									JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}
		}

		if (e.getSource() == sourceCountryImageLabel) {
			if (sourceCountry != null) {
				String text = "Owner: " + sourceCountry.getPlayer().getName()
						+ "\nArmy Size: " + sourceCountry.getArmySize();

				JOptionPane.showMessageDialog(null, text, sourceCountry.getName(),
						JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	public void chooseCountryErr() {
		JOptionPane.showMessageDialog(null, "You cannot pick this country!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lastButton = e.getSource();
		// TODO Auto-generated method stub
		if (e.getSource() == attackButton) {
			System.out.println("Attack!");
		}

		if (e.getSource() == chooseSourceButton || e.getSource() == chooseDestButton) {
			gameState = State.ATTACK;
		}
		
		if (e.getSource() == endTurnButton) {
			int messageType = JOptionPane.WARNING_MESSAGE;
      String[] options = {"Yes", "No"};
			int response = JOptionPane.showOptionDialog(this, "Are you sure?", "Warning: Ending Turn?!", 0, messageType, null, options, "No");
			
			if (response == 0) {
				// Set up next player
				nextPlayer();
				String text = String.format("Player: %s - It's your turn!", currPlayer.getName());
				JOptionPane.showMessageDialog(null, text, "Next Turn", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Aborted Action!", "Hey!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
