package com.tatasky.mcr.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tatasky.mcr.constants.McrConstants;
import com.tatasky.mcr.entity.Fault;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExportsExcelHelper {

	/**
	 * Write DFAD report
	 * 
	 * @param faults
	 * @param workbook
	 * @param period
	 * @return
	 * @throws ParseException
	 */
	public static ByteArrayInputStream exportFaults(List<Fault> faults) throws ParseException {

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	        XSSFWorkbook workbook = new XSSFWorkbook(); 
			Sheet sheet = workbook.createSheet("Faults");
			String[] columns = new String[] { "S.No.", "Channel", "Event Type", "Date \r\n" + "			(yyyy-mm-dd)",
					"Error No.", "Ticket No.", "Start (hh:mm:ss)", "End (hh:mm:ss)", "Duration (hh:mm:ss)", "Cause",
					"Status", "Remarks", "Schedule / Unschedule" };
			Row row = sheet.createRow(0);
			row.setHeightInPoints(30);
			CellStyle cellStyle = getStyle(workbook, BorderStyle.MEDIUM, BorderStyle.MEDIUM, BorderStyle.MEDIUM,
					BorderStyle.MEDIUM, VerticalAlignment.CENTER, HorizontalAlignment.CENTER);
			Font font = workbook.createFont();
			font.setBold(true);
			short size = 10;
			font.setFontHeightInPoints(size);
			font.setFontName("Arial");
			cellStyle.setFont(font);
			cellStyle.setFont(font);
			int colIndex = 0;
			for (String col : columns) {
				Cell cell = row.createCell(colIndex++);
				cell.setCellValue(col);
				cell.setCellStyle(cellStyle);
			}

			int lastRowIndex = 1;
			int i = 1;
			BorderStyle bottomBorderStyle = BorderStyle.THIN;
			for (Fault fault : faults) {
				// If last row then applied bottom border MEDIUM
				if (faults.size() == i) {
					bottomBorderStyle = BorderStyle.MEDIUM;
				}
				row = sheet.createRow(lastRowIndex++);
				row.setHeightInPoints(30);
				Cell cell = row.createCell(0);
				cell.setCellValue(i++);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.MEDIUM,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell = row.createCell(1);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.LEFT));
				cell.setCellValue(
						fault.getChannel() == null ? McrConstants.EMPTY : fault.getChannel().getChannelName());
				cell = row.createCell(2);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(
						fault.getEventType() == null ? McrConstants.EMPTY : fault.getEventType().getEventType());
				cell = row.createCell(3);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.DATE_FORMAT_YYYYMMDD_MINUS_SEPERATED));
				cell = row.createCell(4);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getErrorNo());
				cell = row.createCell(5);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getTicket());
				cell = row.createCell(6);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getTimeIn() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeIn(), McrConstants.TIME_FORMAT_HHMMSS));
				cell = row.createCell(7);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getTimeOut() == null ? McrConstants.EMPTY
						: CommonUtil.formatDate(fault.getTimeOut(), McrConstants.TIME_FORMAT_HHMMSS));
				cell = row.createCell(8);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getDuration());
				cell = row.createCell(9);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getCause() == null ? McrConstants.EMPTY : fault.getCause().getCause());
				cell = row.createCell(10);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.CENTER));
				cell.setCellValue(fault.getTicketStatus());
				cell = row.createCell(11);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.THIN, VerticalAlignment.CENTER, HorizontalAlignment.LEFT));
				cell.setCellValue(fault.getRemarks());
				cell = row.createCell(12);
				cell.setCellStyle(getStyle(workbook, BorderStyle.THIN, bottomBorderStyle, BorderStyle.THIN,
						BorderStyle.MEDIUM, VerticalAlignment.CENTER, HorizontalAlignment.LEFT));
				cell.setCellValue(fault.getScheduled());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("failed to exports data" + e.getMessage());
		}
	}

	private static CellStyle getStyle(Workbook workbook, BorderStyle top, BorderStyle bottom, BorderStyle left,
			BorderStyle right, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment) {
		CellStyle style = workbook.createCellStyle();
		//style.setBorderTop(top);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		//style.setBorderBottom(bottom);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		//style.setBorderLeft(left);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		//style.setBorderRight(right);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		//style.setVerticalAlignment(verticalAlignment);
		//style.setAlignment(horizontalAlignment);
		return style;
	}

}
