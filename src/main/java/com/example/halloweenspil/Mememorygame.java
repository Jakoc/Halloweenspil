package com.example.halloweenspil;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
        stage.show();

    }

    public void buildGame(EventHandler<MouseEvent> eventHandler) {
        cards = new Card[5][5];
        int id =1;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                if (id <= 10) {
                    cards[i][j] = new Card(id++, i, j, this);
                    root.getChildren().add(cards[i][j]);
                    cards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                }
            }



    }

    public static void main(String[] args) {
        launch();
    }
}