package com.ZLHW.base.command;

import java.io.Serializable;


public interface Command<T> extends Serializable {
  
  T execute(Environment environment) throws Exception;
}