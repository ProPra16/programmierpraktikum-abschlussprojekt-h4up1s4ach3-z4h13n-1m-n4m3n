package de.hzin.tddt.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ExerciseConfig.java
 * Purpose: Contains the following configurations for an exercise:
 *              - Babysteps:    Wenn fur eine Übung Babysteps eingeschaltet sind, wird die Zeit, die der Nutzer in den Phasen
 *                              RED und GREEN hat, limitiert. Ist die Zeit abgelaufen, wird der neue Test/Code geloscht und
 *                              es wird in die vorangegangene Phase zuruckgewechselt. Babysteps haben den Sinn dem Nutzer
 *                              die Entwicklung in kleineren Schritten nahezulegen/anzutrainieren. Die Zeit soll kongurierbar
 *                              sein. Ein ublicher Wert sind 2 -3 Minuten. Die Zeit fur REFACTOR ist nicht limitiert.
 *
 *              - Timetracking: Die Tracking Funktion zeichnet auf, was der Benuzter wann geandert hat. Tracking soll erlauben
 *                              herauszunden fur welche Aktivitaten der Nutzer viel Zeit benotigt und welche Fehler auftreten.
 *                              Die Daten konnen analysiert werden um Probleme zu erkennen in die die Nutzer haug laufen.
 *                              Sie mussen zusatzlich zum reinen Tracking mindestens eine Analyse schreiben, die in Form
 *                              eines Charts anzeigt, wie lange der Nutzer in den einzelnen Phasen verbracht hat.
 *
 * @author Aron Weisermann
 */
public class ExerciseConfig {
    private Babysteps babysteps;
    private Timetracking timetracking;

    @XmlElement(name = "babysteps")
    public Babysteps getBabysteps() {
        return babysteps;
    }

    public void setBabysteps(Babysteps babysteps) {
        this.babysteps = babysteps;
    }

    @XmlElement(name = "timetracking")
    public Timetracking getTimetracking() {
        return timetracking;
    }

    public void setTimetracking(Timetracking timetracking) {
        this.timetracking = timetracking;
    }

    @XmlType(propOrder = {"value", "time"})
    static class Babysteps{
        private String value;
        private String time;

        @XmlAttribute
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @XmlAttribute
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    static class Timetracking {
        private String value;

        @XmlAttribute
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
