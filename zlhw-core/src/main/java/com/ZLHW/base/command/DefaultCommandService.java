package com.ZLHW.base.command;

import org.apache.log4j.Logger;



public class DefaultCommandService implements CommandService {

	  private static final Logger log = Logger.getLogger(DefaultCommandService.class);

	  public <T> T execute(Command<T> command) {
	    EnvironmentImpl environment = EnvironmentImpl.getCurrent();
	    
	    try {
	      return command.execute(environment);
	      
	    } catch (RuntimeException e) {
	      log.info("exception while executing command "+command, e);
	      throw e;
	      
	    } catch (Exception e) {
	      log.info("exception while executing command "+command, e);
	      throw new ZLHWException("exception while executing command "+command, e);
	    }
	  }
	}
