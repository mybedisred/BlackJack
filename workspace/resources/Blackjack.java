package resources;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Collections;
import javax.swing.Timer;


public class Blackjack {
	//variables
	private boolean isPlayerTurn;
	private int playerBankroll = 500; 
	private int potAmount = 0;
	private static int fixedBet = 20;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> dealerHand;
	private Stack<Card> deck; 

	//constructor
	public Blackjack(){
		newRound();
	}

	//getters and setters
	public int getPlayerBankroll(){
		return playerBankroll;
	}
	
	public int getPotAmount(){
		return potAmount;
	}
	
	public ArrayList<Card> getPlayerHand(){
		return playerHand;
	}

	public ArrayList<Card> getDealerHand(){
		return dealerHand;
	}

	public Stack<Card> getDeck(){
		return deck;
	}

	
	//the part of your program that's in charge of game rules goes here.
	
	//precondition: instance variables have been initialized
	//postcondition: starts a new round of blackjack with an intial deal
	public void newRound(){
		deck = makeShuffledDeck();
		playerHand = new ArrayList<>();
		dealerHand = new ArrayList<>();
		potAmount = fixedBet * 2;
		playerBankroll -= fixedBet;
		isPlayerTurn = true;

		//deal the cards
		Card playerFirstCard = deck.pop();
		playerFirstCard.show();
		playerHand.add(playerFirstCard);
		Card playerSecondCard = deck.pop();
		playerSecondCard.show();
		playerHand.add(playerSecondCard);
		Card dealerFirstCard = deck.pop();
		dealerHand.add(dealerFirstCard);
		Card dealerSecondCard = deck.pop();
		dealerSecondCard.show();
		dealerHand.add(dealerSecondCard);

		/*playerHand.add(deck.pop());
		playerHand.add(deck.pop());
		dealerHand.add(deck.pop());
		dealerHand.add(deck.pop());
		dealerHand.get(0).hide();
		*/
	}


	 // Precondition: None
	 // Postcondition: Returns a stack shuffled cards
	public Stack<Card> makeShuffledDeck(){
		Stack<Card> deck = new Stack<>();
	   	for (Card.Suit suit : Card.Suit.values()){
			for (int value = 1; value <= 13; value++){
				Card card = new Card(value, suit);
				card.hide();
				deck.push(card);
			}
	   }
	   Collections.shuffle(deck);
	   return deck;
	}

	//Precondition: None
	//Postcondition: Returns value of a player/dealer's hand
	public int calculateHandValue(ArrayList<Card> hand){
		int total = 0;
		int aceCount = 0;

		for (Card card : hand){
			int value = card.value;
			if (value > 10){
				value = 10;
			}
			if (value == 1 ){
				aceCount++;
			}
			total += value;
		}

		while (aceCount > 0 && total + 10 <= 21){
			total += 10;
			aceCount --;
		}

		return total;
	}

	//pre condition: isPlayerTurn is true
	//post condition: gives player another card
	public void playerHit(){
		/*if (isPlayerTurn){
			playerHand.add(deck.pop().hide());
		}
		*/
		if (isPlayerTurn){
			Card hitCard = deck.pop();
			hitCard.show();
			playerHand.add(hitCard);
		}
	}

	//pre condition: isPlayerTurn is true
	//finishes player's turn and makes it calls method to perform dealer action 
	public void playerStand(){
		if (isPlayerTurn){
			isPlayerTurn = false;
			dealerPlay();
		}
	}

	//pre condition: isPlayerTurn is true and player can afford double
	//post condition: doubles the player bet and gives the player exactly one more card
	public void playerDouble(){
		if ((isPlayerTurn) && (playerBankroll > fixedBet)){
			playerBankroll -= fixedBet;
			potAmount += fixedBet * 2;
			//playerHand.add(deck.pop());
			Card doubleCard = deck.pop();
			doubleCard.show();
			playerHand.add(doubleCard);
			isPlayerTurn = false;
			dealerPlay();
		}
	} 

	//pre condition: isPlayerTurn == false
	//post condition: gives dealer more cards until 17-21 or bust
	public void dealerPlay(){
		dealerHand.get(0).show();

		while (calculateHandValue(dealerHand) < 17){
			/*try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			*/
			
			Card dealerCard = deck.pop();
			dealerCard.show();
			dealerHand.add(dealerCard);
			
		}
	}	
		
	

	//precondition: playerHand and dealerHand have been initialized
	//postcondition: returns string telling you who won the hand
	public String calculateWinner(){
		int playerValue = calculateHandValue(playerHand);
		int dealerValue = calculateHandValue(dealerHand);
		
		if (playerValue == 21 && playerHand.size() == 2 && dealerValue != 21){
			playerBankroll += potAmount;
			potAmount = 0;
			return "Player Wins! Player has BLACKJACK!";
		}
		if (dealerValue == 21 && dealerHand.size() == 2 && playerValue != 21){
			dealerHand.get(0).show();
			potAmount = 0;
			return "Dealer Wins! Dealer has BLACKJACK!";
		}

		
		if (playerValue > 21){
			isPlayerTurn = false;
			potAmount = 0;
			return "Player Busts. Dealer Wins!";
		}
		else if (!isPlayerTurn) {
			
			if (dealerValue > 21){
				playerBankroll += potAmount;
				potAmount = 0;
				return "Dealer Busts. Player Wins!";
			}
			else if (dealerValue >= 17 && playerValue > dealerValue){
				playerBankroll += potAmount;
				potAmount = 0;
				return "Player Wins!";
			}
			else if (dealerValue >= 17 && playerValue < dealerValue){
				potAmount = 0;
				return "Dealer Wins!";
			}
			else {
				//if none of those then tie
				playerBankroll += fixedBet;
				potAmount = 0;
				return "Push!";
			}
		}
		else {
			return null;
		}
	}
}


