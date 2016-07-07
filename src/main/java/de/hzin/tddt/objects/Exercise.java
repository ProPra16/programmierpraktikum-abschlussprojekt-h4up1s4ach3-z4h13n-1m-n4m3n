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
@XmlType(propOrder = {"description", "classes", "config"})
public class Exercise {

    private String name;
    private String description;
    private List<ExerciseClass> classes;
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

    @XmlElementWrapper(name = "classes")
    @XmlElement(name = "class")
    public List<ExerciseClass> getClasses() {
        return classes;
    }

    public void setClasses(List<ExerciseClass> classes) {
        this.classes = classes;
    }

    @XmlElement(name = "config")
    public ExerciseConfig getConfig() {
        return config;
    }

    public void setConfig(ExerciseConfig config) {
        this.config = config;
    }
}
