package com.tatasky.mcr.controller;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tatasky.mcr.constants.RequestURLConstants;
import com.tatasky.mcr.service.ReportsService;
import com.tatasky.mcr.util.CommonUtil;
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
public class ReportsController {

	@Autowired
	private ReportsService reportsService;

	@GetMapping(RequestURLConstants.FAULT_REPORTS)
	@ApiOperation(value = "Generate Fault Reports", notes = "Generate Fault Reports")
	public ResponseEntity<Resource> generateReports(@RequestParam("reportType") String reportType,
			@RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
			@RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws Exception {
		String filename = CommonUtil.getTemplateName(reportType);
		InputStreamResource file = new InputStreamResource(reportsService.load(reportType, fromDate, toDate));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	

	@GetMapping(RequestURLConstants.FAULT_REPORTS_ByID)
	@ApiOperation(value = "Generate Feedback_MCR Reports", notes = "Generate Feedback_MCR Reports")
	public ResponseEntity<Resource> generateFeedbackMCRReport(@RequestParam("reportType") String reportType ,@RequestParam("sid") Long sid) throws Exception {
		String filename = CommonUtil.getTemplateName(reportType);
		InputStreamResource file = new InputStreamResource(reportsService.loadid(reportType ,sid));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}
