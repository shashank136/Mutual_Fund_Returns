package com.example.demo.domains;

public class Result {
	
	private String months;
	private double returns;
	private String startDate;
	private String endDate;
		
	public Result(String months, double returns, String startDate, String endDate) {
		super();
		this.months = months;
		this.returns = returns;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public double getReturns() {
		return returns;
	}
	public void setReturns(double returns) {
		this.returns = returns;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	

}
