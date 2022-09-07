package app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.game.BlackJack;
import app.game.Card;
import client.Client;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Game implements Initializable {

	@FXML
	private Button start, exit, hit, stay, cSend, rule;
	@FXML
	private TextArea log, Del, Pla;
	@FXML
	private TextField chat;
	@FXML
	private Label player1, player2;
	@FXML
	private ImageView playerImg1, playerImg2, gameTable;
	@FXML
	private HBox uCard, dCard;

	private BlackJack bj;
	private ArrayList<Card> dealerCard;
	private ArrayList<Card> playerCard;

	private int playerScore;
	private int dealerScore;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 게임 실행시 root 호출해서 client한테 연결시켜줌
		// -> textAear에 text 출력
		Root root = Root.getRoot();
		root.clientConn.setGame(this);

		disabledBtn();

		start.setOnAction(e -> {
			isRun();
		});

		rule.setOnMouseClicked(e -> {
			rules();
		});

		exit.setOnAction(e -> {
			Root.getRoot().toEnd();
		});

		hit.setOnMouseClicked(e -> {
			bj.hit();
			insertUCard();
		});

		stay.setOnMouseClicked(e -> {
			bj.stay();
		});

		cSend.setOnAction(e -> {
			String msg = chat.getText();
			Client.send(2, msg);
			chat.clear();
			chat.requestFocus();
		});

		chat.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					cSend.fire();
				}
			}
		});
	}

	private void rules() {
		final Stage stage = new Stage(StageStyle.DECORATED);
		stage.initModality(Modality.WINDOW_MODAL);
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("../page/rules.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent);
		stage.setTitle("게임 룰 설명");
		stage.getIcons().add(new Image("/app/image/blackjack.png"));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	// 여기에 appendText를 생성해서 log에 접근하려고 만듬
	public void appendText(String text) {
		Platform.runLater(() -> {
			log.appendText(text + "\n");
		});

	}

	private void isRun() {
		// 승패 결정 후 게임 화면 초기화
		if (!uCard.getChildren().isEmpty()) {
			for (int i = 0; i < uCard.getChildren().size();) {
				uCard.getChildren().remove(i);
			}
		}
		if (!dCard.getChildren().isEmpty()) {
			for (int i = 0; i < dCard.getChildren().size();) {
				dCard.getChildren().remove(i);
			}
		}

		log.clear();
		
		hit.setDisable(false);
		stay.setDisable(false);

		bj = new BlackJack(log, this);
		createStartCard();
		
		Client.send(1, Client.nickName + "님이 게임을 시작합니다.");
		
		// 시작 후 플레이어 카드 합 표시 
		Pla.setText(String.valueOf("총합 : " + playerScore));
	}

	public void disabledBtn() {
		hit.setDisable(true);
		stay.setDisable(true);
	}

	// 시작버튼을 눌렀을 때 textArea 초기화
	private void createStartCard() {

		Pla.clear();
		Del.clear();

		dealerCard = bj.dealerCard;
		String dDeck1 = dealerCard.get(0).getDeck();
		dDeck1 = dDeck1.replaceAll(" ", "");

		ImageView dCard1;
		ImageView dCard2;

		String path = "../cardimg/" + dDeck1 + ".png";
		URL url = getClass().getResource(path);
		dCard1 = new ImageView(url.toString());
		dCard1.setPreserveRatio(true); // 가로세로 비율 유지
		dCard.getChildren().add(dCard1); // HBox에 자식요소로 추가

		path = "../cardimg/cardBack.png"; // ex)DIAMOND1.png
		url = getClass().getResource(path);
		dCard2 = new ImageView(url.toString());
		dCard2.setPreserveRatio(true);
		dCard.getChildren().add(dCard2);

		ArrayList<Card> playerCard = bj.playerCard; // 값을 가져오기위한 인스턴스 생성
		String deck1 = playerCard.get(0).getDeck();// 플레이어 덱의 카드리스트 첫번째카드(index 0번)
		String deck2 = playerCard.get(1).getDeck();

		deck1 = deck1.replaceAll(" ", "");
		deck2 = deck2.replaceAll(" ", "");

		ImageView card1;
		ImageView card2;

		String userPath = "../cardimg/" + deck1 + ".png"; // ex)DIAMOND1.png
		URL userUrl = getClass().getResource(userPath);
		card1 = new ImageView(userUrl.toString());
		card1.setPreserveRatio(true); // 가로세로 비율 유지
		uCard.getChildren().add(card1); // HBox에 자식요소로 추가

		userPath = "../cardimg/" + deck2 + ".png";
		userUrl = getClass().getResource(userPath);
		card2 = new ImageView(userUrl.toString());
		card2.setPreserveRatio(true);
		uCard.getChildren().add(card2);
	}

	// 승패 결정 할 때 딜러 카드 open
	public void CardOpen() {
		disabledBtn();

		dealerCard = bj.dealerCard;
		ImageView dCardAdd;
		for (int i = 0; i < dCard.getChildren().size();) {
			dCard.getChildren().remove(i);
		}
		for (int i = 0; i < dealerCard.size(); i++) {
			String addDCard = dealerCard.get(i).getDeck();
			addDCard = addDCard.replaceAll(" ", "");
			String path = "../cardimg/" + addDCard + ".png";

			URL url = getClass().getResource(path);
			dCardAdd = new ImageView(url.toString());
			dCard.getChildren().add(dCardAdd);
		}

		Pla.setText(String.valueOf("총합 : " + playerScore));
		Del.setText(String.valueOf("총합 : " + dealerScore));
	}
	
	// 총합 점수 저장
	public void setScore(int player, int dealer) {
		playerScore = player;
		dealerScore = dealer;
	}

	// hit 버튼시 user한테 카드 추가
	private void insertUCard() {
		playerCard = bj.playerCard;
		int count = playerCard.size() - 1;
		String addCard = playerCard.get(count).getDeck(); // 마지막 인덱스 불러오기
		addCard = addCard.replaceAll(" ", "");

		ImageView cardAdd;
		String path = "../cardimg/" + addCard + ".png";
		URL url = getClass().getResource(path);
		cardAdd = new ImageView(url.toString());
		uCard.getChildren().add(cardAdd);
		// hit 클릭시 플레이어 카드 합 표시
		Pla.setText(String.valueOf("총합 : "+ playerScore));
	}

}
