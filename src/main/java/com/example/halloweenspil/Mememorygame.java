package com.example.halloweenspil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mememorygame extends Application {

    private Card[][] cards;
    private Pane root;

    private Card firstCard = null;
    private Card secondCard = null;

    private int score = 0;

    private int clickCount =0;
    private Label scoreLabel;

    private EventHandler<MouseEvent> eventHandler;

    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane();
        root.setPrefSize(600, 600);
        //opstiller antal forsøg tavle oppe i venstre hjørne
        VBox scoreBox = new VBox();
        scoreLabel = new Label("Antal forsøg: 0");
        scoreBox.getChildren().add(scoreLabel);
        scoreBox.setTranslateX(10);
        scoreBox.setTranslateY(10);
        root.getChildren().add(scoreBox);
        eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Card c = (Card) e.getSource();


                // Tjekker om begge kort allerede er vendt, eller det samme kort er klikket igen, skal vi ikke gøre noget.
                if (firstCard == null || secondCard == null || firstCard == c) {
                    if (c != firstCard) {
                        c.flip();
                        if (firstCard == null) {
                            firstCard = c;
                        } else {
                            secondCard = c;


                            // fjerner kortene hvis de passer sammen (virker ikke) (alle har ID = null)
                            if (firstCard.getId() == secondCard.getId() + 10 || secondCard.getCardId() == firstCard.getCardId() + 10) {
                                root.getChildren().remove(firstCard);
                                root.getChildren().remove(secondCard);
                                firstCard = null;
                                secondCard = null;

                            } else {
                                //Vender kortene hvis de ikke passer sammen
                                Timeline timeline = new Timeline(
                                        new KeyFrame(Duration.seconds(1), event -> {
                                            firstCard.flip();
                                            secondCard.flip();
                                            firstCard = null;
                                            secondCard = null;
                                        })
                                );
                                //tæller hver gang man har vendt 2 kort
                                timeline.setCycleCount(1);
                                timeline.play();
                                if (clickCount < 20) {
                                    clickCount++;
                                    scoreLabel.setText("Antal forsøg: " + clickCount);}
                                else{
                                    resetGame();
                                    }
                                }
                            }
                        }
                    }
                }

        };
        //bygger vores scene
        Scene scene = new Scene(root);

        stage.setTitle("Halloweenspil");
        stage.setScene(scene);
        buildGame(eventHandler);
        placeRandomCards();
        stage.show();

        Button resetButton = new Button();
        resetButton.setTranslateX(500);
        resetButton.setTranslateY(10);
        resetButton.setText("Restart");
        resetButton.setOnAction(event -> resetGame());
        root.getChildren().add(resetButton);


    }
    //sætter kortene ind på scenen
    public void buildGame(EventHandler<MouseEvent> eventHandler) {
        cards = new Card[4][5];
        int id = 1;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                if (id <= 20) {
                    cards[i][j] = new Card(id++, i, j, this);
                    root.getChildren().add(cards[i][j]);
                    cards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                }
            }
    }
    //placere vores kort tilfæligt
    public void placeRandomCards() {
        List<Card> cardList = new ArrayList<>();


            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    cardList.add(cards[i][j]);
                }
            }

            Collections.shuffle(cardList);

            int index = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    cards[i][j] = cardList.get(index++);
                    cards[i][j].setX(j * 100);
                    cards[i][j].setY(i * 100);
                }
            }
        }
    private void resetGame(){
        score = 0;
        clickCount = 0;
        scoreLabel.setText("Antal forsøg: 0");
        root.getChildren().clear(); // Fjern alle kort fra scenen
        buildGame(eventHandler); // Opret spillet igen
        placeRandomCards(); // Placer kort tilfældigt

        // Tilføj scoretavlen og reset-knappen tilbage til root
        VBox scoreBox = new VBox();
        scoreBox.getChildren().add(scoreLabel);
        scoreBox.setTranslateX(10);
        scoreBox.setTranslateY(10);
        root.getChildren().add(scoreBox);

        Button resetButton = new Button();
        resetButton.setTranslateX(500);
        resetButton.setTranslateY(10);
        resetButton.setText("Restart");
        resetButton.setOnAction(event -> resetGame());
        root.getChildren().add(resetButton);
    }


    public static void main(String[] args) {
        launch();
    }
}