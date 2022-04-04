package driver;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import utils.test.extension.TestSuiteLoggerExtension;

/**
 * Regression test suite of the compiler
 * Contained unit test cases:
 * !    syntactic analysis (parse)
 * !    contextual analysis (check)
 * !    code generation (run)
 *
 * Exclude unit test cases:
 * !    compiler arguments (driver usage check, you may run this individually)
 */
@Suite
@SuiteDisplayName("Regression Test suite - [PascalCompilerDriverSuiteTest.java]")
@SelectPackages("driver")
@IncludeTags("regression")
public class PascalCompilerDriverTestSuite {
    //@RegisterExtension
    //static TestSuiteLoggerExtension extension = new TestSuiteLoggerExtension();
}
