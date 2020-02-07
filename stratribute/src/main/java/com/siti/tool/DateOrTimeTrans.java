package com.siti.tool;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DateOrTimeTrans {
public DateOrTimeTrans(){
		
	}

	public static final Integer TIME_TYPE_YYYY = 1;
	
	public static final Integer TIME_TYPE_YYYY_MM = 2;
	
	public static final Integer TIME_TYPE_YYYY_MM_DD = 3;

	public static String TimeStamp2Date(Timestamp timestamp){
		long timest = timestamp.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(new Date(timest));
		return dateStr;
	}
	
	public static String Long2Cal(long timest){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date(timest));
		return dateStr;
	}
	
	public static String TimeStamp2Cal(Timestamp timestamp){
		long timest = timestamp.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date(timest));
		return dateStr;
	}
	
	public static String nowTimetoString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
	
	
	public static String TimetoString(Long s){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(s);
		return simpleDateFormat.format(date);
	}
	
	public static Timestamp String2Timestamp(String time){
		return Timestamp.valueOf(time);
	}
	
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date strtodate = null;
		try {
			strtodate = formatter.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strtodate;
	}
	
	public static Date String2Date(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d=null;
		try {
			d=sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	public static String Date2String(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ret=sdf.format(date);
		return ret;
	}
	
	public static String Date2StringLon(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ret=sdf.format(date);
		return ret;
	}
	
	public static String TimeStamp2String(Timestamp timestamp){
		long timest = timestamp.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(new Date(timest));
		return dateStr;
	}
	
	public static String formatDateString(Date date,Integer timeType){
		if(timeType.equals(TIME_TYPE_YYYY)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			return sdf.format(date);
		}else if(timeType.equals(TIME_TYPE_YYYY_MM)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(date);
		}else if(timeType.equals(TIME_TYPE_YYYY_MM_DD)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}
	}
	
	/**
	 * 格式化map中键名为keyName的时间值为timeType类型的时间格式
	 * @param map
	 * @param keyName
	 * @param timeType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void formatMapDateString(Map map, String keyName,Integer timeType){
		Date statisticsDate = (Date)map.get(keyName);
		if(statisticsDate != null){
			String statisticsDateStr = formatDateString(statisticsDate,timeType);
			map.put(keyName, statisticsDateStr);
		}
	}
}
