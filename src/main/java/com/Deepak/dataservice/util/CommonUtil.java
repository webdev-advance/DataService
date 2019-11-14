package com.Deepak.dataservice.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class CommonUtil {

	public static String getTodaysDateInUIFormat() {
		DateFormat format=new SimpleDateFormat("yyyy-mm-dd");
		String dateStr=null;
		try {
			Calendar cal=Calendar.getInstance();
			dateStr= format.format(cal.getTime());
		}catch(Exception e) {
//			Log.error("Error !");
		}
		return dateStr;
	}

}
