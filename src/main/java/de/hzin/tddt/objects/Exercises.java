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

    @XmlElement(name = "exercise")
    public List<Exercise> getExercisesList() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
