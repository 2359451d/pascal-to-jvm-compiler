package utils.test.extension;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import tableUtils.TableManager;
import type.TypeDescriptor;
import utils.log.GlobalLogger;
import utils.log.TestLogger;
import utils.test.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestLoggerExtension implements BeforeEachCallback, AfterEachCallback,
        BeforeAllCallback, AfterAllCallback {
    private String command;
    private StringBuilder base = new StringBuilder(TestUtils.testResourcesBase);
    ;
    private List<String> argumentsList = new ArrayList<>();
    private long eachStartTime;
    private long eachEndsTime;
    private long totalTestTime;
    private String testBodySeparator = "===============================";
    ;
    private String testMethodTimeSeparator = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";
    private String testClassSeparator = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";
    private TableManager<Object, TypeDescriptor> tableManager;

    public TestLoggerExtension(String command) {
        this.command = command;
        this.argumentsList.add(command);
        this.tableManager = TableManager.getInstance();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        // reset global logger level
        GlobalLogger.setLevel(Level.INFO);

        // reset total testing time
        this.totalTestTime = 0L;

        base = TestUtils.concatenateTestResourcePath(base,
                extensionContext.getTestClass().get());

        TestLogger.info("****** Setup for Test Class [{}] ******",
                extensionContext::getDisplayName);
        TestLogger.info("Base Test Resource Path = {}", ()->base);
        TestLogger.info("Command: {}", ()->command);
        TestLogger.info("****** Setup Ends ******");

        //System.out.printf("Base Test Resource Path = %s\n", base);
        //System.out.printf("Command: %s \n", command);
        //System.out.println("****** Setup Ends ******\n");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        TestLogger.info("++ Current Test Method - [{}] ++",
                ()->extensionContext.getTestMethod().get().getName());
        TestLogger.info("{}", extensionContext::getDisplayName);
        TestLogger.info(testBodySeparator);
        //System.out.printf("++ Current Test Method - [%s] ++\n", extensionContext.getTestMethod().get().getName());
        //System.out.println(testBodySeparator);
        if (argumentsList.size() > 1) argumentsList.remove(1);

        // reset all the tables
        tableManager.resetAndInitAllTables();

        eachStartTime = System.currentTimeMillis();
    }

    private void printFormattedTime(long duration) {
        TestLogger.info("{} seconds = {} milliseconds",
                ()-> TimeUnit.MILLISECONDS.toSeconds(duration),
                ()-> duration
        );
        //System.out.printf("%d seconds\n", TimeUnit.MILLISECONDS.toSeconds(duration));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        TestLogger.info("+-+-+- Total Execution Time of Test Class [{}] -+-+-+", ()->extensionContext.getDisplayName());
        //TestLogger.info("{} milliseconds = ", ()->totalTestTime);

        //System.out.printf("+-+-+- Total Execution Time of Test Class [%s] -+-+-+\n", extensionContext.getDisplayName());
        //System.out.printf("%d milliseconds = ", totalTestTime);
        printFormattedTime(totalTestTime);

        TestLogger.info(testClassSeparator);
        //System.out.println(testClassSeparator);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        eachEndsTime = System.currentTimeMillis();
        long duration = eachEndsTime - eachStartTime;
        totalTestTime += duration;

        TestLogger.info(testBodySeparator);
        TestLogger.info("++ Current Test Method Ends, Total Execution Time of Method [{}] ++",
                ()->extensionContext.getTestMethod().get().getName());

        //System.out.println(testBodySeparator);
        //System.out.printf("++ Current Test Method Ends, Total Execution Time of Method [%s] ++\n",
        //        extensionContext.getTestMethod().get().getName());
        //System.out.printf("%d milliseconds = ", duration);
        printFormattedTime(duration);

        TestLogger.info(testMethodTimeSeparator+"\n");
        //System.out.println(testMethodTimeSeparator);
        //System.out.println();
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
