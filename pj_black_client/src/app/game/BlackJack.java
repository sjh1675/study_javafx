package app.game;

import java.util.ArrayList;

import app.controller.Game;
import client.Client;
import javafx.scene.control.TextArea;

public class BlackJack {

	Dealer dealer;
	Player player;
	Rule rule;

	// 카드 덱
	ArrayList<Card> deck;
	// 딜러카드
	public ArrayList<Card> dealerCard;
	// 플레이어 카드
	public ArrayList<Card> playerCard;
	TextArea area;
	Game gameCont;

	int playerCardSum;
	int dealerCardSum;
	String text;
	private String text2;
	private String text3;

	public BlackJack(TextArea log, Game gameCont) {
		dealer = new Dealer();
		player = new Player();
		rule = new Rule(log, gameCont);

		deck = dealer.setCard();
		dealerCard = new ArrayList<Card>();
		playerCard = new ArrayList<Card>();

		area = log;
		this.gameCont = gameCont;

		for (int i = 1; i <= 2; i++) {
			dealerCard.add(dealer.getCard(deck));
			playerCard.add(dealer.getCard(deck));
		}

		// 딜러 카드합계
		dealerCardSum = rule.cardSum("dealer", dealerCard);
		// 플레이어 카드합계
		int psi = (playerCard.size());

		playerCardSum = rule.cardSum(Client.nickName, playerCard);
		String uCard = playerCard.get(0).getDeck() + " , " + playerCard.get(1).getDeck();
		for (int i = 0; i < psi; i++) {
			if (playerCard.get(i).getDeck().contains("ACE") && playerCardSum < 22) {
				text2 = Client.nickName + " 카드 총합 : " + (playerCardSum - 10) + "or" + playerCardSum + "\n";
				text = "당신의 받은 카드 : " + uCard + "\n";
				// 시작 클릭시 플레이거 카드 합 표시
				gameCont.setScore(playerCardSum, dealerCardSum);
			} else {
				text = "당신의 받은 카드 : " + uCard + "\n";
				text2 = Client.nickName + " 카드 총합 : " + playerCardSum + "\n";
				// 시작 클릭시 플레이거 카드 합 표시
				gameCont.setScore(playerCardSum, dealerCardSum);
			}
		}
		area.appendText(text);
		area.appendText(text2);
		System.out.println("딜러 카드합" + dealerCardSum);
		System.out.println("플레이어 카드합" + playerCardSum);

		// 딜러카드합이 21일경우 블랙잭
		if (dealerCardSum == 21) {
			area.appendText("딜러 BLACK JACK! \n");
			stay();
			return;
			// 플레이어 카드합이 21일경우 블랙잭
		} else if (playerCardSum == 21) {
			area.appendText("플레이어 BLACK JACK! \n");
			stay();
		}
	}

	public void hit() {

		// 카드 받기
		Card cardAdd = player.hit(dealer, deck);

		playerCard.add(cardAdd);
		String text = "새로받은 카드 : " + playerCard.get((playerCard.size()) - 1).getDeck() + "\n";
		area.appendText(text);

		// 추가한 덱의 합계
		playerCardSum = rule.cardSum(Client.nickName, playerCard);

		// 현재카드 합
		for (int i = 0; i < playerCard.size(); i++) {
			if (playerCard.get(i).getDeck().contains("ACE") && playerCardSum < 22) {
				playerCardSum -= 10;
				text3 = "현재" + Client.nickName + " 카드 총합 : " + (playerCardSum - 10) + "or" + playerCardSum + "\n";
			} else {
				text3 = "현재" + Client.nickName + " 카드 총합 : " + playerCardSum + "\n";
			}
		}
		area.appendText(text3);

		gameCont.setScore(playerCardSum, dealerCardSum);
		// 플레이어가 버스트하면 종료
		if (rule.isBust(Client.nickName, playerCardSum)) {
			System.out.println(" Black Jack : 0 ");
			gameCont.setScore(playerCardSum, dealerCardSum);
			area.appendText("dealer 승 \n");
			gameCont.disabledBtn();
			gameCont.CardOpen();
			return;
		}

		// 플레이어 카드합이 21일경우 강제 stay
		if (playerCardSum == 21) {
			stay();
			return;
		}

		// 플레이어의 턴이 끝난 후 딜러의 카드합이 17 미만이면 17이상이 될 때까지 뽑기
		while (dealerCardSum < 17) {
			dealerCard.add(dealer.getCard(deck));
			dealerCardSum = rule.cardSum("dealer", dealerCard);
		}
	}

	public void stay() {

		// 현재카드 합
		for (int i = 0; i < playerCard.size(); i++) {
			if (playerCard.get(i).getDeck().contains("ACE") && playerCardSum < 22) {
				playerCardSum -= 10;
				text3 = "현재" + Client.nickName + " 카드 총합 : " + (playerCardSum - 10) + "or" + playerCardSum + "\n";
			} else {
				text3 = "현재" + Client.nickName + " 카드 총합 : " + playerCardSum + "\n";
			}
		}
		
		// 딜러 카드 합계
		dealerCardSum = rule.getCardSum(dealerCard);
		if (!(dealerCardSum == 21)) {

			// 딜러의 카드합이 17 미만이면 17이상이 될 때까지 뽑기
			while (dealerCardSum < 17 && !(playerCardSum == 21)) {
				dealerCard.add(dealer.getCard(deck));
				dealerCardSum = rule.cardSum("dealer", dealerCard);
			}
		}
		// 17미만 일 때 딜러가 카드를 뽑다가 21이 넘어가면 player 승리
		if (rule.isBust("dealer", dealerCardSum)) {
			gameCont.setScore(playerCardSum, dealerCardSum);
			area.appendText(Client.nickName + " 승 \n");
			gameCont.disabledBtn();
			gameCont.CardOpen();
		} else {
			// 딜러 카드의 합이 17~21인 경우 승자를 정해준다
			rule.winner(playerCardSum, dealerCardSum);
			area.appendText("딜러카드의 합계 : " + dealerCardSum + "\n");
		}
	}
}
