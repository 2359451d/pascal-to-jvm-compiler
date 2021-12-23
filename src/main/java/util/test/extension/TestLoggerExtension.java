package util.test.extension;

import org.junit.jupiter.api.extension.*;
import util.test.TestUtils;

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
    private long totalStartTime;
    private long totalEndsTimes;
    private String testBodySeparator = "===============================";
    ;
    private String testMethodTimeSeparator = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";
    private String testClassSeparator = "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+";

    public TestLoggerExtension(String command) {
        this.command = command;
        this.argumentsList.add(command);
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.printf("****** Setup for Test Class [%s] ******\n", extensionContext.getDisplayName());
        // base testing resources path
        //StringBuilder base = new StringBuilder(TestUtils.testResourcesBase);

        totalStartTime = System.currentTimeMillis();

        base = TestUtils.concatenateTestResourcePath(base, extensionContext.getTestClass().get());
        //ENDs with correct Path in test resources
        System.out.printf("Base Test Resource Path = %s\n", base);
        System.out.printf("Command: %s \n", command);
        System.out.println("****** Setup Ends ******\n");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.printf("++ Current Test Method - [%s] ++\n", extensionContext.getTestMethod().get().getName());
        System.out.println(testBodySeparator);
        if (argumentsList.size()>1) argumentsList.remove(1);
        System.out.println("argumentsList = " + argumentsList);
        eachStartTime = System.currentTimeMillis();
    }

    private void printTimeInSeconds(long duration) {
        System.out.printf("%d seconds\n", TimeUnit.MILLISECONDS.toSeconds(duration));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        totalEndsTimes = System.currentTimeMillis();
        long duration = totalEndsTimes - totalStartTime;
        System.out.printf("+-+-+- Total Execution Time of Test Class [%s] -+-+-+\n", extensionContext.getDisplayName());
        System.out.printf("%d milliseconds = ", duration);
        printTimeInSeconds(duration);
        System.out.println(testClassSeparator);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        eachEndsTime = System.currentTimeMillis();
        long duration = eachEndsTime - eachStartTime;
        System.out.println(testBodySeparator);
        System.out.printf("++ Current Test Method Ends, Total Execution Time of Method [%s] ++\n",
                extensionContext.getTestMethod().get().getName());
        System.out.printf("%d milliseconds = ", duration);
        printTimeInSeconds(duration);
        System.out.println(testMethodTimeSeparator);
        System.out.println();
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
