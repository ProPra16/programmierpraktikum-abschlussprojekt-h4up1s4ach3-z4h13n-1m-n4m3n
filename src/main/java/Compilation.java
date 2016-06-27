import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.TestResult;
import vk.core.internal.InternalCompiler;

/**
 * Created by julius on 24.06.16.
 */
public class Compilation {

    public Compilation(){
        CompilationUnit[] unit = new CompilationUnit[2];
        unit[0] = new CompilationUnit("LeapYear", "public class LeapYear {\n  public static boolean isLeapYear(int year) {\n    if (year % 400 == 0) return true;\n    if (year % 100 == 0) return false;\n    if (year % 4 == 0) return true;\n    return false;\n  }\n}",false);
        unit[1] = new CompilationUnit("LeapYearTest", "import static org.junit.Assert.*;\nimport org.junit.*;\n\npublic class LeapYearTest {\n    @Test\n    public void testone()  {\n        int input = 1;\n        assertEquals(false, LeapYear.isLeapYear(input));\n    }\n    @Test\n    public void multi()  {\n        int input = 800;\n        assertEquals(true, LeapYear.isLeapYear(input));\n    }\n    @Test\n    public void isleap()  {\n        int input = 4;\n        assertEquals(true, LeapYear.isLeapYear(input));\n    }\n    @Test\n    public void isleapmult()  {\n        int input = 16;\n        assertEquals(true, LeapYear.isLeapYear(input));\n    }\n    @Test\n    public void hundredexept()  {\n        int input = 200;\n        assertEquals(false, LeapYear.isLeapYear(input));\n    }    \n}\n", true);
        InternalCompiler comp = new InternalCompiler(unit);
        comp.compileAndRunTests();
        comp.getClass();
        CompilerResult compilerResult = comp.getCompilerResult();
        TestResult testResult = comp.getTestResult();
        if(compilerResult.hasCompileErrors()){
            System.out.println(compilerResult.getCompilerErrorsForCompilationUnit(unit[0]));
        } else {
            System.out.println("Successful Tests:" + testResult.getNumberOfSuccessfulTests() + "\nFailed Tests:" + testResult.getNumberOfFailedTests());
            if(testResult.getNumberOfFailedTests() >= 1){
                System.out.println(testResult.getTestFailures());
            }
        }
    }
}
