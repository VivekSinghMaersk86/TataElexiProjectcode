package com.tatasky.mcr.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tatasky.mcr.converters.AvCodeConverter;
import com.tatasky.mcr.converters.CauseConverter;
import com.tatasky.mcr.converters.ChannelConverter;
import com.tatasky.mcr.converters.EventTypeConverter;
import com.tatasky.mcr.entity.AvCode;
import com.tatasky.mcr.entity.Cause;
import com.tatasky.mcr.entity.Channel;
import com.tatasky.mcr.entity.EventType;
import com.tatasky.mcr.model.response.AvCodeResponse;
import com.tatasky.mcr.model.response.CauseResponse;
import com.tatasky.mcr.model.response.ChannelResponse;
import com.tatasky.mcr.model.response.EventTypeResponse;
import com.tatasky.mcr.repository.AvCodeMasterRepository;
import com.tatasky.mcr.repository.CauseMasterRepository;
import com.tatasky.mcr.repository.ChannelMasterRepository;
import com.tatasky.mcr.repository.EventTypeMasterRepository;
import com.tatasky.mcr.service.MasterService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MasterServiceImpl implements MasterService{
	
	@Autowired
	private AvCodeMasterRepository avCodeMasterRepostry;
	
	@Autowired
	private ChannelMasterRepository channelMasterRepostry;
	
	@Autowired
	private EventTypeMasterRepository eventTypeMasterRepostry;
	
	@Autowired
	private CauseMasterRepository causeMasterRepository;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,readOnly=true)
	public List<AvCodeResponse> getAllAvCodes() throws Exception {
		try {
		List<AvCode> avCodelist=avCodeMasterRepostry.findAll();
		return AvCodeConverter.mapAvCodeToAvCodeResponse(avCodelist);
		}catch(Exception e) {
			log.error("Error in getAllAvCodes() -> [{}] ",e.getMessage());
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,readOnly=true)
	public List<ChannelResponse> getAllChannel() throws Exception{
		try {
        List<Channel> channellist=channelMasterRepostry.findAll();
		return ChannelConverter.mapChannelToChannelResponse(channellist);
		}catch(Exception e) {
			log.error("Error in getAllChannel() -> [{}] ",e.getMessage());
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,readOnly=true)
	public List<EventTypeResponse> getAllEventType() throws Exception{
		try {
        List<EventType> eventTypelist=eventTypeMasterRepostry.findAll();
		return EventTypeConverter.mapEventTypeToEventTypeResponse(eventTypelist);
		}catch(Exception e) {
			log.error("Error in getAllEventType() -> [{}] ",e.getMessage());
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED ,readOnly=true)
	public List<CauseResponse> getAllCause() throws Exception {
       
		try {
        List<Cause> causeList=causeMasterRepository.findAll();
		return CauseConverter.mapCauseToCauseResponse(causeList);
		}catch(Exception e) {
			log.error("Error in getAllCause() -> [{}] ",e.getMessage());
			throw e;
		}
	}
}
