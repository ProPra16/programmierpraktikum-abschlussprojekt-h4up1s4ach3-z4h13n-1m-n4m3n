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

    public TimeKeeper(){
        resetTime();
    }

    public void addCodeTime(){
        timeCode += (Instant.now().getNano()-time.getNano())/1000000;
        resetTime();
    }

    public void addRefactorTime(){
        timeRefactor += (Instant.now().getNano()-time.getNano())/1000000;
        resetTime();
    }

    public void addTestTime(){
        timeTest += (Instant.now().getNano()-time.getNano())/1000000;
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
