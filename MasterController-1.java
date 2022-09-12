package com.tatasky.mcr.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tatasky.mcr.constants.RequestURLConstants;
import com.tatasky.mcr.model.response.AvCodeResponse;
import com.tatasky.mcr.model.response.CauseResponse;
import com.tatasky.mcr.model.response.ChannelResponse;
import com.tatasky.mcr.model.response.EventTypeResponse;
import com.tatasky.mcr.service.MasterService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author vikaschoudhary
 *
 */
@RestController
@Slf4j
@Validated
@RequestMapping(value = "/master")
public class MasterController {

	@Autowired
	private MasterService masterService;

	@RequestMapping(value = RequestURLConstants.AVCODE, method = RequestMethod.GET)
	@ApiOperation(value = "Retrive AvCode API", notes = "API to Retrive AvCode")
	public ResponseEntity<?> getAllAvCodes() throws Exception {                             
		List<AvCodeResponse> avCodeResponseList = masterService.getAllAvCodes();
		return new ResponseEntity<List<AvCodeResponse>>(avCodeResponseList ,HttpStatus.OK);                
	}

	@RequestMapping(value = RequestURLConstants.CHANNEL, method = RequestMethod.GET)
	@ApiOperation(value = "Retrive Channel API", notes = "API to Retrive Channel")
	public ResponseEntity<?> getAllChannel() throws Exception {                                         
		List<ChannelResponse> channelResponseList = masterService.getAllChannel();
		return new ResponseEntity<List<ChannelResponse>>(channelResponseList ,HttpStatus.OK);      
	}
	
	@RequestMapping(value = RequestURLConstants.EVENTTYPE, method = RequestMethod.GET)
	@ApiOperation(value = "Retrive EventType API", notes = "API to Retrive EventType")
	public ResponseEntity<?> getAllEventType() throws Exception {                            
		List<EventTypeResponse> eventTypeResponseList = masterService.getAllEventType();
		return new ResponseEntity<List<EventTypeResponse>>(eventTypeResponseList ,HttpStatus.OK);          
	}
	
	@RequestMapping(value = RequestURLConstants.CAUSE, method = RequestMethod.GET)
	@ApiOperation(value = "Retrive Cause API", notes = "API to Retrive Cause")
	public ResponseEntity<?>  getAllCause() throws Exception {                                         
		List<CauseResponse> causeResponseList = masterService.getAllCause();
		return new ResponseEntity<List<CauseResponse>>(causeResponseList ,HttpStatus.OK);               
	}
}
