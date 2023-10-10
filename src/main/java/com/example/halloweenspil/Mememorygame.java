package com.example.halloweenspil;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mememorygame extends Application {

    private Card[][] cards;
    private Pane root;
    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane();
        root.setPrefSize(600, 600);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Card c = (Card) e.getSource();
                c.flip();
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