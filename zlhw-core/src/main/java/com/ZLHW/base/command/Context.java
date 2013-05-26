package com.ZLHW.base.command;

import java.util.Set;

public interface Context {

	  /** key of the process-engine-context in the environment */
	  String CONTEXTNAME_PROCESS_ENGINE = "process-engine";
	  
	  /** key of the transaction-context in the environment */
	  String CONTEXTNAME_TRANSACTION = "transaction";

	  /** key of the execution-context in the environment */
	  String CONTEXTNAME_EXECUTION = "execution";

	  /** key of the task-context in the environment */
	  String CONTEXTNAME_TASK = "task";
	  
	  /** key of the job-context in the environment */
	  String CONTEXTNAME_JOB = "job";
	  
	  String getName();

	  Object get(String key);
	  <T> T get(Class<T> type);

	  boolean has(String key);
	  Object set(String key, Object value);
	  Set<String> keys();

	}