package de.hzin.tddt;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by Rafael on 28.06.2016.
 */
public class TimeManager {

    Timeline time;

    @FXML
    private Label timecounter;


    public void starteTimer() {
        int Sekunden=0;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Sekunden++;
            timecounter.setText(String.valueOf(Sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void stopZeit() {
        time.stop();
    }

    public void startebabystepTimer(){
        int Sekunden = 180;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Sekunden--;
            if(Sekunden==0){
                stopZeit();
                timecounter.setText(String.valueOf(Sekunden));
            }
            timecounter.setText(String.valueOf(Sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }
}
