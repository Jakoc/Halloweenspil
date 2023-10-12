package com.example.halloweenspil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        root.setPrefSize(600, 450);

        //opstiller en knappe som hedder regler som viser en alert box
        Alert rulesAlert = regler();
        Button rulesButton = new Button("Regler");
        rulesButton.setOnAction(e -> rulesAlert.showAndWait());
        rulesButton.setTranslateX(430);
        rulesButton.setTranslateY(10);
        root.getChildren().add(rulesButton);
        //opstiller antal forsøg tavle oppe i venstre hjørne
        VBox scoreBox = new VBox();
        scoreLabel = new Label("Antal forkerte: 0");
        scoreBox.getChildren().add(scoreLabel);
        scoreBox.setTranslateX(10);
        scoreBox.setTranslateY(10);
        root.getChildren().add(scoreBox);

        eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Card c = (Card) e.getSource();
                int cardId = c.getCardId();
                //viser id'et på kortene i terminalen
                System.out.println("Kortets ID er: " + cardId);


                // Tjekker om begge kort allerede er vendt, eller det samme kort er klikket igen, skal vi ikke gøre noget.
                if (firstCard == null || secondCard == null || firstCard == c) {
                    if (c != firstCard) {
                        c.flip();
                        if (firstCard == null) {
                            firstCard = c;
                        } else {
                            secondCard = c;


                            // fjerner kortene hvis de passer sammen
                            if (firstCard.getCardId() == secondCard.getCardId() + 10 || secondCard.getCardId() == firstCard.getCardId() + 10) {
                                Timeline removeTimeline = new Timeline(
                                        new KeyFrame(Duration.seconds(1), event -> {
                                            root.getChildren().remove(firstCard);
                                            root.getChildren().remove(secondCard);
                                            firstCard = null;
                                            secondCard = null;

                                            // Tjek om der ikke er flere kort tilbage
                                            if (areAllCardsRemoved()) {
                                                // Alle kort er fjernet, opdater scoreteksten
                                                scoreLabel.setText("Du har vundet! Antal forkerte: " + clickCount);
                                            }
                                        })
                                );
                                removeTimeline.setCycleCount(1);
                                removeTimeline.play();
                            } else {
                                //Vender kortene hvis de ikke passer sammen
                                Timeline flipBackTimeline = new Timeline(
                                        new KeyFrame(Duration.seconds(1), event -> {
                                            firstCard.flip();
                                            secondCard.flip();
                                            firstCard = null;
                                            secondCard = null;
                                        })
                                );
                                flipBackTimeline.setCycleCount(1);
                                flipBackTimeline.play();
                                if (clickCount < 20) {
                                    clickCount++;
                                    scoreLabel.setText("Antal forkerte: " + clickCount);
                                } else {
                                    resetGame();
                                }
                            }
                        }


                    }
                }
            }
        };
        //bygger vores scene, samt reset knap
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

    //tjekker om alle kort er fjernet
    private boolean areAllCardsRemoved() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (cards[i][j].getParent() != null) {
                    return false;
                }
            }
        }
        return true;
    }
    //sætter kortene ind på scenen
    public void buildGame(EventHandler<MouseEvent> eventHandler) {
        cards = new Card[4][5];
        int id = 1;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                if (id <= 20)
                cards[i][j] = new Card(id++, i, j, this);
                root.getChildren().add(cards[i][j]);
                cards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

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
                    cards[i][j].setX(j * 100 + 50);
                    cards[i][j].setY(i * 100 + 50);
                }
            }
        }
    private void resetGame(){
        //fjerner vores spil og tilføjer kort+blanding, scoretavle, resetbutton og rulesButton
        score = 0;
        clickCount = 0;
        scoreLabel.setText("Antal forkerte: 0");
        root.getChildren().clear();
        buildGame(eventHandler);
        placeRandomCards();


        //bygger vi scoretavle igen
        VBox scoreBox = new VBox();
        scoreBox.getChildren().add(scoreLabel);
        scoreBox.setTranslateX(10);
        scoreBox.setTranslateY(10);
        root.getChildren().add(scoreBox);

        //bygger vores resetbutton igen
        Button resetButton = new Button();
        resetButton.setTranslateX(500);
        resetButton.setTranslateY(10);
        resetButton.setText("Restart");
        resetButton.setOnAction(event -> resetGame());
        root.getChildren().add(resetButton);

        //bygger vores RulesButton igen
        Alert rulesAlert = regler();
        Button rulesButton = new Button("Regler");
        rulesButton.setOnAction(e -> rulesAlert.showAndWait());
        rulesButton.setTranslateX(430);
        rulesButton.setTranslateY(10);
        root.getChildren().add(rulesButton);


    }
    //her er det som viser hvad regl knappen indenholder
    private Alert regler() {
        Alert rulesAlert = new Alert(Alert.AlertType.INFORMATION);
        rulesAlert.setTitle("Regler");
        rulesAlert.setHeaderText(null);
        rulesAlert.setContentText("Du kan gætte forkert 20 gange, ellers genstarter spillet \n"
                + "Du kal få billedet til at passe med navnet\n"
                + "Når du har få fjernet alle kort har du vundet\n"
                + "Løs spillet med så lidt forkerte som muligt");
        return rulesAlert;
    }

    public static void main(String[] args) {
        launch();
    }
}