package driver;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Regression test suite of the compiler
 * Contained unit test suites:
 * !    compiler arguments
 * !    syntactic analysis (parse)
 * !    contextual analysis (check)
 * !    code generation (run)
 */
@Suite
@DisplayName("Test suite PascalCompilerDriverSuiteTest.java")
@SelectPackages("driver")
public class PascalCompilerDriverTestSuite {

}
