package com.example.halloweenspil;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Memorygame extends Application {

    private Card[][] cards;
    private Pane scenegraf;
    @Override
    public void start(Stage stage) throws IOException {
        Pane scenegraf = new Pane();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Card c = (Card) e.getSource();
                c.flip();
            }
        };
        Scene scene = new Scene(scenegraf, 600, 600);
        stage.setTitle("MemorySkelet!");
        stage.setScene(scene);
        stage.show();

    }

    public void buildGame(EventHandler<MouseEvent> eventHandler) {
        cards = new Card[5][4];
        int id =1;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 4; j++) {
                if (id <= 20) {
                    cards[i][j] = new Card(id++, i, j, this);
                    scenegraf.getChildren().add(cards[i][j]);
                    cards[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                }
            }
    }

    public static void main(String[] args) {
        launch();
    }
}