package com.tatasky.mcr.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.tatasky.mcr.model.response.AvCodeResponse;
import com.tatasky.mcr.model.response.CauseResponse;
import com.tatasky.mcr.model.response.ChannelResponse;
import com.tatasky.mcr.model.response.EventTypeResponse;

@Service
public interface MasterService {

	public List<AvCodeResponse> getAllAvCodes() throws Exception;
	
	public List<ChannelResponse> getAllChannel() throws Exception;
	
	public List<EventTypeResponse> getAllEventType() throws Exception;
	
	public List<CauseResponse> getAllCause() throws Exception;
}

