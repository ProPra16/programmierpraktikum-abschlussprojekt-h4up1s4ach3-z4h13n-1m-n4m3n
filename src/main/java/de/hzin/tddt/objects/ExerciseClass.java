package de.hzin.tddt.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * ExerciseClass.java
 * Purpose: Contains name and code of a class.
 *
 * @author Aron Weisermann
 */
public class ExerciseClass {

    private String name;
    private String code;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlValue
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExerciseClass() {

    }

    public ExerciseClass(String className, String classContent) {
        this.name = className;
        this.code = classContent;
    }
}
