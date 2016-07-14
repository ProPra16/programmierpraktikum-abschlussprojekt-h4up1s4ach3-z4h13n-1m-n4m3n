package de.hzin.tddt.util;

import de.hzin.tddt.objects.State;

import java.time.Instant;

/**
 * Created by julius on 12.07.16.
 */
public class TimeKeeper {
    private Instant time = Instant.now();
    private double timeCode = 0;
    private double timeRefactor = 0;
    private double timeTest = 0;
    private State state = State.TEST;

    public TimeKeeper() {
        resetTime();
    }

    public void changeStateTo(State c) {
        refreshTime();
        state = c;
    }

    public void refreshTime() {
        if (state == state.CODE) {
            addCodeTime();
        } else if (state == state.TEST) {
            addTestTime();
        } else if (state == state.REFACTOR) {
            addRefactorTime();
        }
        resetTime();
    }

    public void addCodeTime() {
        timeCode += (Instant.now().toEpochMilli() - time.toEpochMilli()) / 1000;
        resetTime();
    }

    public void addRefactorTime() {
        timeRefactor += (Instant.now().toEpochMilli() - time.toEpochMilli()) / 1000;
        resetTime();
    }

    public void addTestTime() {
        timeTest += (Instant.now().toEpochMilli() - time.toEpochMilli()) / 1000;
        resetTime();
    }

    public double getTimeCode() {
        return timeCode;
    }

    public double getTimeRefactor() {
        return timeRefactor;
    }

    public double getTimeTest() {
        return timeTest;
    }

    private void resetTime() {
        time = Instant.now();
    }
}
