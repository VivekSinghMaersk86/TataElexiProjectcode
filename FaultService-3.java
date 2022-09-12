package com.tatasky.mcr.service;
import java.util.List;
import com.tatasky.mcr.model.request.CloseRequest;
import com.tatasky.mcr.model.response.FaultResponse;

public interface FaultService {
		
	public List<FaultResponse> getAllFaults() throws Exception;
	public FaultResponse updateFault(Integer sid ,CloseRequest closeRequest) throws Exception;

}
