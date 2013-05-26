package com.ZLHW.base.command;


public interface CommandService {
	  
	  String NAME_TX_REQUIRED_COMMAND_SERVICE = "txRequiredCommandService";
	  String NAME_NEW_TX_REQUIRED_COMMAND_SERVICE = "newTxRequiredCommandService";

	  /**
	   * @throws JbpmException if command throws an exception.
	   */
	  <T> T execute(Command<T> command);
	}