package com.zb.jnlxc.model;

import java.util.HashMap;
import java.util.Map;



public enum WorkFlowType {
		EditMouldFlow("EditMouldFlow","修模流程"),
		schemeFlow("schemeFlow","图纸流程"),
		mouldFlow("mouldFlow","模具流程"),
		orderFlow("orderFlow","订单流程"),
		productRecordFlow("productRecordFlow","跟踪单流程");
		public static Map map = new HashMap();
		private String value,name;
		private WorkFlowType(String value , String name){
			this.value = value;
			this.name = name;
		}
		static {
			for (WorkFlowType workFlowType : values()) {
				map.put(workFlowType.getName(), workFlowType);
			}
		}
		
		public static WorkFlowType converseToEnum(String name){
			return (WorkFlowType) map.get(name);
		}


		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
}
