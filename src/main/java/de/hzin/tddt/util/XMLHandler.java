package de.hzin.tddt.util;

import de.hzin.tddt.objects.Exercises;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * XMLHandler.java
 * Purpose: Loads and saves xml files.
 *
 * @author Aron Weisermann
 */
public class XMLHandler {

    /**
     * Example:
     * <pre>
     * Exercises exercises = new Exercises();
     * try {
     * exercises = (XMLHandler.unmarshal(new File("Catalog/aufgabe1.xml")));
     * } catch (JAXBException e) {
     * e.printStackTrace();
     * }
     * </pre>
     *
     * @param importFile a File which defines the relative path where the xml files is located.
     * @return a new instance of Exercises loaded from the importFile.
     * @throws JAXBException
     */
    public static Exercises unmarshal(File importFile) throws JAXBException {
        Exercises exercises;

        JAXBContext context = JAXBContext.newInstance(Exercises.class);
        Unmarshaller um = context.createUnmarshaller();
        exercises = (Exercises) um.unmarshal(importFile);

        return exercises;
    }

    /**
     * Example:
     * <pre>
     * try {
     *  XMLHandler.marshal(exercises, new File("Catalog/exercises.xml"));
     * } catch (IOException e) {
     *  e.printStackTrace();
     * } catch (JAXBException e) {
     *  e.printStackTrace();
     * }
     * </pre>
     *
     * @param exercises    which should be saved.
     * @param selectedFile where to save the xml.
     * @throws IOException
     * @throws JAXBException
     */
    public static void marshal(Exercises exercises, File selectedFile)
            throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Exercises.class);
        BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(exercises, writer);
        writer.close();
    }
}
