import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class App extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JFrame frame;

	// GUI Components
	private final int CENTER = GridBagConstraints.CENTER;
	private static int WIDTH = 1000, HEIGHT = 700;

	// Menu
	private JMenuBar menubar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem quit = new JMenuItem("Quit");
	private JMenu editMenu = new JMenu("Edit");
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem about = new JMenuItem("About");

	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel playerInfoPanel;
	private MapPanel mapPanel;

	private PlayerPanel[] playerPanel = new PlayerPanel[3];

	private JButton attackButton;

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
		frame = new JFrame("RISK");

		initMenu();
		initComponents();

		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		frame.add(mainPanel);

		initMapPanel();
		initPlayerInfoPanel();
		this.setBackground(new Color(0x000000));

		attackButton = new JButton("Attack");
		attackButton.setPreferredSize(new Dimension(80, 40));
	}

	private void initPlayerInfoPanel() {
		// creates control panel
		playerInfoPanel = new JPanel();
		playerInfoPanel.setPreferredSize(new Dimension(150, 0));
		// rightToolbar.setLayout(new GridLayout(4,1,0,15));
		playerInfoPanel.setLayout(new GridBagLayout());
		playerInfoPanel.setBorder(LineBorder.createGrayLineBorder());
		playerInfoPanel.setBackground(new Color(0x888888));

		// Initialize Player Panels
		playerPanel[0] = new PlayerPanel();
		playerPanel[1] = new PlayerPanel();
		playerPanel[2] = new PlayerPanel();

		addComponent(playerInfoPanel, playerPanel[0], 0, 0, 1, 1, CENTER);
		addComponent(playerInfoPanel, playerPanel[1], 0, 1, 1, 1, CENTER);
		addComponent(playerInfoPanel, playerPanel[2], 0, 2, 1, 1, CENTER);
		mainPanel.add(playerInfoPanel, BorderLayout.LINE_START);
	}

	private void initMapPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setBackground(new Color(0xaabbff));
		mapPanel = new MapPanel();
		mapPanel.setBoard(gameboard);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		addComponent(centerPanel, mapPanel, 0, 0, 1, 1, GridBagConstraints.CENTER);
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
		frame.setJMenuBar(menubar);
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
}
