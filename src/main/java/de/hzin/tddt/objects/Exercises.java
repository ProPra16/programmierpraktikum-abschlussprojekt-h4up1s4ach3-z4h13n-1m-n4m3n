package de.hzin.tddt.objects;

import de.hzin.tddt.util.XMLHandler;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
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
    private int currentIndex = 0;
    private File file;

    @XmlElement(name = "exercise")
    public List<Exercise> getExercisesList() {
        return exercises;
    }

    public Exercise getExerciseByIndex(int index){
        return exercises.get(index);
    }

    public Exercise getCurrentExercise(){
        return exercises.get(currentIndex);
    }

    public void setCurrentIndex(int i){
        currentIndex = i;
    }

    public int getCurrentIndex(){
        return currentIndex;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void saveExercises(){
        try {
                XMLHandler.marshal(this, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file = file;
    }
}
