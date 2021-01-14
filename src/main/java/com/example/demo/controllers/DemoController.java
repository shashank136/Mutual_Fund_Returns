package com.example.demo.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domains.Result;
import com.example.demo.services.MutualFundsReturns;

@RestController
public class DemoController {
	
	private MutualFundsReturns mutualFundsReturns;
	private RestTemplate restTemplate;
	
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	@Autowired
	public void setMutualFundsReturns(MutualFundsReturns mutualFundsReturns) {
		this.mutualFundsReturns = mutualFundsReturns;
	}


	@GetMapping("/app")
	public List<Result> getMFReturns(@RequestParam("scheme") String scheme, @RequestParam("period") Integer period, @RequestParam("horizon") Integer horizon) throws ParseException {
		return mutualFundsReturns.getMFSReturns(restTemplate, scheme, period, horizon);
	}
}
