package com.tatasky.mcr.constants;

/**
 * 
 * @author vikaschoudhary
 *
 */

public enum FaultReportsName {
	DFAD("DFAD.xlsx"), DFR("DFR.xlsx") , ACTIVEDARSHAN("ActveDarshan.xlsx") ,MSOR("MSOR.xlsx") ,FEEDBACKMCR("Feedback_MCR.xlsx"),
	MIB("MIB.xlsx"), NOCC2("NOCC2.xlsx") ,NOCC1("NOCC2.xlsx") , MOR("MOR.xlsx");

	private String faultTemplateName;

	FaultReportsName(String faultTemplateName) {
		this.faultTemplateName = faultTemplateName;
	}

	public String getFaultTemplateName() {
		return faultTemplateName;
	}

}
