package com.umass.healthos.database.test;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.joda.time.LocalDateTime;
import com.umass.healthos.database.SMD;
import com.umass.healthos.objects.Result;
import com.umass.healthos.objects.TypeEnum;

public class SMDTest extends TestCase {
	
	/********** TESTING ************/
	private static final boolean test = true;

	/********** TEST DATA **********/
	private static LocalDateTime localTime;
	private static LocalDateTime endTime;
	private static List<Result> resultList;
	private static String[] csv;
	private MockDatabase db;
	private static SMD smd;
	List<String> taken = new ArrayList<String>();
	private TypeEnum MEDICATION;
	private TypeEnum PHYSICAL_REPORT;
	private TypeEnum SURVEY; 
	
	public void initializeMedications(){
		db.addResult(new Result(1, MEDICATION, new LocalDateTime(2014, 4, 11, 10, 23), taken, 1));
		db.addResult(new Result(2, MEDICATION, new LocalDateTime(2014, 4, 12, 22, 23), taken, 0));
		db.addResult(new Result(1, MEDICATION, new LocalDateTime(2014, 4, 13, 10, 23), taken, 2));
		db.addResult(new Result(2, MEDICATION, new LocalDateTime(2014, 4, 14, 22, 23), taken, 3));
		db.addResult(new Result(1, MEDICATION, new LocalDateTime(2014, 4, 15, 10, 23), taken, 0));
	}
	
	public void initializePhysicalReports(){
		List<String> bpHigh = new ArrayList<String>();
		List<String> bpLow = new ArrayList<String>();
		bpHigh.add("120");
		bpLow.add("90");
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpHigh, 0));
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpLow, 0));
		bpHigh.add("119");
		bpLow.add("92");
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 12, 23), bpHigh, 0));
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 12, 23), bpLow, 0));
		bpHigh.add("121");
		bpLow.add("93");
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 10, 00, 23), bpHigh, 0));
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 10, 00, 23), bpLow, 0));
		bpHigh.add("118");
		bpLow.add("89");
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 15, 12, 23), bpHigh, 0));
		db.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 15, 12, 23), bpLow, 0));

	}
	
	public void initializeSurveys(){
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 11, 10, 23), taken, 1));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 12, 22, 23), taken, 0));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 13, 10, 23), taken, 2));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 13, 22, 23), taken, 3));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 14, 10, 23), taken, 1));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 15, 22, 23), taken, 0));
	}
	

	public void setUp(){
		localTime = new LocalDateTime(2014, 4, 13, 11, 30);
		new LocalDateTime(2014, 4, 12, 0, 0);
		endTime = new LocalDateTime(2014, 4, 14, 23, 59);
		MEDICATION = TypeEnum.MEDICATION;
		SURVEY = TypeEnum.SURVEY;
		PHYSICAL_REPORT = TypeEnum.PHYSICAL_REPORT;
		db = new MockDatabase();
		smd = new SMD(db);
		initializeMedications();
		initializePhysicalReports();
		initializeSurveys();
		taken.add("taken");
	}
	
	public SMDTest(){
		super();
		//this.setName("SMDTest");
	}


	// if we make a new LocalDate we need to continuously change our mockdata. Thus I am using endTime instead.
	public void testGetResultsLocalDateTimeTypeEnum() {
		clearData();
		TypeEnum m;
		if(test){ 
			resultList = smd.getResultsByTimeSpan(localTime, endTime, MEDICATION);
			for (Result r: resultList){
				m = r.getType();
				if(m != MEDICATION)
					fail("Expected: " + MEDICATION + "returned: " + m);
				if(!(db.contains(r, MEDICATION, localTime, endTime))){
					fail("Database does not contain result #" + r.getID());
				}
			}
			resultList = smd.getResultsByTimeSpan(localTime, endTime, PHYSICAL_REPORT);
			for (Result r: resultList){
				m = r.getType();
				if(m != PHYSICAL_REPORT)
					fail("Expected: " + PHYSICAL_REPORT + "returned: " + m);
				if(!(db.contains(r, PHYSICAL_REPORT, localTime, endTime))){
					fail("Database does not contain result #" + r.getID());
				}
			}
			resultList = smd.getResultsByTimeSpan(localTime, endTime, SURVEY);
			for (Result r: resultList){
				m = r.getType();
				if(m != SURVEY)
					fail("Expected: " + SURVEY + "returned: " + m);
				if(!(db.contains(r, SURVEY, localTime, endTime))){
					fail("Database does not contain result #" + r.getID());
				}
			}
		}
	}


	public void testGetResultsTypeEnum() {
		clearData();
		TypeEnum m;
		if(test){
			resultList = smd.getResults(MEDICATION);
			for (Result r: resultList){
				m = r.getType();
				if(m != MEDICATION)
					fail("Expected: " + MEDICATION + "returned: " + m);
				if(!(db.contains(r, MEDICATION, null, null))){
					fail("Database does not contain result #" + r.getID());
				}
			}
			resultList = smd.getResults(PHYSICAL_REPORT);
			for (Result r: resultList){
				m = r.getType();
				if(m != PHYSICAL_REPORT)
					fail("Expected: " + PHYSICAL_REPORT + "returned: " + m);
				if(!(db.contains(r, PHYSICAL_REPORT, null, null))){
					fail("Database does not contain result #" + r.getID());
				}
			}
			resultList = smd.getResults(SURVEY);
			for (Result r: resultList){
				m = r.getType();
				if(m != SURVEY)
					fail("Expected: " + SURVEY + "returned: " + m);
				if(!(db.contains(r, SURVEY, null, null))){
					fail("Database does not contain result #" + r.getID());
				}
			}
		}
	}
	
	//duplicated test?
