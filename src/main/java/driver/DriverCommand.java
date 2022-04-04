package driver;

public enum DriverCommand {

   PARSE("parse"),
   CHECK("check"),
   COMPILE("compile"),
   RUN("run")

   ;

   private String commandName;
   DriverCommand(String commandName){
      this.commandName = commandName;
   }

   public String getCommandName() {
      return commandName;
   }
}
