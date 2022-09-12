package com.tatasky.mcr.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tatasky.mcr.constants.RequestURLConstants;
import com.tatasky.mcr.model.request.CloseRequest;
import com.tatasky.mcr.model.response.FaultResponse;
import com.tatasky.mcr.service.FaultService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Validated
@RequestMapping(value = "/api/faults") 
public class FaultController {

		@Autowired
		private FaultService faultService;
		
		@RequestMapping(value = RequestURLConstants.FAULT, method = RequestMethod.GET)
		@ApiOperation(value = "Retrive Fault API", notes = "API to Retrive Fault")
		public ResponseEntity<?> getAllFaults() throws Exception {                           
			List<FaultResponse> faultResponseList = faultService.getAllFaults();
			return new ResponseEntity<List<FaultResponse>>(faultResponseList ,HttpStatus.OK);                     
	   }
		
		@RequestMapping(value = RequestURLConstants.FAULTPUTID, method = RequestMethod.PUT ,consumes ="application/json") 
	    public ResponseEntity<?> updateUser(@PathVariable("sid") Integer sid, @RequestBody CloseRequest closeRequest)  throws Exception{
	        log.info("Updating Fault with sid {}", sid);
	        return new ResponseEntity<FaultResponse>(faultService.updateFault(sid , closeRequest), HttpStatus.OK);
	    }
}
