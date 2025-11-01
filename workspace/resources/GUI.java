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
import java.util.Stack;



public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	Blackjack game;
   	public GUI(Blackjack game){
		this.game= game;
        //Create and set up the window.
       setTitle("Blackjack");
       setSize(900,580);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
       
       // this supplies the background
       try {
		System.out.println(getClass().toString());
		Image blackImg = ImageIO.read(getClass().getResource("background.jpg"));
		setContentPane(new ImagePanel(blackImg));

       }catch(IOException e) {
    	   e.printStackTrace();
       }

	   GridBagLayout layout = new GridBagLayout();
	   setLayout(layout);
	   GridBagConstraints c = new GridBagConstraints();
	   c.fill = GridBagConstraints.BOTH;

	   //top left
	   JPanel dealerArea = new JPanel(new BorderLayout());
	   dealerArea.setOpaque(false);
	   dealerArea.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	   c.gridx = 0;
	   c.gridy = 0;
	   c.gridwidth = 2;
	   c.weightx = 0.7;
	   c.weighty = 0.5;
	   add(dealerArea, c);

	   //top right
	   JPanel deckArea = new JPanel(new BorderLayout());
	   deckArea.setOpaque(false);
	   deckArea.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
	   c.gridx = 2;
	   c.gridy = 0;
	   c.gridwidth = 1;
	   c.weightx = 0.3;
	   c.weighty = 0.5;
		JLayeredPane deckPane = drawPile(game.makeShuffledDeck(), 1);
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
	   c.weighty = 0.5;
	   add(bankrollArea, c);



	   //bottom middle
	   JPanel playerArea = new JPanel(new BorderLayout());
	   playerArea.setOpaque(false);
	   playerArea.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
	   c.gridx = 1;
	   c.gridy = 1;
	   c.gridwidth = 1;
	   c.weightx = 0.5;
	   c.weighty = 0.5;
	   add(playerArea, c);

	   //bottom right
	   JPanel optionArea = new JPanel(new GridLayout(3, 1));
	   optionArea.setOpaque(false);
	   optionArea.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 3));
	   c.gridx = 2;
	   c.gridy = 1;
	   c.gridwidth = 1;
	   c.weightx = 0.3;
	   c.weighty = 0.5;
	   
		//Buttons
		JButton hitOption = new JButton("HIT");
	   	optionArea.add(hitOption, BorderLayout.NORTH);
	   
	   	JButton standOption = new JButton("STAND");
	   	optionArea.add(standOption, BorderLayout.CENTER);
	   
	   	JButton doubleOption = new JButton("DOUBLE");
	   	optionArea.add(doubleOption, BorderLayout.SOUTH);
	   	add(optionArea, c);

	   
	   Stack<Card> test = new Stack<>();
	   test.push(new Card(1, Card.Suit.Spades));
	   test.push(new Card(12, Card.Suit.Hearts));
	   test.push(new Card(5, Card.Suit.Clubs));
	   test.push(new Card(10, Card.Suit.Diamonds));
	   
	   
	   
	   JLayeredPane pile = drawPile(test, 20);
	   dealerArea.add(pile, BorderLayout.CENTER);
	   
	   
	   /* 
	   Stack<Card> deck = new Stack<>();
	   for (Card.Suit suit : Card.Suit.values()){
			for (int value = 1; value <= 13; value++){
				Card card = new Card(value, suit);
				card.hide();
				deck.push(card);
			}
	   }

	   JLayeredPane deckPane = drawPile(deck,1);
	   deckArea.add(deckPane, BorderLayout.CENTER);
	   */

		//deckArea.revalidate();
		//deckArea.repaint();

		//Current Pot
		JLabel pot = new JLabel(Integer.toString(game.getPotAmount()));
		bankrollArea.add(pot);

    	this.setVisible(true);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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