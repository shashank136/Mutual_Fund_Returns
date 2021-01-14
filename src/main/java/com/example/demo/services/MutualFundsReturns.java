package com.example.demo.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domains.Data;
import com.example.demo.domains.Response;
import com.example.demo.domains.Result;

@Service
public class MutualFundsReturns {
	private LinkedHashMap<String, Double> hm= new LinkedHashMap<>();
	private List<Result> responseResult = new ArrayList<>();
	
	public Date getTodaysDate() {
		return new Date();
	}
	
	public Date getPreviousDate(Date date) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24; // milli_seconds*seconds*minutes*hour
		return new Date(date.getTime()-MILLIS_IN_DAY);
	}
	
	public String get_The_First_Date_Aavailble() {
		return hm.keySet().iterator().next();
	}
	
	public Date getDate_NYears_Back(Date date, int n) { // get end date for nav calculation
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR)-n;
		cal.set(year,month,day);
		
//		System.out.println("here is the new date: "+day+" "+(month+1)+" "+year);
		return cal.getTime();
	}
	
	public boolean isLeapYear(int year) {
		return (((year % 4 == 0) && (year % 100!= 0)) || (year%400 == 0));
	}
	
	public Date get_Previous_Month_Date(Date date) { // get previous month date
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)-1;
		int year = cal.get(Calendar.YEAR);
		
		if(month<0) {
			month=11;
			year--;
		}
		
		cal.set(year,month,day);
		
//		System.out.println("here is the new start date: "+day+" "+(month+1)+" "+year);
		return cal.getTime();
	}
	
	public String getFormatedDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		return dateFormat.format(date);
	}
	
	public double getReturns(double end, double start, int period) {
		double a = (double)start/end;
		double p = (double)1/period;
		double exp = (double)Math.pow(a, p);
		double res = (double)(exp-1)*100;
//		System.out.println("res: "+res+", a= "+a+", p= "+p+", exp= "+exp);
		return res;
	}
	
	public Date getDateFromString(String tdate) throws ParseException {
		Date date = new SimpleDateFormat("dd-MMM-yyyy").parse(tdate);
		return date;
	}
	
	public boolean isDateAvailable(Date date) {
		String tdate = getFormatedDate(date);
		return hm.containsKey(tdate);
	}
	
	public double getAvailableNav(String date) throws ParseException {
		double nav=0;
		String cur = date;
		while(!hm.containsKey(cur)) {
			Date present_day = getDateFromString(cur);
			Date previous_day = getPreviousDate(present_day);
			cur = getFormatedDate(previous_day);
		}
		if(cur!=null)
			nav = hm.get(cur);
		return nav;
	}
	
	public List<Result> getMFSReturns(RestTemplate restTemplate, String scheme, Integer period, Integer horizon) throws ParseException {
		responseResult.clear();
		String url = "https://api.mfapi.in/mf/";
		url = url+scheme;
		ResponseEntity<Response> responseEntity = restTemplate.getForEntity(url, Response.class);
		Response response = responseEntity.getBody();
		
		for(Data d: response.getData()) {
			this.hm.put(d.getDate(), d.getReturns());
		}
		
		Date curDate = getTodaysDate();
		String start = getFormatedDate(getTodaysDate());
		String end = getFormatedDate(getDate_NYears_Back(getTodaysDate(), period));
		
		double startNav = getAvailableNav(start);
		double endNav = getAvailableNav(end);
		
		int k = horizon*12;
		
		for(int i=1; i<=k; i++) {			
			
			double result = getReturns(endNav, startNav, period);
			
			/*
			System.out.println("month no.: "+i);
			System.out.println("For month: "+start.substring(3));
			System.out.println("Returns: "+result);
			System.out.println("Start Date: "+start+" startNav: "+startNav);
			System.out.println("End Date: "+end+" endNav: "+endNav);
			System.out.println();
			*/
			
			responseResult.add(new Result(start.substring(3), result, start, end));
			
			// get previous month start dates
			Date previous_month_same_start_date = get_Previous_Month_Date(curDate);
			start = getFormatedDate(previous_month_same_start_date);
			startNav = getAvailableNav(start);
			
			// get end date
			end = getFormatedDate(getDate_NYears_Back(previous_month_same_start_date, period));
			endNav = getAvailableNav(end);
			
			// update the curDate
			curDate = get_Previous_Month_Date(curDate);
		}		
		System.out.println("Completed...\n");
		
		return responseResult;
	}
}
