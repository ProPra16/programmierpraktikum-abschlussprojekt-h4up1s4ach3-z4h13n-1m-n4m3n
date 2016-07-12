package de.hzin.tddt.util;

import java.time.Instant;

/**
 * Created by julius on 12.07.16.
 */
public class TimeKeeper {
    private Instant time = Instant.now();
    private double timeCode = 0;
    private double timeRefactor = 0;
    private double timeTest = 0;
    private int state = 0;

    public TimeKeeper(){
        resetTime();
    }

    public void changeStateTo(int c){
        System.out.println(time.toEpochMilli()/1000);
        System.out.println("Angekommener State" + c);
        if(state == 0){
            addCodeTime();
        }
        else if(state == 1){
            addTestTime();
        }
        else if(state == 2){
            addRefactorTime();
        }
        state = c;
        resetTime();
    }

    public void refreshTime(int c){
        System.out.println(time.toEpochMilli()/1000);
        if(c == 0){
            addCodeTime();
        }
        else if(c == 1){
            addTestTime();
        }
        else if(c == 2){
            addRefactorTime();
        }
        resetTime();
    }

    public void addCodeTime(){
        timeCode += (Instant.now().toEpochMilli()-time.toEpochMilli())/1000;
        resetTime();
    }

    public void addRefactorTime(){
        timeRefactor += (Instant.now().toEpochMilli()-time.toEpochMilli())/1000;
        resetTime();
    }

    public void addTestTime(){
        timeTest += (Instant.now().toEpochMilli()-time.toEpochMilli())/1000;
        resetTime();
    }

    public double getTimeCode(){
        return timeCode;
    }

    public double getTimeRefactor(){
        return timeRefactor;
    }

    public double getTimeTest(){
        return timeTest;
    }

    private void resetTime(){
        time = Instant.now();
    }
}
