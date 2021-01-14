package com.example.demo.domains;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private String date;
	private String 	nav;
	private Double returns;
	
	public Double getReturns() {
		return returns;
	}
	public void setReturns(String nav) {
		this.returns = Double.parseDouble(nav);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) throws ParseException {
		Date tdate = new SimpleDateFormat("dd-mm-yyyy").parse(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		this.date = dateFormat.format(tdate);
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
		this.setReturns(nav);
	}
	@Override
	public String toString() {
		return "Data [date=" + date + ", nav=" + nav + "]";
	}
	
}
