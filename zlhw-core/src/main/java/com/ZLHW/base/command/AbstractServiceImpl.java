package com.ZLHW.base.command;


public class AbstractServiceImpl {
  
  protected CommandService commandService;

  public CommandService getCommandService() {
    return commandService;
  }

  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }
}