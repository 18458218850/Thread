package com.zuokai.spring0515;

import java.util.Date;

public class Logger {
	
	public enum Leval{
		info,debug;
	}

	/**
	 * 
	 * @param leval 日志等级
	 * @param context 日志内容
	 */
	public static void logger(Leval leval,String context){
		
		if("info".equals(leval)){
			System.out.println(new Date() + "info logger start " + context);
		}else{
			System.out.println(new Date() + "debug logger start " + context);
		}
	}
}
