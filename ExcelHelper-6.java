package com.tatasky.mcr.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.springframework.util.ResourceUtils;
import com.tatasky.mcr.constants.McrConstants;
import com.tatasky.mcr.entity.Channel;
import com.tatasky.mcr.entity.Fault;
import com.tatasky.mcr.entity.UpLinkTransponder;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelHelper {

	public static XSSFWorkbook readTemplate(String templateName) throws IOException {
		File file = ResourceUtils.getFile("classpath:templates/reports/" + templateName);
		try (FileInputStream fis = new FileInputStream(file);) {
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			return workbook;
		} catch (Exception e) {
			log.error(templateName + " Template Read Error ");
			throw e;
		}
	}

	public static ByteArrayInputStream writeDFAD(List<Fault> faults, XSSFWorkbook workbook, String period) throws ParseException {

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			Row row1 = sheet.getRow(3);
			row1.getCell(0).setCellValue("Report Period:- "+period);
			int lastRowIndex = 5;// sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			int i = 1;
			for (Fault fault : faults) {
				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(0).setCellValue(i++);
				row.createCell(1).setCellValue(
						fault.getChannel() == null ? McrConstants.EMPTY : fault.getChannel().getChannelName());
				row.createCell(2).setCellValue(
						fault.getEventType() == null ? McrConstants.EMPTY : fault.getEventType().getEventType());
				row.createCell(3).setCellValue(fault.getTxDate() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTxDate(), McrConstants.DATE_FORMAT_YYMMDD));
				row.createCell(4).setCellValue(fault.getErrorNo());
				row.createCell(5).setCellValue(fault.getTicket());
				row.createCell(6).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(7).setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(8).setCellValue(fault.getDuration());
				row.createCell(9)
						.setCellValue(fault.getCause() == null ? McrConstants.EMPTY : fault.getCause().getCause());
				row.createCell(10).setCellValue(fault.getTicketStatus());
				row.createCell(11).setCellValue(fault.getRemarks());
				row.createCell(12).setCellValue(fault.getScheduled());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream writeDFR(List<Fault> faults, XSSFWorkbook workbook, String period) throws ParseException {

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			Row row1 = sheet.getRow(3);
			row1.getCell(1).setCellValue("Report Period:- "+period);
			int lastRowIndex = 5;
			int i = 1;
			for (Fault fault : faults) {

				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(0).setCellValue(i++);
				row.createCell(1).setCellValue(
						fault.getChannel() == null ? McrConstants.EMPTY : fault.getChannel().getChannelName());
				row.createCell(2).setCellValue(
						fault.getEventType() == null ? McrConstants.EMPTY : fault.getEventType().getEventType());
				row.createCell(3).setCellValue(fault.getErrorNo());
				row.createCell(4).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.DATE_FORMAT_YYMMDD));
				row.createCell(5).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(6).setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.DATE_FORMAT_YYMMDD));
				row.createCell(7).setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(8).setCellValue(fault.getDuration());
				row.createCell(9)
						.setCellValue(fault.getCause() == null ? McrConstants.EMPTY : fault.getCause().getCause());
				row.createCell(10).setCellValue(fault.getScheduled());
				row.createCell(11).setCellValue(fault.getTicketStatus());
				row.createCell(12).setCellValue(fault.getRemarks());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	}
	
	public static ByteArrayInputStream  writeActveDarshan_Blank(List<Fault> faults, XSSFWorkbook workbook, String period) throws ParseException {

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);			
			Row row1 = sheet.getRow(51);
			row1.getCell(1).setCellValue("Report Period:- "+period); //avCodeSid
			
		// ====================================================	total fault logger began================================================	
			
			List<String> ChannelList=new ArrayList<>();
			 for (Fault faultObjkey : faults) {
				 ChannelList.add(faultObjkey.getChannel().getChannelName());
				 
			 }
			
			Map<String, Integer> faultCountMap = new LinkedHashMap<String, Integer>();
	        for (String channelObjkey : ChannelList) {
	        	if(faultCountMap.containsKey(channelObjkey)) {
	        		
	        		faultCountMap.put(channelObjkey, faultCountMap.get(channelObjkey)+1);
	        }else {
	        	    faultCountMap.put(channelObjkey, 1);
	        }}
//	        	faultCountMap.forEach((Channel, count) -> { 
//         			System.out.println("Key : " + Channel
//	    			+ " value : " + count);
//	    		});

	        	Iterator<String> itr = faultCountMap.keySet().iterator();
	        	int k=0;
	        	while (itr.hasNext())
	        	{
	        	    String key = itr.next();
	        	    Integer value = faultCountMap.get(key);
	        	    Row row = sheet.createRow((k++)+8);
	        	    if(((k++)+8)<=11) {
	        	    	row.createCell(1).setCellValue(key);
						row.createCell(2).setCellValue(value);	
	        	    }
	        	    
//	        	    else{
//	        	    	row.createCell(10).setCellValue(key);
//						row.createCell(11).setCellValue(value);
//	        	    }				
	        	}
	        	
	     // ====================================================total fault logger End================================================
	        	//int finalDr = Integer.parseInt(final1Dr);
        		//int finalDr =Integer.valueOf(final1Dr);
	        	
	     //==============================================Unscheduled Outrages & scheduled Outrages begin-(fault-channel)===============
	        	Map<String, String> ChannelSchMap = new LinkedHashMap<String, String>();
	        	
	        	Map<String, String> ChannelUnschduleMap = new LinkedHashMap<String, String>();

		        for (Fault faultlist : faults) {
		        	
                    if(faultlist.getScheduled().equals("Scheduled") == true) {  //Unscheduled
		           	String channel=faultlist.getChannel().getChannelName();
		        	String duration=faultlist.getDuration();		        	
		        	//ChannelSchMap.put(channel, seconds);
		        	
		        	
		        	
		        	  if(ChannelSchMap.containsKey(channel) ) {	
		        		  
		        		String duration1=faultlist.getDuration();
		        		//int i=Integer.parseInt(duration1);                         
		        		//int i1=Integer.parseInt(duration); 
		        		//int final1Dr=i+i1;                                 		        		
		        		String finalDr=String.valueOf("two"+duration1);
		        		
		        		ChannelSchMap.put(channel, finalDr);
		        }else {
		        	    ChannelSchMap.put(channel, duration);
		        }
		        	  
                 }else if(faultlist.getScheduled().equals("Unscheduled") == true ) {
                	String channel=faultlist.getChannel().getChannelName();
 		        	String duration=faultlist.getDuration();		        	
 		        	//ChannelSchMap.put(channel, seconds);
 		        	
 		        	
 		        	
 		        	  if(ChannelUnschduleMap.containsKey(channel) ) {	
 		        		  
 		        		String duration1=faultlist.getDuration();
 		        		//int i=Integer.parseInt(duration1);                         
 		        		//int i1=Integer.parseInt(duration); 
 		        		//int final1Dr=i+i1;                                 		        		
 		        		String finalDr=String.valueOf("four"+duration1);
 		        		
 		        		ChannelUnschduleMap.put(channel, finalDr);
 		        }else {
 		        	    ChannelUnschduleMap.put(channel, duration);
 		        }          	 
                 }   
		        }
		        
