import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ejavashop.core.TimeUtil;

public class test {
	
	public static void main(String[] args) {
			test t= new test();
	        Date d =t.lastFirday();
	        System.out.println(TimeUtil.getDateString(d));
	        System.out.println(getPreviousSunday());
//	        getFirstDayOfWeek()
	        	
	}
	
	 public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
		  Calendar cal = Calendar.getInstance();
		  if (date != null)
		   cal.setTime(date);
		  cal.setFirstDayOfWeek(firstDayOfWeek);//设置一星期的第一天是哪一天
		  cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);//指示一个星期中的某天
		  cal.set(Calendar.HOUR_OF_DAY, 0);//指示一天中的小时。HOUR_OF_DAY 用于 24 小时制时钟。例如，在 10:04:15.250 PM 这一时刻，HOUR_OF_DAY 为 22。
		  cal.set(Calendar.MINUTE, 0);//指示一小时中的分钟。例如，在 10:04:15.250 PM 这一时刻，MINUTE 为 4。
		  cal.set(Calendar.SECOND, 0);
		  cal.set(Calendar.MILLISECOND, 0);
		  return cal.getTime();
		 }
	 
	 
	 public Date lastFirday() {
        Calendar calendar = Calendar.getInstance();
        
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(dayOfWeek);
        int offset =7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 9);

        return this.getFirstDayOfWeek(calendar.getTime(),7);//这是从上周日开始数的到本周五为6

    }
	 
	 // 获得当前周- 周日  的日期  
	    public static String getPreviousSunday() {  
	        int mondayPlus = getMondayPlus();  
	        GregorianCalendar currentDate = new GregorianCalendar();  
	        currentDate.add(GregorianCalendar.DATE, mondayPlus +4);  
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
	  
}
