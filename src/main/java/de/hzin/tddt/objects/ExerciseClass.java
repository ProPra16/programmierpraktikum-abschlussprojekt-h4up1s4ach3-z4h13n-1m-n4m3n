package de.hzin.tddt.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * ExerciseClass.java
 * Purpose: Contains name and code of a class.
 *
 * @author Aron Weisermann
 */
public class ExerciseClass{
    private String name;
    private RevertableCode code = new RevertableCode();
    private ExerciseTest test;
    private boolean isCurrentTest = true;

    @XmlElement
    public ExerciseTest getTest() {
        return test;
    }

    public void setTest(ExerciseTest test) {
        this.test = test;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getCode() {
        if(!(code.getCurrentContent() == null)) {
            return code.getCurrentContent();
        } else {
            return "";
        }
    }

    public void setCode(String code) {
        this.code.addContent(code);
    }

    public ExerciseClass() {

    }

    public ExerciseClass(String className, String classContent) {
        this.name = className;
        this.code.addContent(classContent);
    }

    public void setIsCurrentTest(boolean isCurrentTest){
        this.isCurrentTest = isCurrentTest;
    }

    public boolean isCurrentTest(){
        return isCurrentTest;
    }
}