//		            ChannelSchMap.forEach((Channel, timeduration) -> { 
//	         			System.out.println("Channel : " + Channel
//		    			+ " Schedule_Duration : " + timeduration);
//		    		});	
//		            
//		            ChannelUnschduleMap.forEach((Channel, timeduration) -> { 
//	         			System.out.println("Channel : " + Channel
//		    			+ " Unschedule_Duration : " + timeduration);
//		    		});	
	        	
		            Iterator<String> itrCsm = ChannelSchMap.keySet().iterator();
		            Iterator<String> itrUsm = ChannelUnschduleMap.keySet().iterator();
		        	int m=0;
		        	while (itrCsm.hasNext() && itrUsm.hasNext())
		        	{
		        	    String keyCsm = itrCsm.next();
		        	    String valueCsm = ChannelSchMap.get(keyCsm);
		        	    
		        	    String keyUsm = itrUsm.next();
		        	    String valueUsm = ChannelUnschduleMap.get(keyUsm);
		        	    
		        	    Row rowCsm = sheet.createRow((m++)+17);
		        	    rowCsm.createCell(1).setCellValue(keyCsm);
		        	    rowCsm.createCell(2).setCellValue(valueCsm);
		        	    rowCsm.createCell(6).setCellValue(keyUsm);
		        	    rowCsm.createCell(7).setCellValue(valueUsm);
			
		        	}
		        	
//		        	 Iterator<String> itrUsm = ChannelUnschduleMap.keySet().iterator();
//			        	int n=0;
//			        	while (itrUsm.hasNext())
//			        	{
//			        	    String keyUsm = itrUsm.next();
//			        	    String valueUsm = ChannelUnschduleMap.get(keyUsm);
//			        	    Row rowUsm = sheet.createRow((n++)+17);
//			           	    	rowUsm.createCell(6).setCellValue(keyUsm);
//								rowUsm.createCell(7).setCellValue(valueUsm);	
//				
//			        	}
//	        	
		   //================================================Unscheduled Outrages & scheduled Outrages -Logic End-(fault-Channel)==============  
		        	
		  //================================================Unscheduled Outrages & scheduled Outrages -Logic End-(cause)======================= 
				
		        	Map<String, String> CauseSchMap = new LinkedHashMap<String, String>();
       	        	Map<String, String> CauseUnschduleMap = new LinkedHashMap<String, String>();
       	        	
       	        	for (Fault faultlist : faults) {
    		        	
                        if(faultlist.getScheduled().equals("Scheduled") == true) { 
    		           	String cause=faultlist.getCause().getCause();
    		        	String durationC=faultlist.getDuration();		        	
    		        	
    		        	  if(CauseSchMap.containsKey(cause) ) {	

    		        		String durationCC=faultlist.getDuration();
    		        		//String finalDr=String.valueOf("AD"+durationCC);
    		        		
    		        		CauseSchMap.put(cause, durationCC);
    		        }else {
    		        	    CauseSchMap.put(cause, durationC);
    		        }
    		        	  
                     }else if(faultlist.getScheduled().equals("Unscheduled") == true ) {
                    	String causel=faultlist.getCause().getCause();
     		        	String durationUC=faultlist.getDuration();  
     		        	
     		        	  if(CauseUnschduleMap.containsKey(causel) ) {	
     		        		String duration1=faultlist.getDuration();
     		        		
     		        		//Both  duration adding logic need to write here                     
     		        		//String finalDr=String.valueOf("BD"+duration1);
     		        		
     		        		CauseUnschduleMap.put(causel, duration1);
     		        }else {
     		        	    CauseUnschduleMap.put(causel, durationUC);
     		        }          	 
                     }   
    		        }
    		        
