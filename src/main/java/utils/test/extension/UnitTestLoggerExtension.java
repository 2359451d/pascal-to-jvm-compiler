package utils.test.extension;

import ast.visitor.listeners.CustomConsoleErrorListener;
import ch.qos.logback.classic.Level;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import tableUtils.TableManager;
import type.TypeDescriptor;
import utils.log.ErrorReporter;
import utils.log.GlobalLogger;
import utils.log.RegressionTestErrorReporter;
import utils.log.TestLogger;
import utils.test.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnitTestLoggerExtension implements BeforeEachCallback, AfterEachCallback,
        BeforeAllCallback, AfterAllCallback {

    private String command;
    private StringBuilder base;
    private List<String> argumentsList;
    private TableManager<Object, TypeDescriptor> tableManager;

    private long testCountPassed;
    private long testCountFailed;
    private long eachStartTime;
    private long eachEndsTime;
    private long totalTestTime;
    private String testBodySeparator = "===============================";
    private String testMethodTimeSeparator = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";
    private String testClassSeparator = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";

    public UnitTestLoggerExtension(String command, String displayName) {
        this.command = command;
        this.argumentsList = new ArrayList<>();
        this.argumentsList.add(command);
        this.tableManager = TableManager.getInstance();
        this.base = new StringBuilder(TestUtils.testResourcesBase);
        this.testCountPassed = 0;
        this.testCountFailed = 0;

        // the error level information would also be redirected to regression error log
        CustomConsoleErrorListener.INSTANCE.setRedirectToRegressionLog(true);
        if (StringUtils.isNotBlank(displayName)) {
            RegressionTestErrorReporter.info("{}",()->testClassSeparator);
            RegressionTestErrorReporter.info("Log File Generated for {}", () -> displayName);
            RegressionTestErrorReporter.info("{}\n",()->testClassSeparator);
        }
    }

    public UnitTestLoggerExtension(String command) {
        this(command, null);
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        // reset global logger level
        GlobalLogger.setLevel(Level.INFO);

        // reset total testing time
        this.totalTestTime = 0L;

        base = TestUtils.concatenateTestResourcePath(base,
                extensionContext.getTestClass().get());

        TestLogger.info("****** Setup for Test Class {} ******",
                extensionContext::getDisplayName);
        TestLogger.info("Base Test Resource Path = {}", () -> base);
        TestLogger.info("Command: {}", () -> command);
        TestLogger.info("****** Setup Ends ******");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        //ErrorReporter.info("{}", extensionContext::getDisplayName);

        TestLogger.info("++ Current Test Method - [{}] ++",
                () -> extensionContext.getTestMethod().get().getName());
        TestLogger.info("{}", extensionContext::getDisplayName);
        TestLogger.info(testBodySeparator);
        if (argumentsList.size() > 1) argumentsList.remove(1);

        // reset all the tables
        tableManager.resetAndInitAllTables();

        eachStartTime = System.currentTimeMillis();
    }

    private void printFormattedTime(long duration) {
        TestLogger.info("{} seconds = {} milliseconds",
                () -> TimeUnit.MILLISECONDS.toSeconds(duration),
                () -> duration
        );
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        TestLogger.info("+-+-+- Total Execution Time of Test Class [{}] -+-+-+", () -> extensionContext.getDisplayName());
        printFormattedTime(totalTestTime);
        TestLogger.info(testClassSeparator);

        RegressionTestErrorReporter.info("{}", () -> testBodySeparator);
        RegressionTestErrorReporter.info("{}",()-> extensionContext.getDisplayName());
        RegressionTestErrorReporter.info("Test Case Count: [{}], Matched: [{}], Unmatched: [{}]",
                () -> testCountFailed+testCountPassed,
                ()->testCountPassed,
                ()->testCountFailed);
        RegressionTestErrorReporter.info("{}\n", () -> testBodySeparator);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        boolean throwable = extensionContext.getExecutionException().isPresent();
        if (throwable) {
            testCountFailed++;
            RegressionTestErrorReporter.info("Failed Test Case: {}\n", extensionContext::getDisplayName);
        } else testCountPassed++;

        eachEndsTime = System.currentTimeMillis();
        long duration = eachEndsTime - eachStartTime;
        totalTestTime += duration;

        TestLogger.info(testBodySeparator);
        TestLogger.info("++ Current Test Method Ends, Total Execution Time of Method [{}] ++",
                () -> extensionContext.getTestMethod().get().getName());

        printFormattedTime(duration);

        TestLogger.info(testMethodTimeSeparator + "\n");
    }

    public StringBuilder getBase() {
        return base;
    }

    public void addNewArgument(String argument) {
        argumentsList.add(argument);
    }

    public List<String> getArgumentsList() {
        return argumentsList;
    }

    public String[] getArgumentsArr() {
        return argumentsList.toArray(argumentsList.toArray(new String[0]));
    }


}
