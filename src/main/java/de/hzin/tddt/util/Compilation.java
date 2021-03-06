package de.hzin.tddt.util;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.ExerciseTest;
import de.hzin.tddt.objects.Exercises;
import javafx.scene.control.TextArea;
import vk.core.api.*;
import vk.core.internal.InternalCompiler;

import java.util.ArrayList;
import java.util.Collection;
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
    private List<CompilationUnit> cusList = new ArrayList<>();
    private List<CompilationUnit> additionalResources = new ArrayList();
    private ErrorCounter errorCounter = new ErrorCounter();

    public Compilation(Exercises exercises, TextArea inTextArea) {
        addAdditionalResource("StdRandom");
        addAdditionalResource("StdOut");
        //Exercise
        //addWebResource("http://introcs.cs.princeton.edu/java/stdlib/StdOut.java","StdOut");
        textArea = inTextArea;
        if (exercises != null) {
            Exercise classAndTest = exercises.getCurrentExercise();
            List<ExerciseClass> currentClasses = classAndTest.getClasses();
            cusList = new ArrayList<CompilationUnit>();
            for (CompilationUnit i : additionalResources) {
                cusList.add(i);
            }
            for (int i = 1; i <= currentClasses.size(); i++) {
                ExerciseTest currentTest = currentClasses.get(i - 1).getTest();
                cusList.add(new CompilationUnit(currentClasses.get(i - 1).getName(), currentClasses.get(i - 1).getCode(), false));
                cusList.add(new CompilationUnit(currentTest.getName(), currentTest.getCode(), true));
            }
            cus = cusList.toArray(new CompilationUnit[cusList.size()]);
            runCompilation();
        } else {
            textArea.setText("No exercise selected");
        }
    }

    public Compilation(String[] classNames, TextArea guiOut) {
        usedClasses = classNames;
        textArea = guiOut;
        textArea.setStyle("-fx-font-family: monospace");
        readCus();
        runCompilation();
    }

    public boolean hasCompileErrors() {
        return compilerResult.hasCompileErrors();
    }

    public int getNumberOfFailedTests() {
        return testResult.getNumberOfFailedTests();
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
                Collection<CompileError> currentErrorList = compilerResult.getCompilerErrorsForCompilationUnit(cus[i]);
                for (CompileError currentError : currentErrorList) {
                    errors = errors + "[Zeile:" + currentError.getLineNumber() + "]" + currentError.toString() + "\n";
                    errorCounter.countError(currentError.toString());
                }
            }
            textArea.setText(errors);
        } else {
            String text = "";
            String textNumbers = "";
            if (textArea != null) {
                textNumbers = String.format("Successfully Compiled\nSuccessful Tests:%4d\nFailed Tests:%8d", testResult.getNumberOfSuccessfulTests(), testResult.getNumberOfFailedTests());
            } else {
                System.out.println("Successfully Compiled\nSuccessful Tests:" + testResult.getNumberOfSuccessfulTests() + "\nFailed Tests:" + testResult.getNumberOfFailedTests());
            }
            if (testResult.getNumberOfFailedTests() >= 1) {
                Collection<TestFailure> testFailures = testResult.getTestFailures();
                for(TestFailure i : testFailures){
                    text = text + "[" + i.getTestClassName() + "~" + i.getMethodName() + "] " + i.getMessage() + "\n";
                }
            }
            textArea.setText(text + textNumbers);
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

    private void addAdditionalResource(String addClass) {
        String addClassPath = "lib/" + addClass;
        Filehandler file = new Filehandler(addClassPath);
        additionalResources.add(new CompilationUnit(addClass, file.getContent(), false));
    }

    private void addWebResource(String addWebSite, String addWebClass) {
        Filehandler file = null;
        try {
            file = new Filehandler(addWebSite, addWebClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        additionalResources.add(new CompilationUnit(addWebClass, file.getContent(), false));
    }

    public ErrorCounter getErrorCounter() {
        return errorCounter;
    }
}
