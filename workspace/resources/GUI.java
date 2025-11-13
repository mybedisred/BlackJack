/*
 * Author: Spencer Gilcrest
 * Date: 10/22/25
 * This creates the GUI for our black jack game
 */
package resources;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;



public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private Blackjack game;

	private JButton hitOption;
	private JButton standOption;
	private JButton doubleOption;
	
	private JLabel playerBankDisplay;
	private JLabel potDisplay;

	private JPanel dealerArea;
	private JPanel deckArea;
	private JPanel playerArea;

	private JButton nextRoundButton;
	private JLabel resultLabel;

   	public GUI(Blackjack game){
		this.game = game;
		//game.newRound(); //Starts a round of BlackJack
		
		//Create and set up the window.
       	setTitle("Blackjack");
       	setSize(900,580);
       	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       	// this supplies the background
       	try {
			System.out.println(getClass().toString());
			Image backImg = ImageIO.read(getClass().getResource("background.jpg"));
			setContentPane(new ImagePanel(backImg));

       	}catch(IOException e) {
    	   	e.printStackTrace();
       	}

	   	GridBagLayout layout = new GridBagLayout();
	   	GridBagConstraints c = new GridBagConstraints();
	   	c.fill = GridBagConstraints.BOTH;
	   	setLayout(layout);

	   	//top left
	   	dealerArea = new JPanel(new BorderLayout());
	   	dealerArea.setOpaque(false);
	   	dealerArea.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	   	c.gridx = 0;
	   	c.gridy = 0;
	   	c.gridwidth = 2;
	   	c.weightx = 0.7;
	   	c.weighty = 0.5;
	   	add(dealerArea, c);

		//ADD TO UPDATE
		dealerArea.add( drawHand( listToStack(game.getDealerHand()), 50 ) );

	   	//top right
	   	deckArea = new JPanel(new BorderLayout());
	   	deckArea.setOpaque(false);
	   	deckArea.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
	   	c.gridx = 2;
	   	c.gridy = 0;
	   	c.gridwidth = 1;
	   	c.weightx = 0.3;
	   	c.weighty = 0.5;
		JLayeredPane deckPane = drawPile(game.getDeck(), 1);
		deckArea.add(deckPane, BorderLayout.CENTER);
	   	add(deckArea, c);

	   	//bottom left
	   	JPanel bankrollArea = new JPanel(new BorderLayout());
	   	bankrollArea.setOpaque(false);
	   	bankrollArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
	   	c.gridx = 0;
	   	c.gridy = 1;
	   	c.gridwidth = 1;
	   	c.weightx = 0.2;
	   	c.weighty = 0.25;
	   	add(bankrollArea, c);

		

	   	potDisplay = new JLabel("Pot: $" + Integer.toString(game.getPotAmount())); //ADD TO UPDATE

	   	potDisplay = new JLabel(Integer.toString(game.getPotAmount())); //ADD TO UPDATE
		potDisplay.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		potDisplay.setOpaque(true);
	   	potDisplay.setBackground(new Color(50, 204, 0));
		potDisplay.setForeground(Color.WHITE);
		potDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 6));

	   	
		//Positioning
		potDisplay.setHorizontalAlignment(JLabel.CENTER);
		potDisplay.setVerticalAlignment(JLabel.CENTER);

	   	bankrollArea.add(potDisplay, BorderLayout.NORTH);

	   	playerBankDisplay = new JLabel( "Bank: $" + Integer.toString(game.getPlayerBankroll())); //ADD TO UPDATE

		//Style
	   	playerBankDisplay.setOpaque(true);
		playerBankDisplay.setFont(new Font(Font.DIALOG, Font.BOLD,20));
	   	playerBankDisplay.setBackground(new Color(50, 204, 0));
		playerBankDisplay.setForeground(Color.WHITE);
		playerBankDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 6));

	   	
		//Positioning
		playerBankDisplay.setHorizontalAlignment(JLabel.CENTER);
		playerBankDisplay.setVerticalAlignment(JLabel.CENTER);

	   	bankrollArea.add(playerBankDisplay, BorderLayout.SOUTH);

	   	//bottom middle
	   	playerArea = new JPanel(new BorderLayout());
	   	playerArea.setOpaque(false);
	   	playerArea.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
	   	c.gridx = 1;
	   	c.gridy = 1;
	   	c.gridwidth = 1;
	   	c.weightx = 0.75;
	   	c.weighty = 0.25;
	   	this.add(playerArea, c);

		playerArea.add( drawHand( listToStack(game.getPlayerHand()), 50 ) );

	   	//bottom right
	   	JPanel optionArea = new JPanel(new GridLayout(3, 1));
	   	optionArea.setOpaque(false);
	   	optionArea.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 3));
	   	c.gridx = 2;
	   	c.gridy = 1;
	   	c.gridwidth = 1;
	   	c.weightx = 0.3;
	   	c.weighty = 0.25;
	   	add(optionArea, c);

		//Buttons
		hitOption = new JButton("HIT");
		hitOption.setFont(new Font("SansSerif", Font.BOLD, 20));
		hitOption.setBackground(new Color(102, 204, 0, 80));
		hitOption.setForeground(Color.WHITE);
		hitOption.addMouseListener(this);
	   	optionArea.add(hitOption, BorderLayout.NORTH);
	   
	   	standOption = new JButton("STAND");
		standOption.setFont(new Font("SansSerif", Font.BOLD, 20));
		standOption.setBackground(new Color(0, 102, 204, 80));
		standOption.setForeground(Color.WHITE);
		standOption.addMouseListener(this);
	   	optionArea.add(standOption, BorderLayout.CENTER);
	   
	   	doubleOption = new JButton("DOUBLE");
		doubleOption.setFont(new Font("SansSerif", Font.BOLD, 20));
		doubleOption.setBackground(new Color(204, 102, 0, 80));
		doubleOption.setForeground(Color.WHITE);
		doubleOption.addMouseListener(this);
	   	optionArea.add(doubleOption, BorderLayout.SOUTH);

		//tells you result of game
		resultLabel = new JLabel("", JLabel.CENTER);
		resultLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		resultLabel.setForeground(Color.WHITE);
		resultLabel.setOpaque(false);
		resultLabel.setVisible(false);
		dealerArea.add(resultLabel, BorderLayout.SOUTH); 
		
		//starts the next round
		nextRoundButton = new JButton("NEXT ROUND");
		nextRoundButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		nextRoundButton.setBackground(new Color(150, 150, 150));
		nextRoundButton.setForeground(Color.WHITE);
		nextRoundButton.addMouseListener(this);
		deckArea.add(nextRoundButton, BorderLayout.SOUTH);
		nextRoundButton.setVisible(false);




    	
		this.setVisible(true);
    }

	private void update() {
		String gameResult = game.calculateWinner();
		//See if player lost
		if (gameResult != null){
			System.out.println(gameResult);
			hitOption.setEnabled(false);
			standOption.setEnabled(false);
			doubleOption.setEnabled(false);

			resultLabel.setText(gameResult);
			resultLabel.setVisible(true);
			nextRoundButton.setVisible(true);
		}

		

		//Update Player's Hand
		playerArea.removeAll();
		playerArea.add( drawHand( listToStack(game.getPlayerHand()), 50) );

		//Update Dealer's Hand
		dealerArea.removeAll();
		dealerArea.add( drawHand( listToStack(game.getDealerHand()), 50) );

		/* 
		try {
			Thread.sleep(2000);
			dealerArea.add( drawHand( listToStack(game.getDealerHand()), 50) );
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
			*/
		
		
		
		
		dealerArea.add(resultLabel, BorderLayout.SOUTH);

		//Pot Amount Label Display
		potDisplay.setText("Pot: $" + Integer.toString(game.getPotAmount()));

		//Player Bankroll Label Display
		playerBankDisplay.setText("Bank: $" + Integer.toString(game.getPlayerBankroll()));

		updateButtonOpacity();

		System.out.println("Player's Hand's value is: " + game.calculateHandValue(game.getPlayerHand()));
		System.out.println("Dealer's Hand's value is: " + game.calculateHandValue(game.getDealerHand()));

		this.revalidate();
		this.repaint();
	}

	//Precondition: hitOption, standOption, and doubleOption have all been initialized
	//Postcondition: Resets hitOption, standOption, and doubleOption buttons background so the opacity stays the same
	private void updateButtonOpacity() {
		//Update Button Opacity
		hitOption.setBackground(new Color(102, 184, 0, 80));
		standOption.setBackground(new Color(0, 102, 204, 80));
		doubleOption.setBackground(new Color(204, 102, 0, 80));
		this.repaint();
	}

	public JLayeredPane drawPile(Stack<Card> cardStack, int overlap){
			Object[] cards = cardStack.toArray();
			JLayeredPane pile = new JLayeredPane();
			int cardWidth = 100;
			int cardHeight = 145;
			
			for (int i = 0; i < cards.length; i++){
				Card card = (Card) cards[i];
				card.setBounds((overlap * i)/2, (overlap * i)/3, cardWidth, cardHeight);
				pile.add(card, Integer.valueOf(i));
			}

			pile.setOpaque(false);
			return pile;
	   }

	   public JLayeredPane drawHand(Stack<Card> cardStack, int overlap){
			Object[] cards = cardStack.toArray();
			JLayeredPane pile = new JLayeredPane();
			int cardWidth = 100;
			int cardHeight = 145;
			
			for (int i = 0; i < cards.length; i++){
				Card card = (Card) cards[i];
				card.setBounds((overlap * i)/2, (0), cardWidth, cardHeight);
				pile.add(card, Integer.valueOf(i));
			}

			pile.setOpaque(false);
			return pile;
	   }

	//Precondition: None
	//PostCondition: Converts ArrayList of Cards to a stack of cards
	private Stack<Card> listToStack(ArrayList<Card> list) {
		Stack<Card> result = new Stack<Card>();
		for (Card card : list){
			result.push(card);
		}
		return result;
	}



	@Override
	public void mouseDragged(MouseEvent arg0) {
		//TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getSource() instanceof JButton) {
			updateButtonOpacity();
		}
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		if (arg0.getSource() instanceof JButton) {
			updateButtonOpacity();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		if (arg0.getSource() instanceof JButton){ //Checks if button was pressed
			JButton thisButton = (JButton)(arg0.getSource());
			//Checks which button was pressed
			if (thisButton == hitOption) { 
				game.playerHit();
				System.out.println("Player picked HIT");
			}
			else if (thisButton == standOption) {
				game.playerStand();
				System.out.println("Player picked STAND");
			}
			else if (thisButton == doubleOption) {
				game.playerDouble();
				System.out.println("Player picked DOUBLE");
			}
			else if (thisButton == nextRoundButton){
				game.newRound();
				hitOption.setEnabled(true);
				standOption.setEnabled(true);
				doubleOption.setEnabled(true);

				resultLabel.setVisible(false);
				dealerArea.remove(resultLabel);
				nextRoundButton.setVisible(false);
			}
		}

		update(); //Updates screen after clicking on anything with a mouselistener
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}