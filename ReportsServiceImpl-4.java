package com.tatasky.mcr.service.impl;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tatasky.mcr.constants.FaultReportsName;
import com.tatasky.mcr.constants.McrConstants;
import com.tatasky.mcr.entity.Channel;
import com.tatasky.mcr.entity.Fault;
import com.tatasky.mcr.entity.UpLinkTransponder;
import com.tatasky.mcr.repository.ChannelRepository;
import com.tatasky.mcr.repository.FaultRepository;
import com.tatasky.mcr.repository.UpLinkTransponderRepository;
import com.tatasky.mcr.service.ReportsService;
import com.tatasky.mcr.util.CommonUtil;
import com.tatasky.mcr.util.ExcelHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author vikaschoudhary
 *
 */

@Service
@Slf4j
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	private FaultRepository faultRepository;

	@Autowired
	private ChannelRepository channelRepository;
	
	@Autowired
	UpLinkTransponderRepository uplinkTransponderRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public InputStream load(String reportType,Date fromDate, Date toDate) throws Exception {
		FaultReportsName type = FaultReportsName.valueOf(reportType);
		switch (type) {
		case DFAD:
			return generateDFADReport(FaultReportsName.DFAD.getFaultTemplateName(), fromDate, toDate);
		case DFR:
			return generateDFRReport(FaultReportsName.DFR.getFaultTemplateName(), fromDate, toDate);
		case ACTIVEDARSHAN:
			return generateActveDarshanReport(FaultReportsName.ACTIVEDARSHAN.getFaultTemplateName(), fromDate, toDate);
		case MSOR:
			  return generateMSORReport(FaultReportsName.MSOR.getFaultTemplateName() , fromDate, toDate);	
		case MIB:
			  return generateMIBReport(FaultReportsName.MIB.getFaultTemplateName() , fromDate, toDate);	
		case NOCC2:
			  return generateNOCC2Report(FaultReportsName.NOCC2.getFaultTemplateName() , fromDate, toDate);
		case NOCC1:
			  return generateNOCC1Report(FaultReportsName.NOCC1.getFaultTemplateName() , fromDate, toDate);
		case MOR:
			  return generateMORReport(FaultReportsName.MOR.getFaultTemplateName() , fromDate, toDate);
			  
		default:
			throw new Exception();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public InputStream loadid(String reportType,Long sid) throws Exception {
		FaultReportsName type = FaultReportsName.valueOf(reportType);
		switch (type) {
			  
		case FEEDBACKMCR:
			  return generateFEEDBACKMCRReport(FaultReportsName.FEEDBACKMCR.getFaultTemplateName() ,sid);
			
		default:
			throw new Exception();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private InputStream generateDFADReport(String templateName, Date fromDate, Date toDate) throws Exception {
		List<Channel> tataDarshanChannels = channelRepository.findByChannelCode(McrConstants.TATA_DARSHAN_CODE);
		List<Fault> data = faultRepository.findByChannelInAndTxDateBetween(tataDarshanChannels, fromDate, toDate);
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(templateName);
			return ExcelHelper.writeDFAD(data, workbook,
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private InputStream generateDFRReport(String templateName, Date fromDate, Date toDate) throws Exception {
		List<Fault> data = faultRepository.findByTxDateBetween(fromDate, toDate);
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(templateName);
			return ExcelHelper.writeDFR(data, workbook, 
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private InputStream generateActveDarshanReport(String ActveDarshanTemplateName, Date fromDate, Date toDate) throws Exception {
		List<Channel> tataDarshanChannels = channelRepository.findByChannelCode(McrConstants.TATA_DARSHAN_CODE);
		
		List<Fault> data = faultRepository.findByChannelInAndTxDateBetween(tataDarshanChannels,fromDate, toDate);
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(ActveDarshanTemplateName);
			return ExcelHelper.writeActveDarshan_Blank(data, workbook, 
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private InputStream generateMSORReport(String templateName, Date fromDate, Date toDate) throws Exception {
		List<Fault> data = faultRepository.findByTxDateBetween(fromDate, toDate);
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(templateName);
			return ExcelHelper.writeMSOR(data, workbook, 
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	private InputStream generateFEEDBACKMCRReport(String faultTemplateName, Long sid) throws Exception {
		Optional<Fault> fault = faultRepository.findBySid(sid);
		Fault faultx = fault.orElseThrow(() -> new Exception("Resource Not Found"));
	try {
		XSSFWorkbook workbook = ExcelHelper.readTemplate(faultTemplateName);
		return ExcelHelper.writeFEEDBACKMCR(faultx, workbook, sid);
	} catch (Exception e) {
		log.error("Error in exporting file [{}]", e.getMessage());
		throw e;
	}}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED , readOnly = true)
	private InputStream generateMIBReport(String ChanneltemplateName, Date fromDate, Date toDate) throws Exception {
		List<Channel> tataDarshanChannelsx = channelRepository.findAll();
	try {
		XSSFWorkbook workbook = ExcelHelper.readTemplate(ChanneltemplateName);
		return ExcelHelper.writeMIB(tataDarshanChannelsx, workbook,
				CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
						+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
	} catch (Exception e) {
		log.error("Error in exporting file [{}]", e.getMessage());
		throw e;
	}}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED , readOnly = true)
	private InputStream generateNOCC2Report(String TAServiceTemplateName, Date fromDate, Date toDate) throws Exception {
		List<Channel> tataDarshanChannelsy = channelRepository.findAll();
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(TAServiceTemplateName);
			return ExcelHelper.writeNOCC2(tataDarshanChannelsy, workbook,
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED , readOnly = true)
	private InputStream generateNOCC1Report(String NOCC1TemplateName, Date fromDate, Date toDate) throws Exception {
		List<UpLinkTransponder> NOCCChannel = uplinkTransponderRepository.findAll();
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(NOCC1TemplateName);
			return ExcelHelper.writeNOCC1(NOCCChannel, workbook,
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}}
	
	 @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	 private InputStream generateMORReport(String MORtemplateName, Date fromDate, Date toDate) throws Exception {
		List<Fault> data = faultRepository.findByTxDateBetween(fromDate, toDate);
		try {
			XSSFWorkbook workbook = ExcelHelper.readTemplate(MORtemplateName);
			return ExcelHelper.writeMOR(data, workbook, 
					CommonUtil.formatDate(fromDate, McrConstants.DATE_FORMAT_YYYYMMDD) + " to "
							+ CommonUtil.formatDate(toDate, McrConstants.DATE_FORMAT_YYYYMMDD));
		} catch (Exception e) {
			log.error("Error in exporting file [{}]", e.getMessage());
			throw e;
		}
	}


	}
