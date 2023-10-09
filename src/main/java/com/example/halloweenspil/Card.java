package com.example.halloweenspil;

import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Card extends ImageView {

    private ScaleTransition scaleInTransition;
    private ScaleTransition scaleOutTransition;

    private Mememorygame mg;
    private int id;

    private Image front;
    private Image back;
    private boolean fs;

    public Card(int nr ,int x, int y, Mememorygame m) {
        id = nr;
        String filnavn = "b" + id + ".png";
        String filename = "bagside.png";
        front = new Image(getClass().getResource(filnavn).toString());
        back = new Image(getClass().getResource(filename).toString());
        setImage(front);
        fs = true;
        setX(x*80);
        setY(y*80);
        mg = m;




        // Opret skaleind-transition
        scaleInTransition = new ScaleTransition(Duration.seconds(0.5), this);
        scaleInTransition.setFromX(0);
        scaleInTransition.setFromY(0);
        scaleInTransition.setToX(1);
        scaleInTransition.setToY(1);

        // Opret skaleud-transition
        scaleOutTransition = new ScaleTransition(Duration.seconds(0.5), this);
        scaleOutTransition.setFromX(1);
        scaleOutTransition.setFromY(1);
        scaleOutTransition.setToX(0);
        scaleOutTransition.setToY(0);

    }

    public void flip() {
        System.out.println("brik " + getX() + "," + getY());

        if(fs){
            setImage(back);
            fs = false;
        }
        else{
            setImage(front);
            fs = true;
        }

    }

}
