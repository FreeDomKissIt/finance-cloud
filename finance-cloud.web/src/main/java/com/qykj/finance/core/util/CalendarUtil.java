/**
 *
 */
package com.qykj.finance.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

/**
 *  文件名: CalendarUtils.java <br/>
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class CalendarUtil {
	
	public static final String FISRT_QUARTER = "01";	//第一季度
	public static final String SECOND_QUARTER = "02";	//第二季度	
	public static final String THIRD_QUARTER = "03";	//第三季度
	public static final String FOURTH_QUARTER = "04";	//第四季度
	public static final String DATE_FORMAT_MIN = "yyyy-MM-dd";	//日期年月日的格式化表达式
	public static final String DATE_FORMAT_YEAR_MONTH = "yyyyMM";	//日期年月的格式化表达式
	public static final String DATE_FORMAT_YEAR = "yyyy";	//日期年的格式化表达式
	
	
	
	/**
	 * 根据开始和结束时间计算中间年份
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param pattern		时间格式
	 * @return
	 * @throws ParseException
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static List<String> countYear(String startTime,String endTime,String pattern) throws ParseException{
 
        List<String> result = new ArrayList<String>();      
        DateTime startDate = new DateTime(startTime);
        DateTime endDate = new DateTime(endTime);
        Assert.isTrue(!startDate.isAfter(endDate), "开始和结束时间不正确!");
        do {
        	result.add(startDate.getYear()+"");
        	startDate = startDate.plusYears(1);
        } while (startDate.isBefore(endDate));
        
    	if(!result.contains(endDate.getYear()+"")){      		
    		result.add(endDate.getYear()+"");

        }
        return result;
    }
	
	/**
	 * 根据开始和结束时间计算中间月份
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param pattern		时间格式
	 * @return
	 * @throws ParseException
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static List<String> countMonth(String startTime,String endTime,String pattern) throws ParseException{

        List<String> result = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH);       
        DateTime startDate = new DateTime(startTime);
        DateTime endDate = new DateTime(endTime);
        Assert.isTrue(!startDate.isAfter(endDate), "开始和结束时间不正确!");
        do {
        	result.add(format.format(startDate.toDate()));
        	startDate = startDate.plusMonths(1);//增加一个月
        } while (startDate.isBefore(endDate));
        
        //最后一月特殊处理
    	String month = format.format(endDate.toDate());
    	if(!result.contains(month)){      		
    		result.add(month);
    	}
         
        return result;
    }
	
	
	/**
	 * 根据开始和结束时间计算中间季度
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param pattern		时间格式
	 * @return
	 * @throws ParseException
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static List<String> countQuarter(String startTime,String endTime,String pattern) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        List<String> result = new ArrayList<String>();
        start.setTime(sdf.parse(startTime));
        end.setTime(sdf.parse(endTime));
        Assert.isTrue(!start.after(end), "开始和结束时间不正确!");
        do {
        	String quarter = getQuarter(start);
        	int year = start.get(Calendar.YEAR);
        	if(!result.contains(year+quarter)){
        		result.add(year+quarter);       		
        	}
        	start.add(Calendar.MONTH, 1);
		} while (start.before(end));
        
        //最后一个月特殊处理

    	String quarter = getQuarter(end);
    	int year = end.get(Calendar.YEAR);
    	if(!result.contains(year+quarter)){
    		result.add(year+quarter);       		
    	}
        return result;
    }
	
	
	/**
	 * 根据开始和结束时间计算中间周
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param pattern		时间格式
	 * @return
	 * @throws ParseException
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static List<String> countWeek(String startTime,String endTime,String pattern) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
        List<String> result = new ArrayList<String>();
        DateTime startDate = new DateTime(sdf.parse(startTime));
        DateTime endDate = new DateTime(sdf.parse(endTime));
        String yearWeek = "";        
        do {
        	int week = startDate.getWeekOfWeekyear();
        	int year = startDate.getWeekyear();
        	if(week < 10){
        		yearWeek = year + "0" + week;
        	}else{
        		yearWeek = year + "" + week;
        	}
        	result.add(yearWeek);       		
        	startDate = startDate.plusWeeks(1);
		} while (startDate.isBefore(endDate));
 
        //最后一周特殊处理
    	int week = endDate.getWeekOfWeekyear();
    	int year = endDate.getWeekyear();
    	if(week < 10){
    		yearWeek = year + "0" + week;
    	}else{
    		yearWeek = year + "" + week;
    	}
    	if(!result.contains(yearWeek)){
    		result.add(yearWeek);       		
    	}

        return result;

    }
	
	/**
	 * 根据日期确定季度
	 * @param calendar
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static String getQuarter(Calendar calendar) {  
		  
        String season = "00";  

        int month = calendar.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = FISRT_QUARTER;  			//第一季度
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = SECOND_QUARTER;  			//第二季度
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = THIRD_QUARTER;    			//第三季度
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = FOURTH_QUARTER;    		//第四季度
            break;  
        default:  
            break;  
        }  
        return season;  
    }  
	
}
