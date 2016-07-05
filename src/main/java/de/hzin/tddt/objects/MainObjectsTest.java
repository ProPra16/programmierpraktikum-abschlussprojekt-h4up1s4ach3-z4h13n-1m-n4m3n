package de.hzin.tddt.objects;

import de.hzin.tddt.util.XMLHandler;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;



public class MainObjectsTest {
    public static void main(String[] args) throws JAXBException {
        Exercises exercises = new Exercises();
        try {
            exercises = (XMLHandler.unmarshal(new File("Catalog/aufgabe1.xml")));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        ExerciseConfig config = new ExerciseConfig();
        try {
            XMLHandler.marshal(exercises, new File("Catalog/exercises.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