//	public void testGetResultsByTimeSpan() {
//		clearData();
//		TypeEnum m;
//		if(test){ 
//			resultList = smd.getResultsByTimeSpan(startTime, endTime, MEDICATION);
//			for (Result r: resultList){
//				m = r.getType();
//				if(m != MEDICATION)
//					fail("Expected: " + MEDICATION + "returned: " + m);
//				if(!(db.getResultsByType(MEDICATION).contains(r))){
//					fail("Database does not contain result #" + r.getID() + " given ("+resultsAsString()+")");
//				}
//			}
//			resultList = smd.getResultsByTimeSpan(startTime, endTime, PHYSICAL_REPORT);
//			for (Result r: resultList){
//				m = r.getType();
//				if(m != MEDICATION)
//					fail("Expected: " + PHYSICAL_REPORT + "returned: " + m);
//				if(!(db.getResultsByType(MEDICATION).contains(r))){
//					fail("Database does not contain result #" + r.getID() + " given ("+resultsAsString()+")");
//				}
//			}
//			resultList = smd.getResultsByTimeSpan(startTime, endTime, SURVEY);
//			for (Result r: resultList){
//				m = r.getType();
//				if(m != MEDICATION)
//					fail("Expected: " + SURVEY + "returned: " + m);
//				if(!(db.getResultsByType(MEDICATION).contains(r))){
//					fail("Database does not contain result #" + r.getID() + " given ("+resultsAsString()+")");
//				}
//			}
//		}
//	}


	public void testAddResults(){
		clearData();
		if(test){
			 Result r1 = new Result(41, MEDICATION, new LocalDateTime(2014, 4, 17, 10, 23), taken, 1);
			 Result r2 = new Result(42, MEDICATION, new LocalDateTime(2014, 4, 18, 22, 23), taken, 1);
			 resultList = new ArrayList<Result>();
			 resultList.add(r1);
			 resultList.add(r2);
			 assertTrue(smd.addResults(resultList));
			 resultList = smd.getResults(MEDICATION);
			 if (!resultList.contains(r1) || !resultList.contains(r2)) 
				 fail("Wrong");
		}
	}

	
	public void testAddResult() {
		clearData();
		if(test){
			Result temp;
			
			// MEDICATION
			
			temp = new Result(42, MEDICATION, new LocalDateTime(2014, 4, 16, 10, 23), null, 1);
			assertTrue(smd.addResult(temp));
			resultList = smd.getResults(MEDICATION);
			assertNotNull(resultList);
			if (!resultList.contains(temp))
				fail("Failed inserting medication result: expected " + temp.getID() + " but got " + resultsAsString());
			
			// PHYSICAL_REPORT
			List<String> bpHigh = new ArrayList<String>();
			bpHigh.add("120");
			temp = new Result(43, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpHigh, 0);
			db.addResult(temp);
			resultList = smd.getResults(PHYSICAL_REPORT);
			if (resultList == null || !resultList.contains(temp)) 
				fail("Failed inserting physical report");
			
			// SURVEY
			temp = new Result(44, SURVEY, new LocalDateTime(2014, 4, 16, 10, 23), null, 1);
			db.addResult(temp);
			resultList = smd.getResults(SURVEY);
			if (resultList == null || !resultList.contains(temp)) 
				fail("Failed inserting survey result");
			
			clearData();
		}
	}

	
	public void testGetCSVs() {
		clearData();
		if(test){
			csv = smd.getCSVs();
			if(!db.getCSVs().equals(csv)){
				fail("Wrong");
			}
		}
	}
	
	private String resultsAsString(){
		String ret = "";
		int length = resultList.size();
		for(int i=0; i<length; i++){
			ret += resultList.get(i).getID() + (i <length-1 ? "," : "");
		}
		return ret;
	}
	
	private void clearData(){
		db.clearData();
		initializeMedications();
		initializePhysicalReports();
		initializeSurveys();
	}

}
