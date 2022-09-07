package app.game;

import java.util.ArrayList;

public class Player { 
	
	public Card hit(Dealer dealer, ArrayList<Card> deck) {
		Card card = dealer.getCard(deck);
		return card;
	}
}
