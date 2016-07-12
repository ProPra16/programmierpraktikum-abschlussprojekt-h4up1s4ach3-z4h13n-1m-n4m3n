package de.hzin.tddt.panes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by Aron on 12.07.2016.
 */
public class BabystepTimer extends Label {
    private int time;
    private Timeline timeLine;

    public BabystepTimer(){
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            time--;
            if (time <= 0) {
                stopTimer();
                setText("Zeit:" + String.valueOf(time));
            }
            setText("Zeit:" + String.valueOf(time));
        }));
        timeLine.setCycleCount(Animation.INDEFINITE);
    }

    public void startTimer(int s){
        time = s;
        timeLine.play();
    }

    public void stopTimer(){
        timeLine.stop();
    }

    public int getTime() {
        return time;
    }
}
