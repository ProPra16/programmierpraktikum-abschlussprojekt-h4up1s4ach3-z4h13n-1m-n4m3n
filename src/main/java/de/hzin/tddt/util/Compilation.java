package de.hzin.tddt.util;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.ExerciseTest;
import de.hzin.tddt.objects.Exercises;
import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.TestResult;
import vk.core.internal.InternalCompiler;

import java.util.List;

/**
 * Created by julius on 24.06.16.
 */
public class Compilation {
    private String[] usedClasses;
    private TestResult testResult;
    private CompilerResult compilerResult;
    private InternalCompiler compiler;
    private CompilationUnit[] cus;
    private TextArea textArea;

    public Compilation(Exercises exercises, TextArea inTextArea, String[] contents) {
        //Exercise
        textArea = inTextArea;
        //System.out.print("asd");

        List<Exercise> exerciseList = exercises.getExercisesList();
        Exercise classAndTest = exercises.getCurrentExercise();
        List<ExerciseClass> currentClasses = classAndTest.getClasses();
        //System.out.print(currentClasses.get(0).getName());
        //System.out.print("\n" + currentClasses.size());
        cus = new CompilationUnit[currentClasses.size()*2];
        for (int i = 1; i <= currentClasses.size(); i++) {
            cus[(i*2)-2] = new CompilationUnit(currentClasses.get(i-1).getName(), currentClasses.get(i-1).getCode(), false);
            System.out.println(currentClasses.get(i-1).getName());
            ExerciseTest currentTest = currentClasses.get(i-1).getTest();
            cus[(i*2)-1] = new CompilationUnit(currentTest.getName(), currentTest.getCode(), true);
        }
        runCompilation();
    }

    public Compilation(String[] classNames, TextArea guiOut) {
        usedClasses = classNames;
        textArea = guiOut;
        textArea.setStyle("-fx-font-family: monospace");
        readCus();
        runCompilation();
    }

    public void runCompilation() {
        compiler = new InternalCompiler(cus);
        compiler.compileAndRunTests();
        compiler.getClass();
        compilerResult = compiler.getCompilerResult();
        testResult = compiler.getTestResult();
        if (compilerResult.hasCompileErrors()) {
            String errors = "";
            for (int i = 0; i < cus.length; i++) {
                errors = errors + (compilerResult.getCompilerErrorsForCompilationUnit(cus[i])) + "\n";
            }
            textArea.setText(errors);
        } else {
            if (textArea != null) {
                String text = String.format("Successfully Compiled\nSuccessful Tests:%4d\nFailed Tests:%8d", testResult.getNumberOfSuccessfulTests(), testResult.getNumberOfFailedTests());
                textArea.setText(text);
            }
            else {
                System.out.println("Successfully Compiled\nSuccessful Tests:" + testResult.getNumberOfSuccessfulTests() + "\nFailed Tests:" + testResult.getNumberOfFailedTests());
            }
            if (testResult.getNumberOfFailedTests() >= 1) {
                System.out.println(testResult.getTestFailures());
            }
        }
    }

    private void readCus() {
        cus = new CompilationUnit[usedClasses.length];
        Filehandler fileHandle;
        for (int i = 0; i < usedClasses.length; i++) {
            fileHandle = new Filehandler(usedClasses[i]);
            fileHandle.load();
            cus[i] = new CompilationUnit(usedClasses[i], fileHandle.getContent(), fileHandle.getisTest());
        }
    }

    private void setUsedClasses(String[] classes) {
        usedClasses = classes;
    }

    private void setText() {

    }
}
