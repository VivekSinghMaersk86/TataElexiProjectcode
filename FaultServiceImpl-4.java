package com.tatasky.mcr.service.impl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tatasky.mcr.converters.FaultConverter;
import com.tatasky.mcr.entity.Fault;
import com.tatasky.mcr.model.request.CloseRequest;
import com.tatasky.mcr.model.response.FaultResponse;
import com.tatasky.mcr.repository.FaultMasterRepository;
import com.tatasky.mcr.service.FaultService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class FaultServiceImpl implements FaultService {
	
	@Autowired
	private FaultMasterRepository faultMasterRepository;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,readOnly=true)
	public List<FaultResponse> getAllFaults() throws Exception {
		try {
		List<Fault> faultList=faultMasterRepository.findAll();
		
		return FaultConverter.mapFaultToFaultResponse(faultList);
		}catch(Exception e) {
			log.error("Error in getAllFaults() -> [{}] ",e.getMessage());
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED )
	public FaultResponse updateFault(Integer sid  ,CloseRequest closeRequest) throws Exception {
		Optional<Fault> fault =faultMasterRepository.findById(sid);

		if(fault.isPresent()){
			Fault faultx =fault.get();
			faultMasterRepository.save(FaultConverter.mapCloseRequestToClose(faultx ,closeRequest));
			return FaultConverter.mapCloseToCloseResponse(faultx);
		}else {
			throw new Exception("Resource not found");
		}}
	}
