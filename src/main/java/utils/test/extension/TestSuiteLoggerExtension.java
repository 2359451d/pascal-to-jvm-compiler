package utils.test.extension;

import org.junit.jupiter.api.extension.*;
import utils.log.RegressionTestErrorReporter;

@Deprecated
public class TestSuiteLoggerExtension implements BeforeEachCallback, AfterEachCallback,
        BeforeAllCallback, AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {

    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        //RegressionTestErrorReporter.info("{}", () -> extensionContext.getDisplayName());
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        //RegressionTestErrorReporter.info("{}", () -> extensionContext.getDisplayName());

    }
}
