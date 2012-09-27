import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.Set;

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
import javax.swing.border.LineBorder;

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
	private JLabel sourceCountryLabel;
	private JPanel sourceCountryPanel;
	private ImageIcon sourceCountryImage;
	private JLabel destCountryLabel;
	private JPanel destCountryPanel;
	private ImageIcon destCountryImage;
	private JLabel commandPanelTitle;
	private JButton chooseSourceButton;
	private JButton chooseDestButton;
	private JButton draftUnitsButton;
	private JButton attackButton;
	private JButton fortifyButton;
	private JButton skipButton;
	private JPanel cardListPanel;
	private JLabel cardPanelLabel;
	private JList<String> cardList;
	private JScrollPane cardListScroll;
	
	private Country sourceCountry;
	private Country destCountry;


	private ArrayList<Player> players;
	private Board gameboard;
	private Deck cards;
	private int turn;

	// private Deck territories;

	public App() {
		// Create Board
		this.gameboard = new Board();
		createPlayers();
		dealCards();
		assignTerritories();
		System.out.println(players);
		this.turn = 0;
		initGUI();

		// Add players to the playerPanel and update
		for (int i = 0; i < players.size(); i++) {
			playerPanel[i].addPlayer(players.get(i));
			playerPanel[i].updatePanel();
		}
		
		// Test update by removing 7 of player 1's territories
		for (int i = 0; i < 7; i++) {
			Player HUMAN = players.get(0);
			Player AI = players.get(1);
			Country country = HUMAN.getTerritories().get(0);
			// Simulate losing a territory
			HUMAN.loseBattle(country, AI, 3);
		}
		playerPanel[0].updatePanel();
		playerPanel[1].updatePanel();
		mapPanel.update();
		
		//newGame();
		
		mainPanel.addMouseListener(this);
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
	public void dealCards() {
		this.cards = new Deck();
		for (int i = 0; cards.deckSize() > 0; i++) {
			players.get(i % players.size()).getHand().add(cards.draw());
		}
	}

	/** Based on the cards that players have, assign them territories */
	public void assignTerritories() {
		for (Player player : players) {
			for (Continent continent : gameboard.WORLD) {
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
		viewPanel  = new JPanel(new BorderLayout());
		actionPanel = new JPanel(new GridBagLayout());
		selectPanel = new JPanel(new GridBagLayout());
		commandPanel = new JPanel(new BorderLayout());
		selectPanelTitle = new JLabel("Selected Territories");
		sourceCountryLabel = new JLabel("Source");
		sourceCountryPanel = new JPanel();
		sourceCountryImage = new ImageIcon();
		destCountryLabel  = new JLabel("Desination");
		destCountryPanel = new JPanel();
		destCountryImage = new ImageIcon();
		commandPanelTitle = new JLabel("Commands");
		chooseSourceButton = new JButton("Choose Source");
		chooseDestButton = new JButton("Choose Destination");
		draftUnitsButton = new JButton("Draft");
		attackButton = new JButton("Attack");
		fortifyButton = new JButton("Fortify");
		skipButton = new JButton("Skip Turn");
		cardListPanel = new JPanel(new GridBagLayout());
		cardPanelLabel = new JLabel("Hand");
		cardList = new JList<>(new String[] {"Card 1", "Card 2", "Card 3", "Card 4", "Card 5"});
		cardListScroll = new JScrollPane(cardList);
				
		// Add ActionListeners
		chooseSourceButton.addActionListener(this);
		chooseDestButton.addActionListener(this);
		draftUnitsButton.addActionListener(this);
		attackButton.addActionListener(this);
		fortifyButton.addActionListener(this);
		skipButton.addActionListener(this);
		
		// Player Info panel
		//playerInfoPanel.setPreferredSize(new Dimension(150, 0));
		// rightToolbar.setLayout(new GridLayout(4,1,0,15));
		playerInfoPanel.setBorder(LineBorder.createGrayLineBorder());
		playerInfoPanel.setBackground(new Color(0x888888));		
		mainPanel.setBackground(new Color(0x0));
		
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
		addComponent(selectPanel, selectPanelTitle, 0, 0, 1, 1, GridBagConstraints.NORTH);
		addComponent(selectPanel, sourceCountryLabel, 0, 1, 1, 1, CENTER);
		addComponent(selectPanel, sourceCountryPanel, 0, 2, 1, 1, CENTER);
		addComponent(selectPanel, destCountryLabel, 0, 3, 1, 1, CENTER);
		addComponent(selectPanel, destCountryPanel, 0, 4, 1, 1, CENTER);
		commandPanel.add(actionPanel, BorderLayout.CENTER);
		addComponent(actionPanel, commandPanelTitle, 0, 0, 1, 1, GridBagConstraints.NORTH);
		addComponent(actionPanel, chooseSourceButton, 0, 1, 1, 1, CENTER);
		addComponent(actionPanel, chooseDestButton, 0, 2, 1, 1, CENTER);
		addComponent(actionPanel, draftUnitsButton, 0, 3, 1, 1, CENTER);
		addComponent(actionPanel, attackButton, 0, 4, 1, 1, CENTER);
		addComponent(actionPanel, fortifyButton, 0, 5, 1, 1, CENTER);
		commandPanel.add(cardListPanel, BorderLayout.EAST);
		addComponent(cardListPanel, cardPanelLabel, 0, 0, 1, 1, GridBagConstraints.NORTH);
		addComponent(cardListPanel, cardListScroll, 0, 1, 1, 1, CENTER);

		mapWrapper.setBackground(new Color(0xbbccff));
		sourceCountryPanel.setPreferredSize(new Dimension(20, 20));
		destCountryPanel.setPreferredSize(new Dimension(20, 20));
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

	private void newGame() {
		System.out.println();

		Country curr = gameboard.N_AFRICA;
		try {
			Set<Country> neightbors = gameboard.getNeighbors(curr);
			for (Country c : neightbors) {
				System.out.println(c.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	} // End addComponent.

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

	@Override
	public void mouseClicked(MouseEvent e) {
		// JOptionPane.showMessageDialog(this, "Message", "Title",
		// JOptionPane.INFORMATION_MESSAGE);
		players.get(0).setColor(PlayerColors.BLUE.color());
		playerPanel[0].updatePanel();
		System.err.print("Changed");
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
		// TODO Auto-generated method stub
		if (e.getSource() == attackButton) {
			System.out.println("Attack!");
		}
	}
}
