package com.example.halloweenspil;

import javafx.animation.Interpolator;
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

        this.front = new Image(getClass().getResource(filnavn).toString());
        this.back = new Image(getClass().getResource(filename).toString());
        setImage(back); //ændre setImage til back og fs til false hvis det skal vændes om :)
        fs = false;
        setX(x*100);
        setY(y*100);
        mg = m;

        scaleInTransition = new ScaleTransition(Duration.seconds(0.5), this);
        scaleInTransition.setFromX(0);
        scaleInTransition.setToX(1);
        scaleInTransition.setInterpolator(Interpolator.EASE_BOTH);


        scaleOutTransition = new ScaleTransition(Duration.seconds(0.5), this);
        scaleOutTransition.setFromX(1);
        scaleOutTransition.setToX(0);
        scaleOutTransition.setInterpolator(Interpolator.EASE_BOTH);

    }

    public void flip() {

            if (fs) {
                setImage(back);
                fs = false;
                scaleInTransition.play();
            } else {
                setImage(front);
                fs = true;
                scaleInTransition.play();
            }
    }


 }