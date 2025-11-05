package resources;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Collections;

public class Blackjack {
	//variables
	private boolean playerAction;
	private boolean dealerAction;
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
		playerAction = true;
		dealerAction = false;

		//deal the cards
		playerHand.add(deck.pop());
		playerHand.add(deck.pop());
		dealerHand.add(deck.pop());
		dealerHand.add(deck.pop());
		dealerHand.get(0).hide();
	}


	 // Precondition: None
	 // Postcondition: Returns a stack shuffled cards
	public Stack<Card> makeShuffledDeck(){
	Stack<Card> deck = new Stack<>();
	   for (Card.Suit suit : Card.Suit.values()){
			for (int value = 1; value <= 13; value++){
				Card card = new Card(value, suit);
				//card.hide();
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

	//pre condition: playerAction is true
	//post condition: gives player another card
	public void playerHit(){
		if (playerAction){
			playerHand.add(deck.pop());
		}
	}

	//pre condition: playerAction is true
	//finishes player's turn and makes it calls method to perform dealer action 
	public void playerStand(){
		if (!playerAction){
			return; 
		}
		playerAction = false;
		dealerPlay();
	}

	//pre condition: playerAction is true and player can afford double
	//post condition: doubles the player bet and gives the player exactly one more card
	public void playerDouble(){
		if (!playerAction || playerBankroll < fixedBet){
			return;
		}
		playerBankroll -= fixedBet;
		potAmount += fixedBet * 2;
		playerHand.add(deck.pop());
		playerAction = false;
		dealerPlay();
	} 

	//pre condition: dealerAction = true
	//post condition: gives dealer more cards until 17-21 or bust
	public void dealerPlay(){
		dealerHand.get(0).show();
		dealerAction = false;
		while (calculateHandValue(dealerHand) < 17){
			dealerHand.add(deck.pop());
		}
	}

	//precondition: playerHand and dealerHand have been initialized
	//postcondition: returns string telling you who won the hand
	public String calculateWinner(){
		int playerValue = calculateHandValue(playerHand);
		int dealerValue = calculateHandValue(dealerHand);

		if (playerValue > 21){
			return "Player Busts. Dealer Wins!";
		}
		if (dealerValue > 21){
			playerBankroll += potAmount;
			return "Dealer Busts. Player Wins!";
		}
		if (playerValue > dealerValue){
			playerBankroll += potAmount;
			return "Player Wins!";
		}
		if (playerValue < dealerValue){
			return "Dealer Wins!";
		}
		
		//if none of those then tie
		playerBankroll  += fixedBet;
		return "Push!";
	}
}


