package de.hzin.tddt;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by Rafael on 28.06.2016.
 */
public class TimeManager {


    Timeline time;
    int sekunden;


    public void starteTimer() {
        sekunden =0;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden++;
        //    timecounter.setText("Zeit:" +String.valueOf(sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void stopZeit() {
        time.stop();
    }

    public void startebabystepTimer(){
        sekunden = 180;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden--;
            if(sekunden ==0){
                stopZeit();
            //    timecounter.setText("Zeit:" +String.valueOf(sekunden));
            }
        //    timecounter.setText("Zeit:" +String.valueOf(sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }
}
