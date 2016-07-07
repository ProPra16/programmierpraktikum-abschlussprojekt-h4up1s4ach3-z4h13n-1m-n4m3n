package de.hzin.tddt.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * ExerciseTest.java
 * Purpose: Contains name and code of a test class.
 *
 * @author Aron Weisermann
 */
public class ExerciseTest{
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

    public ExerciseTest() {

    }

    public ExerciseTest(String className, String classContent) {
        this.name = className;
        this.code = classContent;
    }
}
