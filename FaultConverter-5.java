package com.tatasky.mcr.converters;
import java.util.ArrayList;                                              
import java.util.List;
import com.tatasky.mcr.entity.Fault;
import com.tatasky.mcr.model.request.CloseRequest;
import com.tatasky.mcr.model.response.FaultResponse;
import io.jsonwebtoken.lang.Collections;

public class FaultConverter {
	public static List<FaultResponse> mapFaultToFaultResponse(List<Fault> faultList) {
		
		List<FaultResponse>  faultResponselist=new ArrayList<FaultResponse>();
		
		if(Collections.isEmpty(faultList) ) {
			return faultResponselist;
		}
		
		      faultList.stream().forEach(faulti -> {
        	  FaultResponse faultResponsei = new FaultResponse();
        	  faultResponsei.setSid(faulti.getSid());
        	  faultResponsei.setChannelSid(faulti.getChannelSid());
        	  faultResponsei.setErrorNo(faulti.getErrorNo());
        	  faultResponsei.setTxDate(faulti.getTxDate());
        	  faultResponsei.setAvCodeSid(faulti.getAvCodeSid());
        	  faultResponsei.setScheduled(faulti.getScheduled());
        	  faultResponsei.setFileRefrence(faulti.getFileRerence());
        	  faultResponsei.setEventTypeSid(faulti.getEventTypeSid());
        	  faultResponsei.setEventParticular(faulti.getEventParticular());
        	  faultResponsei.setTimeOut(faulti.getTimeOut());
        	  faultResponsei.setTimeIn(faulti.getTimeIn());
        	  faultResponsei.setDuration(faulti.getDuration());
        	  faultResponsei.setCauseSid(faulti.getCauseSid());
        	  faultResponsei.setEquipment(faulti.getEquipment());
        	  faultResponsei.setActionTaken(faulti.getActionTaken());
        	  faultResponsei.setActionConfirm(faulti.getActionConfirm());
        	  faultResponsei.setTicketStatus(faulti.getTicketStatus());
        	  faultResponsei.setRemarks(faulti.getRemarks());
        	  faultResponsei.setTxDate(faulti.getTxDate());
        	  faultResponsei.setModificationDate(faulti.getModificationDate());
        	  faultResponsei.setModificationBy(faulti.getModificationBy());
        	  faultResponsei.setCreatedDate(faulti.getCreatedDate());
        	  faultResponsei.setCreatedBy(faulti.getCreatedBy());   //private String seconds;
        	  faultResponsei.setTicket(faulti.getTicket());
        	  faultResponsei.setSeconds(faulti.getSeconds());

        	  faultResponselist.add(faultResponsei);

		     } );
		
		return faultResponselist;
	}
	
	
	public static Fault mapCloseRequestToClose(Fault fault ,CloseRequest faultRequest) {
		fault.setTimeIn(faultRequest.getTimeIn());
		fault.setTimeOut(faultRequest.getTimeOut());
		fault.setDuration(faultRequest.getDuration());
		return fault;
	}
	
	public static FaultResponse mapCloseToCloseResponse(Fault fault) {
	  FaultResponse faultResponse = new FaultResponse();
  	  faultResponse.setTimeOut(fault.getTimeOut());
  	  faultResponse.setTimeIn(fault.getTimeIn());
  	  faultResponse.setDuration(fault.getDuration());
	 return faultResponse;
	}
	
}
