package com.ejavashop.web.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ejavashop.core.TimeUtil;

public class DateUtil {
	 // 获得当前周- 周日  的日期  
    public static String getPreviousSunday(int countday) {  
        int mondayPlus = getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.add(GregorianCalendar.DATE, mondayPlus +countday);  
        Date monday = currentDate.getTime();  
        DateFormat df = DateFormat.getDateInstance();  
        String preMonday = df.format(monday);  
        return preMonday;  
    }  
    
    
    public static  int getMondayPlus() {  
        Calendar cd = Calendar.getInstance();  
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);  
        if (dayOfWeek == 1) {  
            return -6;  
        } else {  
            return 2 - dayOfWeek;  
        }  
    }  
  
    /**
     * 获取本周五的日期
     * @return
     */
    public static String getFri(){
    	return TimeUtil.getDateString(new Date(DateUtil.getPreviousSunday(4)));
    }
    
    /**
     * 获取本周五的日期
     * @return
     */
    public static String getSat(){
    	return TimeUtil.getDateString(new Date(DateUtil.getPreviousSunday(-2)));
    }
    
    public static void main(String[] args) {

        System.out.println(getSat());
    }
}
