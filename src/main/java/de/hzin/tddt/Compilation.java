package de.hzin.tddt;

import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.TestResult;
import vk.core.internal.InternalCompiler;

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

    public Compilation(String[] classNames){
        usedClasses = classNames;
        readCus();
        runCompilation();
    }

    public Compilation(String[] classNames, TextArea guiOut){
        usedClasses = classNames;
        textArea = guiOut;
        readCus();
        runCompilation();
    }

    public void runCompilation(){
        compiler = new InternalCompiler(cus);
        compiler.compileAndRunTests();
        compiler.getClass();
        compilerResult = compiler.getCompilerResult();
        testResult = compiler.getTestResult();
        if(compilerResult.hasCompileErrors()){
            for(int i = 0; i < cus.length; i++){
                System.out.println(compilerResult.getCompilerErrorsForCompilationUnit(cus[i]));
            }
        } else {
            if(textArea!=null){
                textArea.setText("Successfully Compiled\nSuccessful Tests:" + testResult.getNumberOfSuccessfulTests() + "\nFailed Tests:" + testResult.getNumberOfFailedTests());
            }
            System.out.println("Successfully Compiled\nSuccessful Tests:" + testResult.getNumberOfSuccessfulTests() + "\nFailed Tests:" + testResult.getNumberOfFailedTests());
            if(testResult.getNumberOfFailedTests() >= 1){
                System.out.println(testResult.getTestFailures());
            }
        }
    }

    private void readCus(){
        cus = new CompilationUnit[usedClasses.length];
        Filehandler fileHandle;
        for(int i = 0; i < usedClasses.length; i++){
            fileHandle = new Filehandler(usedClasses[i]);
            fileHandle.load();
            cus[i] = new CompilationUnit(usedClasses[i], fileHandle.getContent(), fileHandle.getisTest());
        }
    }

    private void setUsedClasses(String[] classes){
        usedClasses = classes;
    }

    private void setText(){

    }
}
