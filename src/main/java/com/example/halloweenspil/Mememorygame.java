package com.example.halloweenspil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane();
        root.setPrefSize(600, 600);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Card c = (Card) e.getSource();

                // Hvis begge kort allerede er vendt, eller det samme kort er klikket igen, skal vi ikke gøre noget.
                if (firstCard == null || secondCard == null || firstCard == c) {
                    if (c != firstCard) {
                        c.flip();
                        if (firstCard == null) {
                            firstCard = c;
                        } else {
                            secondCard = c;

                            // Tjek om kortene matcher (f.eks. b1 og b11)
                            if (firstCard.getId() == secondCard.getId() + 10 || secondCard.getId() == firstCard.getId() + 10) {
                                // Kortene matcher, fjern dem fra scenen.
                                root.getChildren().remove(firstCard);
                                root.getChildren().remove(secondCard);
                                firstCard = null;
                                secondCard = null;
                            } else {
                                // Kortene matcher ikke, flip dem tilbage til bagsiden efter en kort forsinkelse.
                                Timeline timeline = new Timeline(
                                        new KeyFrame(Duration.seconds(1), event -> {
                                            firstCard.flip();
                                            secondCard.flip();
                                            firstCard = null;
                                            secondCard = null;
                                        })
                                );
                                timeline.setCycleCount(1);
                                timeline.play();
                            }
                        }
                    }
                }
            }
        };
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
        //resetButton.setOnAction(event -> reset());
        root.getChildren().add(resetButton);


    }

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
        public void placeRandomCards() {
            List<Card> cardList = new ArrayList<>();

            // Fyld cardList med alle kortene
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




    public static void main(String[] args) {
        launch();
    }
}