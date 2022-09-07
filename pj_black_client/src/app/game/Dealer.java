package app.game;

import java.util.ArrayList;

public class Dealer {

	public ArrayList<Card> setCard() {

		ArrayList<Card> deck = new ArrayList<Card>();
		String[] suit = { "HEART ", "DIAMOND ", "CLOVER ", "SPADE " };
		String[] number = { "ACE", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		int cnt = 0;
		for (int i = 0; i < suit.length; i++) {
			for (int j = 0; j < number.length; j++) {
				Card card = new Card();
				card.setDeck(suit[i] + number[j]);
				card.setNumber(number[j]);
				deck.add(cnt, card);
				cnt++;
			}
		}
		return deck;
	}

	// 패 돌리기
	public Card getCard(ArrayList<Card> deck) {
		// deck = 0~51 총 52장
		int num = (int) (Math.random() * deck.size());
		Card card = deck.get(num);
		deck.remove(num);

		return card;
	}

}
