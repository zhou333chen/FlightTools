package com.app.flighttools.util;

public class TimeUtils {

	public static String addNumber(String data, String s)
	{
		data = data+s;
		String temp = data.toString();
		if(temp.length() == 4)
		{
			data = temp.substring(0, 2)+":"+temp.substring(2);
		}
		if(temp.length() > 5)
		{
			temp = temp.substring(0, temp.length()-4) + temp.substring(temp.length()-3);
			data = temp.substring(0, temp.length()-2)+":"+temp.substring(temp.length()-2);
		}
		return data;
	}
	
	public static String deleteNumber(String data)
	{
		if(data.length() == 0)
			return data;
		else if(data.length() == 5)
		{
			data = data.substring(0,2) + data.substring(3,4);
		}
		else if(data.length() <4)
		{
			data = data.substring(0, data.length()-1);
		}
		else
		{
			String temp = data.substring(0, data.length()-3) + data.substring(data.length()-2);
			data = temp.substring(0, temp.length()-3)+":"+temp.substring(temp.length()-3, temp.length()-1);
		}
		return data;
	}
	
	public static String add(String s1, String s2)
	{
		if(s1.equals(""))
			s1 = "00:00";
		if(s2.equals(""))
			s2 = "00:00";
		int hour = 0;
		int minute = 0;
		String[] temp1 = s1.split(":");
		hour += Integer.parseInt(temp1[0]);
		minute += Integer.parseInt(temp1[1]);
		temp1 = s2.split(":");
		hour += Integer.parseInt(temp1[0]);
		minute += Integer.parseInt(temp1[1]);
		if(minute >59)
		{
			hour += minute/60;
			minute %= 60;
		}
		if(minute == 0)
			return hour+":00";
		if(minute <10)
			return hour+":0"+minute;
		return hour+":"+minute;
	}
	
	public static String sub(String s1, String s2)
	{
		if(s1.equals(""))
			s1 = "00:00";
		if(s2.equals(""))
			s2 = "00:00";
		int hour = 0;
		int minute = 0;
		String[] temp1 = s1.split(":");
		String[] temp2 = s2.split(":");
		hour = Integer.parseInt(temp1[0]) - Integer.parseInt(temp2[0]);
		minute = Integer.parseInt(temp1[1]) - Integer.parseInt(temp2[1]);
		if(minute < 0)
		{
			hour -= 1;
			minute += 60;
		}
		if(hour < 0)
			return null;
		if(minute == 0)
			return hour+":00";
		if(minute <10)
			return hour+":0"+minute;
		return hour+":"+minute;
	}
	
	public static boolean confirm(String s)
	{
		if(s.equals(""))
			return true;
		try{
			String[] temp = s.split(":");
			int minute = Integer.parseInt(temp[1]);
			if(minute > 59)
				return false;
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
