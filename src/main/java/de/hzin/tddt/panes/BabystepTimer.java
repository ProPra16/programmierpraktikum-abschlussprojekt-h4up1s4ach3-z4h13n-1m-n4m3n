package de.hzin.tddt.panes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
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
                showTimeExpired();
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

    private void showTimeExpired(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zeit abgelaufen!");
        alert.setHeaderText(null);
        alert.setContentText("Die Zeit ist leider abgelaufen. Du musst die Phase nochmal wiederholen.");

        alert.showAndWait();
    }

    public void stopTimer(){
        timeLine.stop();
    }

    public int getTime() {
        return time;
    }
}
