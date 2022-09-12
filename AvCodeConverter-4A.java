package com.tatasky.mcr.converters;
import java.util.ArrayList;
import java.util.List;
import com.tatasky.mcr.entity.AvCode;                               
import com.tatasky.mcr.model.request.AvCodeRequest;
import com.tatasky.mcr.model.response.AvCodeResponse;

import io.jsonwebtoken.lang.Collections;
/**
 * 
 * @author vikaschoudhary
 *
 */
public class AvCodeConverter {

	public static AvCode mapAvCodeRequestToAvCode(AvCodeRequest avCodeRequest) {
		AvCode avCode = new AvCode();
		avCode.setProblem(avCodeRequest.getProblem());
		avCode.setAvcode(avCodeRequest.getAvcode());
		avCode.setAvcodeN(avCodeRequest.getAvcodeN());
        avCode.setActive(avCodeRequest.isActive());
   
		return avCode;
	}
	
	public static AvCodeResponse mapAvCodeToAvCodeResponse(AvCode avCode) {
		AvCodeResponse avCodeResponse = new AvCodeResponse();
		avCodeResponse.setSid(avCode.getSid());
		avCodeResponse.setProblem(avCode.getProblem());
		avCodeResponse.setAvcode(avCode.getAvcode());
		avCodeResponse.setAvcodeN(avCode.getAvcodeN());
		avCodeResponse.setActive(avCode.isActive());
		return avCodeResponse;
	}
	
	public static List<AvCodeResponse> mapAvCodeToAvCodeResponse(List<AvCode> avCodelist) {
		
		List<AvCodeResponse>  avCodeResponselist=new ArrayList<AvCodeResponse>();
		
		if(Collections.isEmpty(avCodelist) ) {
			return avCodeResponselist;
		}
	
		   avCodelist.stream().forEach(avCodei -> {
			
			AvCodeResponse avCodeResponsei = new AvCodeResponse();
		
			avCodeResponsei.setSid(avCodei.getSid());
			avCodeResponsei.setProblem(avCodei.getProblem());
			avCodeResponsei.setAvcode(avCodei.getAvcode());
			avCodeResponsei.setAvcodeN(avCodei.getAvcodeN());
			avCodeResponsei.setActive(avCodei.isActive());
			
			avCodeResponselist.add(avCodeResponsei);

		   } );
	        
		return avCodeResponselist;
	}
	
	
}
