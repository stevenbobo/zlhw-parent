package com.ZLHW.base.command;


public abstract class AbstractCommand<T> implements Command<T> {
	  
	  private static final long serialVersionUID = 1L;

	  String authenticatedUserId;

	  
	  public String getAuthenticatedUserId() {
	    return authenticatedUserId;
	  }
	  
	  public void setAuthenticatedUserId(String authenticatedUserId) {
	    this.authenticatedUserId = authenticatedUserId;
	  }
	}