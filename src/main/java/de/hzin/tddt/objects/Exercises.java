package de.hzin.tddt.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercises.java
 * Purpose: Contains data from xml file parsed with XMLHandler class.
 *
 * @author Aron Weisermann
 */
@XmlRootElement(name = "exercises")
public class Exercises {
    private List<Exercise> exercises = new ArrayList<>();
    private int currentExercise = 0;

    @XmlElement(name = "exercise")
    public List<Exercise> getExercisesList() {
        return exercises;
    }

    public Exercise getExerciseByIndex(int index){
        return exercises.get(index);
    }

    public void setCurrentExercise(int i){
        currentExercise = i;
    }

    public int getCurrentExercise(){
        return currentExercise;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
