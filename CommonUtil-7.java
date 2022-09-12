package com.tatasky.mcr.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import com.tatasky.mcr.constants.FaultReportsName;
import com.tatasky.mcr.constants.McrErrorCodes;
import com.tatasky.mcr.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author vikaschoudhary
 *
 */
@Slf4j
public class CommonUtil {

	public static String formatDate(Date date, String format) throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(date);
	}
		
		public static String getTemplateName(String reportType) throws Exception {
			try {
				return FaultReportsName.valueOf(reportType).getFaultTemplateName();
			} catch (Exception e) {
				log.error("Invalid Report Type");
				throw new ResourceNotFoundException(McrErrorCodes.ERROR_0011);
			}
		}
		
		@SuppressWarnings("deprecation")
		public static Date getDateTime(Date date, String time) {
			if (null == date) {
				return null;
			}
			if (StringUtils.isEmpty(time)) {
				time = "07:00:00";
			}
			String[] times = time.split(":");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR, Integer.valueOf(times[0]));
			c.set(Calendar.MINUTE, Integer.valueOf(times[1]));
			c.set(Calendar.SECOND, Integer.valueOf(times[2]));
			return c.getTime();
		}

		@SuppressWarnings("unused")
		private static String durationOutput(Duration duration) {
			long hours = duration.toHours();
			long minutes = duration.toMinutes() - (hours * 60);
			long seconds = duration.getSeconds() - (hours * 3600) - (minutes * 60);
			System.out.println(timeUnitsOutput(hours) + ":" + timeUnitsOutput(minutes) + ":" + timeUnitsOutput(seconds));
			return timeUnitsOutput(hours) + ":" + timeUnitsOutput(minutes) + ":" + timeUnitsOutput(seconds);
		}

		private static String timeUnitsOutput(long units) {
			return (units < 10) ? ("0" + units) : String.valueOf(units);
		}

		public static String addStringTime(String time1, String time2) {
			int minSum = 0;
			int hourSum = 0;
			int secSum = 0;
			int firstIndex1 = time1.indexOf(":");
			int lastIndex1 = time1.lastIndexOf(":");
			int firstIndex2 = time2.indexOf(":");
			int lastIndex2 = time2.lastIndexOf(":");
			int hour1 = Integer.parseInt(time1.substring(0, firstIndex1));
			int min1 = Integer.parseInt(time1.substring(firstIndex1 + 1, lastIndex1));
			int sec1 = Integer.parseInt(time1.substring(lastIndex1 + 1));
			int hour2 = Integer.parseInt(time2.substring(0, firstIndex2));
			int min2 = Integer.parseInt(time2.substring(firstIndex2 + 1, lastIndex2));
			int sec2 = Integer.parseInt(time2.substring(lastIndex2 + 1));
			secSum = sec1 + sec2;
			if (secSum > 59) {
				minSum += 1;
				secSum %= 60;
			}
			minSum = minSum + min1 + min2;
			if (minSum > 59) {
				hourSum += 1;
				minSum %= 60;
			}
			hourSum = hourSum + hour1 + hour2;
			String time = timeUnitsOutput(hourSum) + ":" + timeUnitsOutput(minSum) + ":" + timeUnitsOutput(secSum);
			return time;
		}

		public static double calculatOutagePercentage(String time, long days) throws ParseException {
			DecimalFormat df = new DecimalFormat("0.000000");
			String formate = df.format(1-(getTotalHours(time)/days));
			return Double.parseDouble(formate);
		}

		public static long noOfDays(Date fromDate, Date toDate) {
			return ChronoUnit.DAYS.between(dateToLocalDate(fromDate), dateToLocalDate(toDate));
		}

		public static LocalDate dateToLocalDate(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		}

		@SuppressWarnings("deprecation")
		public static double getTotalHours(String time) {
			if (StringUtils.isEmpty(time)) {
				return 0;
			}
			String[] times = time.split(":");
			return (Integer.parseInt(times[0]) + Integer.parseInt(times[0]) / 60 + Integer.parseInt(times[0]) / (60 * 60));
		}

		public static LocalDateTime dateToLocalDateTime(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		}
		

		public static Date getYearStartDate(Date fromDate, Boolean isCalendarYear) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			if (isCalendarYear) {
				cal.set(Calendar.MONTH, 0);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				return cal.getTime();
			} else {
				if (cal.get(Calendar.MONTH) < 3) {
					cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
				}
				cal.set(Calendar.MONTH, 3);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				return cal.getTime();
			}
		}
		
		public static String getCurrentUser() {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof UserDetails) {
			  return ((UserDetails)principal).getUsername();
			} else {
			  return principal.toString();
			}
		}

		public static boolean isTwentyAndMoreThanTwenty( String time ) {
			return isMoreThan(time, 20);
		}
		
		@SuppressWarnings("deprecation")
		public static boolean isMoreThan(String time, int moreThanTimeInMin) {
			if(StringUtils.isEmpty(time)) {
				return false;
			}
			boolean flag = false;
			int firstIndex = time.indexOf(":");
			int lastIndex = time.lastIndexOf(":");
			int hour = Integer.parseInt(time.substring(0, firstIndex));
			int min = Integer.parseInt(time.substring(firstIndex + 1, lastIndex)); 
			int sec = Integer.parseInt(time.substring(lastIndex + 1));
			if(moreThanTimeInMin<=(hour*60)) {
				flag = true;
			}else if(moreThanTimeInMin<=(min+ (sec*60))) {
				flag = true;
			}else {
				flag = false;
			}
			return flag;
		}
	
	
//	============================================================================================================================
	
	public static Double MonthAndYearsDuration() throws ParseException {  
		
		int totalNumberOfHours = 40;
		int totalNoOfDays =2;
		double finalMonthResult= totalNumberOfHours/totalNoOfDays;
		return finalMonthResult;
	}
}