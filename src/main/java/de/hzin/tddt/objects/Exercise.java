package de.hzin.tddt.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise.java
 * Purpose: Contains description, classes, tests and config of an exercise.
 *
 * @author Aron Weisermann
 */
@XmlType(propOrder = {"description", "classes", "tests", "config"})
public class Exercise {

    private String name;
    private String description;
    private List<ExerciseClass> classes;
    private List<ExerciseTest> tests;
    private ExerciseConfig config;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExerciseJavaFile> getAllJavaFiles(){
        List<ExerciseJavaFile> allJavaFilesList = new ArrayList<>();
        allJavaFilesList.addAll(tests);
        allJavaFilesList.addAll(classes);
        return allJavaFilesList;
    }

    @XmlElementWrapper(name = "classes")
    @XmlElement(name = "class")
    public List<ExerciseClass> getClasses() {
        return classes;
    }

    public void setClasses(List<ExerciseClass> classes) {
        this.classes = classes;
    }

    @XmlElementWrapper(name = "tests")
    @XmlElement(name = "test")
    public List<ExerciseTest> getTests() {
        return tests;
    }

    public void setTests(List<ExerciseTest> tests) {
        this.tests = tests;
    }

    @XmlElement(name = "config")
    public ExerciseConfig getConfig() {
        return config;
    }

    public void setConfig(ExerciseConfig config) {
        this.config = config;
    }
}
