package com.tatasky.mcr.service;
import java.io.InputStream;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public interface ReportsService {

	InputStream load(String reportType, Date fromDate, Date toDate) throws Exception;
	
	InputStream loadid(String reportType,Long sid) throws Exception;

}

