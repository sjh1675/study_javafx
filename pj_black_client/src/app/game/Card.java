package app.game;

public class Card {

	// VO
	private String deck;
	private String number;
	
	public String getDeck() {
		return deck;
	}

	public void setDeck(String deck) {
		this.deck = deck;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return deck.replace(" ", "");
	}

	
	
	
	
	
}