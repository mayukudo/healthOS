package com.umass.healthos.database;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.umass.healthos.objects.TypeEnum;
import com.umass.healthos.objects.Result;

import junit.framework.TestCase;

public class SMDTest extends TestCase {
	/********** TESTING ************/
	private static final boolean test = true;
	private static Result res;

	/********** TEST DATA **********/
	private static LocalDateTime localTime;
	private static LocalDateTime startTime;
	private static LocalDateTime endTime;
	private static TypeEnum med;
	private static TypeEnum survey;
	private static TypeEnum report;
	private static TypeEnum type;
	private static List<Result> resultList;
	private static String csv;
	private static TestController testController;
	private static SMD smd;
	List<String> taken = new ArrayList<String>();
	
	private void initialize(){
		localTime = new LocalDateTime(2014, 4, 13, 11, 30);
		startTime = new LocalDateTime(2014, 4, 12, 0, 0);
		endTime = new LocalDateTime(2014, 4, 14, 23, 59);
		med = TypeEnum.MEDICATION;
		testController = new TestController();
		smd = new SMD(testController);
	}
	
	private SMDTest(){
		initialize();
	}
	
	
	// if we make a new LocalDate we need to continuously change our mockdata. Thus I am using endTime instead.
	public void testGetResultsLocalDateTimeTypeEnum() {
		if(test){ 
			resultList = smd.getResultsByTimeSpan(localTime, endTime, med);
			for (Result r: resultList){
				if(!testController.getMedications().contains(r)){
					fail("Wrong");
				}
			}
			resultList = smd.getResultsByTimeSpan(localTime, endTime, report);
			for (Result r: resultList){
				if(!testController.getReports().contains(r)){
					fail("Wrong");
				}
			}
			resultList = smd.getResultsByTimeSpan(localTime, endTime, survey);
			for (Result r: resultList){
				if(!testController.getSurveys().contains(r)){
					fail("Wrong");
				}
			}
		}
	}

	public void testGetResultsTypeEnum() {
		if(test){
			resultList = smd.getResults(med);
			for (Result r: resultList){
				if(!testController.getMedications().contains(r)){
					fail("Wrong");
				}
			}
			resultList = smd.getResults(report);
			for (Result r: resultList){
				if(!testController.getReports().contains(r)){
					fail("Wrong");
				}
			}
			resultList = smd.getResults(survey);
			for (Result r: resultList){
				if(!testController.getSurveys().contains(r)){
					fail("Wrong");
				}
			}
			
		}
	}
	
	public void testGetResultsByTimeSpan() {
		if(test){ 
			resultList = smd.getResultsByTimeSpan(startTime, endTime, med);
			for (Result r: resultList){
				if(!testController.getMedications().contains(r)){
					fail("Wrong");
				}
			}
			resultList = smd.getResultsByTimeSpan(startTime, endTime, report);
			for (Result r: resultList){
				if(!testController.getReports().contains(r)){
					fail("Wrong");
				}
			}
			resultList = smd.getResultsByTimeSpan(startTime, endTime, survey);
			for (Result r: resultList){
				if(!testController.getSurveys().contains(r)){
					fail("Wrong");
				}
			}
		}
	}

	public void testAddResults() {
		if(test){
			List<Result> results = new ArrayList<Result>();
			Result r1 = new Result(1, med, new LocalDateTime(2014, 4, 17, 10, 23), taken, 1);
			Result r2 = new Result(1, med, new LocalDateTime(2014, 4, 18, 22, 23), taken, 1);
			results.add(r1);
			results.add(r2);
			testController.addResults(results);
			resultList = smd.getResults(med);
			if (!resultList.contains(r1) || !resultList.contains(r2)) fail("Wrong");		
		}
	}

	public void testAddResult() {
		if(test){
			// med
			Result m = new Result(1, med, new LocalDateTime(2014, 4, 16, 10, 23), taken, 1);
			testController.addResult(m);
			resultList = smd.getResults(med);
			if (!resultList.contains(m)) fail("Wrong");
			
			// report
			List<String> bpHigh = new ArrayList<String>();
			bpHigh.add("120");
			Result r = new Result(1, report, new LocalDateTime(2014, 4, 9, 00, 23), bpHigh, 0);
			testController.addResult(r);
			resultList = smd.getResults(report);
			if (!resultList.contains(r)) fail("Wrong");
			
			// survey
			Result s = new Result(1, survey, new LocalDateTime(2014, 4, 16, 10, 23), taken, 1);
			testController.addResult(s);
			resultList = smd.getResults(survey);
			if (!resultList.contains(s)) fail("Wrong");
		}
	}

	public void testGetCSV() {
		if(test){
			csv = smd.getCSV();
			if(!testController.getCSV().equals(csv)){
				fail("Wrong");
			}
		}
	}

}