//       	        	   CauseSchMap.forEach((cause, timedurationC) -> { 
//    	         			System.out.println("Cause : " + cause
//    		    			+ " Schedule_Duration : " + timedurationC);
//    		    		});	
//    		            
//    		            CauseUnschduleMap.forEach((cause1, timedurationUc) -> { 
//    	         			System.out.println("Cause : " + cause1
//    		    			+ " Unschedule_Duration : " + timedurationUc);
//    		    		});	
//    	        	
    		            Iterator<String> itrCause = CauseSchMap.keySet().iterator();
    		            Iterator<String> itrUCause = CauseUnschduleMap.keySet().iterator();
    		        	int n=0;
    		        	while (itrUCause.hasNext()) 
    		        	{
    		        		while(itrCause.hasNext()) {
    		        	    String keyCause = itrCause.next();
    		        	    String valueCause = CauseSchMap.get(keyCause);
    		        	    Row rowCause = sheet.createRow((n++)+30);
    		        	    rowCause.createCell(1).setCellValue(keyCause);
    		        	    rowCause.createCell(2).setCellValue(valueCause);
    		        		}
    		        		
    		        	    String keyUCause = itrUCause.next();
    		        	    String valueUCause = CauseUnschduleMap.get(keyUCause);
    		        	    Row rowCause = sheet.createRow((n++)+30);
    		        	    rowCause.createCell(6).setCellValue(keyUCause);
    		        	    rowCause.createCell(7).setCellValue(valueUCause);
    			
    		        	}
		        	
		        	
		//================================================Unscheduled Outrages & scheduled Outrages -Logic End-(cause)======================= 
    	  
    	//================================================Cause Channel feed not working && Duration code -Logic Begin-(cause)================ 
    		        	
   		        	 List<String> CauseChannelList=new ArrayList<>();
   					 for (Fault faultObjkey : faults) {
   						 CauseChannelList.add(faultObjkey.getCause().getCause());       //extra code
   					 }	        	
    		        	
   					Map<String, String> OtherMap = new LinkedHashMap<String, String>();
       	        	Map<String, String> TataSkyMap = new LinkedHashMap<String, String>();	
       	        	Map<String, String> SourceCauseMap = new LinkedHashMap<String, String>();	
    		        	
       	        	for (Fault faultlist : faults) {
       	        		
       	        		String causeType=faultlist.getCause().getCauseType();
         	        		
    		        	if(causeType.equals("Source")== true ) {	
    		        		
    		        		String cause=faultlist.getCause().getCause();
    		        		String duration=faultlist.getDuration();

    		        		SourceCauseMap.put(cause, duration);
    		        	}
    		        	
                        if(causeType.equals("Others")== true ) {	
    		        		
    		        		String causetype=faultlist.getCause().getCauseType();
    		        		String duration=faultlist.getDuration();

    		        		OtherMap.put(causetype, duration);
    		        	}
                        
                       if(causeType.equals("TataSky")== true ) {	
    		        		
    		        		String cause=faultlist.getCause().getCauseType();
    		        		String duration=faultlist.getDuration();

    		        		TataSkyMap.put(cause, duration);
    		        	}

                       SourceCauseMap.forEach((cause, causedur) -> { 
    	         			System.out.println("SourceCause : " + cause
    		    			+ " SourceCause_Duration : " + causedur);
    		    		});	
                       
                        OtherMap.forEach((causetype, causedur) -> { 
   	         			System.out.println("OtherCause : " + causetype
   		    			+ " OtherCause_Duration : " + causedur);
   		    		   });	
                       
                       TataSkyMap.forEach((causetype, causedur) -> { 
   	         			System.out.println("TataskyCause : " + causetype
   		    			+ "TataskyCause_Duration : " + causedur);
   		    		    });	
                       
                    Iterator<String> itrOther = OtherMap.keySet().iterator();
   		            Iterator<String> itrTatasky = TataSkyMap.keySet().iterator();
   		            Iterator<String> itrSource = SourceCauseMap.keySet().iterator();
   		         
   		        	int r=0;
   		        	while (itrOther.hasNext() && itrTatasky.hasNext() && itrSource.hasNext()) 
   		        	{
   		        	    String keyOther = itrOther.next();
   		        	    String valueOther = OtherMap.get(keyOther);
   		        	    
   		        	    String keyTatasky = itrTatasky.next();
		        	    String valueUCause = TataSkyMap.get(keyTatasky);
		        	    
		        	    String keySource = itrSource.next();
		        	    String valueSource = SourceCauseMap.get(keySource);
		        	    
   		        	    Row rowOST = sheet.createRow((r++)+37);
   		        	    
   		        	    rowOST.createCell(1).setCellValue(CauseChannelList.get(r));
   		        	    
   		        	    rowOST.createCell(2).setCellValue(keyOther);
   		        	    rowOST.createCell(3).setCellValue(valueOther);
   		        	    
   		        	    rowOST.createCell(4).setCellValue(keyTatasky);
   		        	    rowOST.createCell(5).setCellValue(valueUCause);
   		        	    
   		        	    rowOST.createCell(6).setCellValue(keySource);
   		        	    rowOST.createCell(7).setCellValue(valueSource);
   		        	}}
   	
      //================================================Cause Channel feed not working && Duration code -Logic End-(cause)=================== 
		        	
	        int lastRowIndex =51;
			int i = 1;

			for (Fault fault : faults) {
				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(0).setCellValue(i++);
				row.createCell(1).setCellValue(
						fault.getChannel() == null ? McrConstants.EMPTY : fault.getChannel().getChannelName());
				row.createCell(2).setCellValue(
						fault.getAvCode() == null ? McrConstants.EMPTY1: fault.getAvCode().getAvcode());
				row.createCell(3).setCellValue(fault.getTicket());
				row.createCell(4).setCellValue(fault.getErrorNo());
				
				row.createCell(5).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.DATE_FORMAT_YYMMDD));
				row.createCell(6).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(7).setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.DATE_FORMAT_YYMMDD));
				row.createCell(8).setCellValue(fault.getDuration());
				
				row.createCell(9).setCellValue(fault.getCause() == null ? McrConstants.EMPTY : fault.getCause().getCause());
				row.createCell(10).setCellValue(fault.getTicketStatus());
				row.createCell(11).setCellValue(fault.getRemarks());
				row.createCell(12).setCellValue(fault.getScheduled());
				row.createCell(13).setCellValue(fault.getCreatedBy());
				row.createCell(14).setCellValue(fault.getModificationBy());
				
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	}

	public static InputStream writeMSOR(List<Fault> faults, XSSFWorkbook workbook, String period) throws Exception {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			Row row1 = sheet.getRow(3);
			row1.getCell(0).setCellValue("Report Period:- "+period);
			int lastRowIndex = 5;// sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			int i = 1;
			for (Fault fault : faults) {
				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(0).setCellValue(i++);
				row.createCell(1).setCellValue(fault.getChannel() == null ? McrConstants.EMPTY : fault.getChannel().getChannelName());
				
				//clearance needed for (monthly and yearly )time duration added

				row.createCell(2).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(3).setCellValue(CommonUtil.MonthAndYearsDuration());                    //for Month Duration
				row.createCell(4).setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(5).setCellValue(CommonUtil.MonthAndYearsDuration());                     //for Years Duration
				row.createCell(6).setCellValue(fault.getRemarks());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}}

	public static InputStream writeFEEDBACKMCR(Fault fault, XSSFWorkbook workbook, Long sid) throws Exception {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			Row row1 = sheet.getRow(3);
			row1.getCell(0).setCellValue("Report Period:- "+sid);
			int lastRowIndex = 4;// sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			int i = 1;

				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(0).setCellValue(i++);
				row.createCell(1).setCellValue(
						fault.getChannel() == null ? McrConstants.EMPTY : fault.getChannel().getChannelName());
				row.createCell(2).setCellValue(fault.getErrorNo());
				row.createCell(3).setCellValue(fault.getTxDate() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTxDate(), McrConstants.DATE_FORMAT_YYMMDD));
				row.createCell(4).setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(5).setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.TIME_FORMAT_HHMMSS));
				row.createCell(6).setCellValue(fault.getDuration());
				row.createCell(7)
				.setCellValue(fault.getCause() == null ? McrConstants.EMPTY : fault.getCause().getCause());
				row.createCell(8).setCellValue(
						fault.getEventType() == null ? McrConstants.EMPTY : fault.getEventType().getEventType());
				row.createCell(9).setCellValue(fault.getEventParticular());
				row.createCell(10).setCellValue(fault.getActionTaken());
				row.createCell(11).setCellValue(fault.getActionConfirm());
				row.createCell(12).setCellValue(fault.getTicketStatus());
				row.createCell(12).setCellValue(McrConstants.EMPTY);           
		
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	}
	
 private static CellStyle getStyle(Workbook workbook, BorderStyle top, BorderStyle bottom, BorderStyle left,
			BorderStyle right, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment) {
		CellStyle style = workbook.createCellStyle();
	//	style.setBorderTop(top);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	//	style.setBorderBottom(bottom);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	//	style.setBorderLeft(left);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	//	style.setBorderRight(right);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	//	style.setVerticalAlignment(verticalAlignment);
	//	style.setAlignment(horizontalAlignment);
		style.setWrapText(true);
		return style;
	}
	
	public static InputStream writeMIB(List<Channel> data, XSSFWorkbook workbook, String period) throws Exception{
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			Row row1 = sheet.getRow(3);
			row1.getCell(0).setCellValue("Report Period:- "+period);
			int lastRowIndex = 2;// sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			int i = 1;
			for (Channel channel : data) {
				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(0).setCellValue(i++);
				row.createCell(1).setCellValue("M/s Tata Sky Limited");
				row.createCell(2).setCellValue("STV(DTH)-21/1-2+2 S/B");
				row.createCell(3).setCellValue(null==channel.getSatellite()?McrConstants.EMPTY:channel.getSatellite().getSatelliteName());
				row.createCell(4).setCellValue(channel.getChannelName());
				row.createCell(5).setCellValue(null==channel.getCompany()?McrConstants.EMPTY:channel.getCompany().getCompanyName());
				row.createCell(6).setCellValue(channel.getLaunchDate() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(channel.getLaunchDate(), McrConstants.DATE_FORMAT_YYYYMMDD1));
				row.createCell(7).setCellValue(channel.isActive()?"Active":"InActive");
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	}

	public static InputStream writeNOCC2(List<Channel> AllChannel, XSSFWorkbook workbook, String period) throws Exception {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(1);
			int lastRowIndex = 1; // sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			int i = 1;
			int x= 0 ;
			int y= 100;
			int M = 1 ;
			int N = 102 ;
			int Q = 201;
			for (Channel channel : AllChannel) {
								
				Row row = sheet.createRow(lastRowIndex++);
				
				if(lastRowIndex > M && lastRowIndex < N && i <  Q ) {
					
				row.createCell(0).setCellValue(x + (i++));
				row.createCell(1).setCellValue(null==channel.getChannelName()?McrConstants.EMPTY:channel.getChannelName());
				row.createCell(2).setCellValue(null==channel.getSatellite()?McrConstants.EMPTY:channel.getSatellite().getSatelliteName());
				row.createCell(3).setCellValue("14310");
				row.createCell(4).setCellValue("71.249");
				
				i--;
				
				row.createCell(7).setCellValue(y + (i++));
				row.createCell(8).setCellValue(null==channel.getChannelName()?McrConstants.EMPTY:channel.getChannelName());
				row.createCell(9).setCellValue(null==channel.getSatellite()?McrConstants.EMPTY:channel.getSatellite().getSatelliteName());
				row.createCell(10).setCellValue("14390");
				row.createCell(11).setCellValue("63.334");
				
				}else if(lastRowIndex >= N) {
					x = x + 200;
					y = y + 200 ;
					M = M + 103 ;
					N = N + 103 ;
					Q = Q + 200 ;
					
					if(lastRowIndex > M && lastRowIndex < N && i <  Q ) {
						
						row.createCell(0).setCellValue(x + (i++));
						row.createCell(1).setCellValue(null==channel.getChannelName()?McrConstants.EMPTY:channel.getChannelName());
						row.createCell(2).setCellValue(null==channel.getSatellite()?McrConstants.EMPTY:channel.getSatellite().getSatelliteName());
						row.createCell(3).setCellValue("14310");
						row.createCell(4).setCellValue("71.249");
						
						i--;
						
						row.createCell(7).setCellValue(y + (i++));
						row.createCell(8).setCellValue(null==channel.getChannelName()?McrConstants.EMPTY:channel.getChannelName());
						row.createCell(9).setCellValue(null==channel.getSatellite()?McrConstants.EMPTY:channel.getSatellite().getSatelliteName());
						row.createCell(10).setCellValue("14390");
						row.createCell(11).setCellValue("63.334");
						
						}
				}				
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	
	
	}

	public static InputStream writeNOCC1(List<UpLinkTransponder> nOCCChannels, XSSFWorkbook workbook, String period) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			int lastRowIndex = 12;// sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			for (UpLinkTransponder ulTras : nOCCChannels) {
				Row row = sheet.createRow(lastRowIndex++);
				row.createCell(1).setCellValue(ulTras.getSatelliteNameAndTransponderNo());
				row.createCell(2).setCellValue(ulTras.getSpaceSegmentBandWidth());
				row.createCell(3).setCellValue(ulTras.getFrequencyRange());
				row.createCell(4).setCellValue(ulTras.getAntennaSize());
				row.createCell(5).setCellValue(ulTras.getCarrierType());
				row.createCell(6).setCellValue("75");                              // constant No Taken (No Clarity)
				row.createCell(7).setCellValue(ulTras.getSSFrequencyDataRate());
				row.createCell(8).setCellValue(ulTras.getOperationalDate());
				row.createCell(9).setCellValue(ulTras.getTechnologyTypeDVBS());
				
				row.createCell(10).setCellValue(ulTras.getTechnologyTypeMPEG());
				row.createCell(11).setCellValue(ulTras.getTechnologyTypeSDHD());
				row.createCell(12).setCellValue(ulTras.getRemark());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}
	}

	public static InputStream writeMOR(List<Fault> data, XSSFWorkbook workbook, String string) throws Exception {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.getSheetAt(0);
			int lastRowIndex = 12;// sheet.getLastRowNum();
			System.out.println("***** count *****" + lastRowIndex);
			
			List<String> listApr21=new ArrayList<String>();
			List<String> listMay21=new ArrayList<String>();
			List<String> listJune21=new ArrayList<String>();
			List<String> listJuly21=new ArrayList<String>();
			List<String> listAug21=new ArrayList<String>();
			List<String> listSep21=new ArrayList<String>();
			List<String> listOct21=new ArrayList<String>();
			List<String> listNov21=new ArrayList<String>();
			List<String> listDec21=new ArrayList<String>();
			List<String> listJan22=new ArrayList<String>();
			List<String> listFeb22=new ArrayList<String>();
			List<String> listMarch22=new ArrayList<String>();
			List<String> listTotal22=new ArrayList<String>();
			
			List<String> OAlistBaseband = new ArrayList<String>();
			List<String> OAlistPlayout = new ArrayList<String>();
			List<String> OAlistCompression = new ArrayList<String>();
			List<String> OAlistCA = new ArrayList<String>();
			List<String> OAlistUpLink = new ArrayList<String>();
			List<String> OAlistSource = new ArrayList<String>();
			List<String> OAlistRainOutage = new ArrayList<String>();
			List<String> OAlistTataSkyDarshan = new ArrayList<String>();
			List<String> OAlistBroadCastIt = new ArrayList<String>();
			
			for (Fault fault : data) {

				if( lastRowIndex >= 12 && lastRowIndex <=23  ) {
				Row row = sheet.createRow(lastRowIndex++);
				
				if(lastRowIndex == 13) {
				row.createCell(1).setCellValue("Apr-21");
				
				for(int x = 0 ; x <= 16 ; x += 2 )	{
	              Cell cell1 = row.createCell(5+x);
	              CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
				  BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
	              cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
	              cell1.setCellStyle(cellStyle);
	              cell1.setCellFormula("1" +  (13) + "/" + (30));
				}
				
				}else if(lastRowIndex == 14) {
				row.createCell(1).setCellValue("May-21");
				
				for(int x = 0 ; x <= 16 ; x += 2 )	{
				Cell cell1 = row.createCell(5+x);
                CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
				BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
                cell1.setCellStyle(cellStyle);
                cell1.setCellFormula("1" +  (14) + "/" + (31));
				}
				
				}else if(lastRowIndex == 15) {
					row.createCell(1).setCellValue("June-21");
					
				for(int x = 0 ; x <= 16 ; x += 2 )	{
				Cell cell1 = row.createCell(5+x);
	            CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
				BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
	            cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
	            cell1.setCellStyle(cellStyle);
	            cell1.setCellFormula("1" +  (15) + "/" + (30));
				}
					
				}else if(lastRowIndex == 16) {
					row.createCell(1).setCellValue("July-21");
					
				for(int x = 0 ; x <= 16 ; x += 2 )	{
					Cell cell1 = row.createCell(5+x);
			        CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
			        cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
			        cell1.setCellStyle(cellStyle);
			        cell1.setCellFormula("1" +  (16) + "/" + (31));
				   }
					
				}else if(lastRowIndex == 17) {
					row.createCell(1).setCellValue("Aug-21");
					
				 for(int x = 0 ; x <= 16 ; x += 2 )	{
				    Cell cell1 = row.createCell(5+x);
				    CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
				    BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
				    cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
				    cell1.setCellStyle(cellStyle);
				    cell1.setCellFormula("1" +  (17) + "/" + (31));
					}
					
				}else if(lastRowIndex == 18) {
					row.createCell(1).setCellValue("Sep-21");
					
				 for(int x = 0 ; x <= 16 ; x += 2 )	{
					 Cell cell1 = row.createCell(5+x);
				     CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
				     BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
				     cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
				     cell1.setCellStyle(cellStyle);
					 cell1.setCellFormula("1" +  (18) + "/" + (30));
					}	
					
				}else if(lastRowIndex == 19) {
					row.createCell(1).setCellValue("Oct-21");
					
				  for(int x = 0 ; x <= 16 ; x += 2 )	{
					  Cell cell1 = row.createCell(5+x);
					  CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					  BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
					  cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
					  cell1.setCellStyle(cellStyle);
					  cell1.setCellFormula("1" +  (19) + "/" + (31));
					 }	
					
				}else if(lastRowIndex == 20) {
					row.createCell(1).setCellValue("Nov-21");
					
				for(int x = 0 ; x <= 16 ; x += 2 )	{
					 Cell cell1 = row.createCell(5+x);
					 CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					 BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
					 cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
					 cell1.setCellStyle(cellStyle);
					 cell1.setCellFormula("1" +  (20) + "/" + (30));
					}	
					
				}else if(lastRowIndex == 21) {
					row.createCell(1).setCellValue("Dec-21");
				
				 for(int x = 0 ; x <= 16 ; x += 2 )	{
					 Cell cell1 = row.createCell(5+x);
					 CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					 BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
					 cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
					 cell1.setCellStyle(cellStyle);
					 cell1.setCellFormula("1" +  (21) + "/" + (31));
					}	
					
					
				}else if(lastRowIndex == 22) {
					row.createCell(1).setCellValue("Jan-22");
					
				  for(int x = 0 ; x <= 16 ; x += 2 )	{
					 Cell cell1 = row.createCell(5+x);
					 CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					 BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
					 cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
					 cell1.setCellStyle(cellStyle);
					 cell1.setCellFormula("1" +  (22) + "/" + (31));
					}	
					
				}else if(lastRowIndex == 23) {
					row.createCell(1).setCellValue("Feb-22");
					
				for(int x = 0 ; x <= 16 ; x += 2 )	{
					 Cell cell1 = row.createCell(5+x);
				     CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					 BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
					 cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
					 cell1.setCellStyle(cellStyle);
					 cell1.setCellFormula("1" +  (23) + "/" + (28));
				    }	

				}else if(lastRowIndex == 24) {
					row.createCell(1).setCellValue("Mar-22");
					
				for(int x = 0 ; x <= 16 ; x += 2 )	{
					Cell cell1 = row.createCell(5+x);
					CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
					BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
					cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
					cell1.setCellStyle(cellStyle);
					cell1.setCellFormula("1" +  (24) + "/" + (31));
				  }	
					
				}
				
				String totalTime = CommonUtil.addStringTime(fault.getDuration(), fault.getDuration());
				for(int i=1 ; i<= data.size() ; i++) {
					totalTime = CommonUtil.addStringTime(totalTime, fault.getDuration());
			    }
				
				row.createCell(4).setCellValue(totalTime);    
				OAlistBaseband.add(totalTime);
              	row.createCell(6).setCellValue(totalTime);
              	OAlistPlayout.add(totalTime);
				row.createCell(8).setCellValue(totalTime);
				OAlistCompression.add(totalTime);
				row.createCell(10).setCellValue(totalTime);
				OAlistCA.add(totalTime);
				row.createCell(12).setCellValue(totalTime);
				OAlistUpLink.add(totalTime);
		     	if ( (fault.getCause().getCause()).equals("Source")==true ) {   
				row.createCell(14).setCellValue(fault.getDuration());
				OAlistSource.add(fault.getDuration());
				}
		     	
				row.createCell(16).setCellValue(totalTime); 
				OAlistRainOutage.add(totalTime);
				row.createCell(18).setCellValue(totalTime);
				OAlistTataSkyDarshan.add(totalTime);
     			row.createCell(20).setCellValue(totalTime);
     			OAlistBroadCastIt.add(totalTime);

				}else if ( lastRowIndex == 24) {
					Row row = sheet.createRow(lastRowIndex++);
					
      				row.createCell(1).setCellValue("TOTAL");
      				
					String OAtotalTimeBaseband = CommonUtil.addStringTime(OAlistBaseband.get(0), OAlistBaseband.get(1));
					for(int i=2 ; i< OAlistBaseband.size() ; i++) {
					    OAtotalTimeBaseband = CommonUtil.addStringTime(OAtotalTimeBaseband, OAlistBaseband.get(i));
					}
					row.createCell(4).setCellValue(OAtotalTimeBaseband);

					String OAtotalTimePlayout = CommonUtil.addStringTime(OAlistPlayout.get(0), OAlistPlayout.get(1));
					for(int i=2 ; i< OAlistPlayout.size() ; i++) {
						OAtotalTimePlayout = CommonUtil.addStringTime(OAtotalTimePlayout, OAlistPlayout.get(i));
					}
					row.createCell(6).setCellValue(OAtotalTimePlayout);
					
					String OAtotalCompressionTime = CommonUtil.addStringTime(OAlistCompression.get(0), OAlistCompression.get(1));
					for(int i=2 ; i< OAlistCompression.size() ; i++) {
						OAtotalCompressionTime = CommonUtil.addStringTime(OAtotalCompressionTime, OAlistCompression.get(i));
					}
					row.createCell(8).setCellValue(OAtotalCompressionTime);
					
					String OAtotalCATime = CommonUtil.addStringTime(OAlistCA.get(0), OAlistCA.get(1));
					for(int i=2 ; i< OAlistCA.size() ; i++) {
						OAtotalCATime = CommonUtil.addStringTime(OAtotalCATime, OAlistCA.get(i));
					}
					row.createCell(10).setCellValue(OAtotalCATime);
					
					String OAtotalUpLinkTime = CommonUtil.addStringTime(OAlistUpLink.get(0), OAlistUpLink.get(1));
					for(int i=2 ; i< OAlistUpLink.size() ; i++) {
						OAtotalUpLinkTime = CommonUtil.addStringTime(OAtotalUpLinkTime, OAlistUpLink.get(i));
					}
					row.createCell(12).setCellValue(OAtotalUpLinkTime);
					
					String OAtotalTimeSource = CommonUtil.addStringTime(OAlistSource.get(0), OAlistSource.get(1));
					for(int i=2 ; i< OAlistSource.size() ; i++) {
						OAtotalTimeSource = CommonUtil.addStringTime(OAtotalTimeSource, OAlistSource.get(i));
					}
					row.createCell(14).setCellValue(OAtotalTimeSource);
					
					String OAtotalTimeRainOutage = CommonUtil.addStringTime(OAlistRainOutage.get(0), OAlistRainOutage.get(1));
					for(int i=2 ; i< OAlistRainOutage.size() ; i++) {
						OAtotalTimeRainOutage = CommonUtil.addStringTime(OAtotalTimeRainOutage, OAlistRainOutage.get(i));
					}
					row.createCell(16).setCellValue(OAtotalTimeRainOutage);
					
					String OAtotalTimeTataSkyDarshan = CommonUtil.addStringTime(OAlistTataSkyDarshan.get(0), OAlistTataSkyDarshan.get(1));
					for(int i=2 ; i< OAlistTataSkyDarshan.size() ; i++) {
						OAtotalTimeTataSkyDarshan = CommonUtil.addStringTime(OAtotalTimeTataSkyDarshan, OAlistTataSkyDarshan.get(i));
					}
					row.createCell(18).setCellValue(OAtotalTimeTataSkyDarshan);
					
					String OAtotalTimeBroadCastIt = CommonUtil.addStringTime(OAlistBroadCastIt.get(0), OAlistBroadCastIt.get(1));
					for(int i=2 ; i< OAlistBroadCastIt.size() ; i++) {
						OAtotalTimeBroadCastIt = CommonUtil.addStringTime(OAtotalTimeBroadCastIt, OAlistBroadCastIt.get(i));
					}
					row.createCell(20).setCellValue(OAtotalTimeBroadCastIt);

					 for(int x = 0 ; x <= 16 ; x += 2 )	{
						 Cell cell1 = row.createCell(5+x);
						 CellStyle cellStyle = getStyle(workbook, BorderStyle.DASHED, BorderStyle.DASHED, BorderStyle.DOUBLE,
						 BorderStyle.DOUBLE, VerticalAlignment.CENTER, HorizontalAlignment.RIGHT);
						 cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0000%"));
						 cell1.setCellStyle(cellStyle);
						 cell1.setCellFormula("1" +  (24) + "/" + (30.417));
						}	
					
				}else if ( lastRowIndex >= 27 && lastRowIndex <=30  ) {
					
				Row row = sheet.createRow(lastRowIndex++);
				
				if ( (fault.getCause().getCauseType()).equals("Source")==true && lastRowIndex ==28 ) { //totalTime2
				
				row.createCell(1).setCellValue("Source"); 
				
				String totalTime = CommonUtil.addStringTime(fault.getDuration(), fault.getDuration());
				for(int i=1 ; i<= data.size() ; i++) {
					totalTime = CommonUtil.addStringTime(totalTime, fault.getDuration());
			    }
				row.createCell(4).setCellValue(totalTime);
				listApr21.add(totalTime);
				row.createCell(5).setCellValue(totalTime);
				listMay21.add(totalTime);
				row.createCell(6).setCellValue(totalTime); 
				listJune21.add(totalTime);
				row.createCell(7).setCellValue(totalTime);
				listJuly21.add(totalTime);
				row.createCell(8).setCellValue(totalTime); 
				listAug21.add(totalTime);
				if(fault.getTxDate().getMonth()== 6) {
				row.createCell(9).setCellValue(totalTime);
				listSep21.add(totalTime);
				}
				row.createCell(10).setCellValue(totalTime);
				listOct21.add(totalTime);
				row.createCell(11).setCellValue(totalTime);
				listNov21.add(totalTime);
				row.createCell(12).setCellValue(totalTime); 
				listDec21.add(totalTime);
				row.createCell(13).setCellValue(totalTime);
				listJan22.add(totalTime);
				row.createCell(14).setCellValue(totalTime);  
				listFeb22.add(totalTime);
				row.createCell(15).setCellValue(totalTime);
				listMarch22.add(totalTime);
				
				String totalTime1 = CommonUtil.addStringTime(totalTime, totalTime);
				for(int i=1 ; i<= 10 ; i++) {
					totalTime1 = CommonUtil.addStringTime(totalTime1, totalTime);
			    }
				row.createCell(16).setCellValue(totalTime1);
				listTotal22.add(totalTime1);
				
			   }else if ( lastRowIndex == 30 ) {      // (fault.getCause().getCauseType()).equals("TataSky")==true && "Others"
				    
				    row.createCell(1).setCellValue("Others"); 
				    
				    String totalTime = CommonUtil.addStringTime(fault.getDuration(), fault.getDuration());
					for(int i=1 ; i<= data.size() ; i++) {
						totalTime = CommonUtil.addStringTime(totalTime, fault.getDuration());
				    }
					//row.getCell(0).getStringCellValue();
					row.createCell(4).setCellValue(totalTime);  
					listApr21.add(totalTime);
					row.createCell(5).setCellValue(totalTime);
					listMay21.add(totalTime);
					row.createCell(6).setCellValue(totalTime); 
					listJune21.add(totalTime);
					row.createCell(7).setCellValue(totalTime);
					listJuly21.add(totalTime);
					row.createCell(8).setCellValue(totalTime); 
					listAug21.add(totalTime);
					row.createCell(9).setCellValue(totalTime);
					listSep21.add(totalTime);
					row.createCell(10).setCellValue(totalTime); 
					listOct21.add(totalTime);
					row.createCell(11).setCellValue(totalTime);
					listNov21.add(totalTime);
					row.createCell(12).setCellValue(totalTime); 
					listDec21.add(totalTime);
					row.createCell(13).setCellValue(totalTime);
					listJan22.add(totalTime);
					row.createCell(14).setCellValue(totalTime);  
					listFeb22.add(totalTime);
					row.createCell(15).setCellValue(totalTime);
					listMarch22.add(totalTime);

					String totalTime1 = CommonUtil.addStringTime(totalTime, totalTime);
					for(int i=1 ; i<= 10 ; i++) {
						totalTime1 = CommonUtil.addStringTime(totalTime1, totalTime);
				    }
					row.createCell(16).setCellValue(totalTime1);
					listTotal22.add(totalTime1);
					
				}else if ( lastRowIndex == 29 )  {
					row.createCell(1).setCellValue("TSL");
					
				    String totalTime = CommonUtil.addStringTime(fault.getDuration(), fault.getDuration());
					for(int i=1 ; i<= data.size() ; i++) {
						totalTime = CommonUtil.addStringTime(totalTime, fault.getDuration());
				    }
					
					row.createCell(4).setCellValue(totalTime);
					listApr21.add(totalTime);
					row.createCell(5).setCellValue(totalTime);
					listMay21.add(totalTime);
					row.createCell(6).setCellValue(totalTime); 
					listJune21.add(totalTime);
					row.createCell(7).setCellValue(totalTime);
					listJuly21.add(totalTime);
					row.createCell(8).setCellValue(totalTime);
					listAug21.add(totalTime);
					row.createCell(9).setCellValue(totalTime);
					listSep21.add(totalTime);
					row.createCell(10).setCellValue(totalTime);    
					listOct21.add(totalTime);
					row.createCell(11).setCellValue(totalTime);
					listNov21.add(totalTime);
					row.createCell(12).setCellValue(totalTime);  
					listDec21.add(totalTime);
					row.createCell(13).setCellValue(totalTime);
					listJan22.add(totalTime);
					row.createCell(14).setCellValue(totalTime);
					listFeb22.add(totalTime);
					row.createCell(15).setCellValue(totalTime);
					listMarch22.add(totalTime);
					
					String totalTime1 = CommonUtil.addStringTime(totalTime, totalTime);
					for(int i=1 ; i<= 10 ; i++) {
						totalTime1 = CommonUtil.addStringTime(totalTime1, totalTime);
				    }
					row.createCell(16).setCellValue(totalTime1);
					listTotal22.add(totalTime1);
					
				}else if ( lastRowIndex == 31 ) { 

					 row.createCell(1).setCellValue("Total");
					 String AprtotalTime = CommonUtil.addStringTime(listApr21.get(0), listApr21.get(1));
					 AprtotalTime = CommonUtil.addStringTime(AprtotalTime, listApr21.get(2));
					 row.createCell(4).setCellValue(AprtotalTime);
					 
					 String MaytotalTime = CommonUtil.addStringTime(listMay21.get(0), listMay21.get(1));
					 MaytotalTime = CommonUtil.addStringTime(MaytotalTime, listMay21.get(2));
					 row.createCell(5).setCellValue(MaytotalTime);
					 
					 String JunetotalTime = CommonUtil.addStringTime(listJune21.get(0), listJune21.get(1));
					 JunetotalTime = CommonUtil.addStringTime(JunetotalTime, listJune21.get(2));
					 row.createCell(6).setCellValue(JunetotalTime);
					 
					 String JulytotalTime = CommonUtil.addStringTime(listJuly21.get(0), listJuly21.get(1));
					 JulytotalTime = CommonUtil.addStringTime(JulytotalTime, listJuly21.get(2));
					 row.createCell(7).setCellValue(JulytotalTime);
					 
					 String AugtotalTime = CommonUtil.addStringTime(listAug21.get(0), listAug21.get(1));
					 AugtotalTime = CommonUtil.addStringTime(AugtotalTime, listAug21.get(2));
					 row.createCell(8).setCellValue(AugtotalTime);
					 
					 String SeptotalTime = CommonUtil.addStringTime(listSep21.get(0), listSep21.get(1));
					 SeptotalTime = CommonUtil.addStringTime(SeptotalTime, listSep21.get(2));
					 row.createCell(9).setCellValue(SeptotalTime);
					 
					 String OcttotalTime = CommonUtil.addStringTime(listOct21.get(0), listOct21.get(1));
					 OcttotalTime = CommonUtil.addStringTime(OcttotalTime, listOct21.get(2));
					 row.createCell(10).setCellValue(OcttotalTime);
					 
					 String NovtotalTime = CommonUtil.addStringTime(listNov21.get(0), listNov21.get(1));
					 NovtotalTime = CommonUtil.addStringTime(NovtotalTime, listNov21.get(2));
					 row.createCell(11).setCellValue(NovtotalTime);
					 
					 String DectotalTime = CommonUtil.addStringTime(listDec21.get(0), listDec21.get(1));
					 DectotalTime = CommonUtil.addStringTime(DectotalTime, listDec21.get(2));
					 row.createCell(12).setCellValue(DectotalTime);
					 
					 String JanTotalTime = CommonUtil.addStringTime(listJan22.get(0), listJan22.get(1));
					 JanTotalTime = CommonUtil.addStringTime(JanTotalTime, listJan22.get(2));
					 row.createCell(13).setCellValue(JanTotalTime);
					 
					 String FebtotalTime = CommonUtil.addStringTime(listFeb22.get(0), listFeb22.get(1));
					 FebtotalTime = CommonUtil.addStringTime(FebtotalTime, listFeb22.get(2));
					 row.createCell(14).setCellValue(FebtotalTime);
					 
					 String MarchTotalTime = CommonUtil.addStringTime(listMarch22.get(0), listMarch22.get(1));
					 MarchTotalTime = CommonUtil.addStringTime(MarchTotalTime, listMarch22.get(2));
					 row.createCell(15).setCellValue(MarchTotalTime);
					 
					 String FaultLoggedtotal = CommonUtil.addStringTime(listTotal22.get(0), listTotal22.get(1));
					 FaultLoggedtotal = CommonUtil.addStringTime(FaultLoggedtotal, listTotal22.get(2));
					 row.createCell(16).setCellValue(FaultLoggedtotal);
	
				}	
				}else if ( lastRowIndex >= 24 && lastRowIndex < 27  ) {
					lastRowIndex++;
					
				}else {
					break;
				}}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data to Excel file: " + e.getMessage());
		}}
}
