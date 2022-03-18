package driver;

public class DriverArgument {
    private DriverCommand driverCommand;
    private String path;

    public DriverArgument(DriverCommand driverCommand, String path) {
        this.driverCommand = driverCommand;
        this.path = path;
    }

    public String getCommandName() {
        return driverCommand.getCommandName();
    }

    public DriverCommand getDriverCommand() {
        return driverCommand;
    }

    public String getPath() {
        return path;
    }
}
