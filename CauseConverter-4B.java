package com.tatasky.mcr.converters;

import java.util.ArrayList;                                      
import java.util.List;
import com.tatasky.mcr.entity.Cause;
import com.tatasky.mcr.model.response.CauseResponse;
import io.jsonwebtoken.lang.Collections;

public class CauseConverter {
	public static List<CauseResponse> mapCauseToCauseResponse(List<Cause> causeList) {
		
		List<CauseResponse>  causeResponselist=new ArrayList<CauseResponse>();
		
		if(Collections.isEmpty(causeList) ) {
			return causeResponselist;
		}
			
		  causeList.stream().forEach(causei -> {
			
		  CauseResponse causeResponsei = new CauseResponse();

      	  causeResponsei.setSid(causei.getSid());
      	  causeResponsei.setCause(causei.getCause());
      	  causeResponsei.setActive(causei.isActive());
      	  causeResponsei.setFaultCode(causei.getFaultCode());
      	  causeResponsei.setProblemCode(causei.getProblemCode());
      	  causeResponsei.setChainCode(causei.getChainCode());
      	  causeResponsei.setCauseType(causei.getCauseType());

			causeResponselist.add(causeResponsei);
			
		} );		
	
		return causeResponselist;
	}}
