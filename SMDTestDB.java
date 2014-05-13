package com.umass.healthos.database.test;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.joda.time.LocalDateTime;
import com.umass.healthos.database.SMD;
import com.umass.healthos.database.TBDatabase;
import com.umass.healthos.objects.PhysicalReportEnum;
import com.umass.healthos.objects.Result;
import com.umass.healthos.objects.SurveyEnum;
import com.umass.healthos.objects.TypeEnum;

public class SMDTestDB extends TestCase {
	
	/********** TESTING ************/
	private static final boolean test = true;

	/********** TEST DATA **********/
	private static LocalDateTime localTime;
	private static LocalDateTime endTime;
	private static List<Result> resultList;
	private TBDatabase db;
	private static SMD smd;
	List<String> taken = new ArrayList<String>();
	private TypeEnum MEDICATION;
	private TypeEnum PHYSICAL_REPORT;
	private TypeEnum SURVEY;
	Result medResult;
	Result physRepResult;
	Result surveyResult;
	String newLine;
	
	public void initializeMEDICATIONications(){
		smd.addResult(new Result(312, MEDICATION, new LocalDateTime(2014, 4, 11, 10, 23), taken, 1));
		smd.addResult(new Result(32, MEDICATION, new LocalDateTime(2014, 4, 12, 22, 23), taken, 0));
		smd.addResult(new Result(432, MEDICATION, new LocalDateTime(2014, 4, 13, 10, 23), taken, 2));
		smd.addResult(new Result(534, MEDICATION, new LocalDateTime(2014, 4, 14, 22, 23), taken, 3));
		smd.addResult(new Result(234, MEDICATION, new LocalDateTime(2014, 4, 15, 10, 23), taken, 0));
	}
	
	public void initializePhysicalReports(){
		List<String> bpHigh = new ArrayList<String>();
		List<String> bpLow = new ArrayList<String>();
		bpHigh.add("120");
		bpLow.add("90");
	//	smd.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpHigh, 0));
	// smd.addResult(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpLow, 0));
		bpHigh.add("119");
		bpLow.add("92");
		smd.addResult(new Result(PhysicalReportEnum.BLOOD_PRESSURE.getId(), PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 12, 23), bpHigh, 0));
		smd.addResult(new Result(PhysicalReportEnum.BLOOD_PRESSURE.getId(), PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 12, 23), bpLow, 0));
	//	bpHigh.add("121");
	//	bpLow.add("93");
	//	smd.addResult(new Result(44, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 10, 00, 23), bpHigh, 0));
	//	smd.addResult(new Result(45, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 10, 00, 23), bpLow, 0));
	//	bpHigh.add("118");
	//	bpLow.add("89");
	//	smd.addResult(new Result(46, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 15, 12, 23), bpHigh, 0));
	//	smd.addResult(new Result(47, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 15, 12, 23), bpLow, 0));

	}
	
	public void initializeSurveys(){
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 12, 10, 23), taken, 1));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 12, 22, 23), taken, 0));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 13, 10, 23), taken, 2));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 13, 22, 23), taken, 3));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 14, 10, 23), taken, 1));
		db.addResult(new Result(1, SURVEY, new LocalDateTime(2014, 4, 15, 10, 23), taken, 0));
	}
	

	public void setUp(){
		localTime = new LocalDateTime(2014, 4, 13, 11, 30);
		new LocalDateTime(2014, 4, 12, 0, 0);
		endTime = new LocalDateTime(2014, 4, 14, 23, 59);
		MEDICATION = TypeEnum.MEDICATION;
		SURVEY = TypeEnum.SURVEY;
		PHYSICAL_REPORT = TypeEnum.PHYSICAL_REPORT;
		db = TBDatabase.getInstance();
		smd = new SMD(db);
		//initializeMEDICATIONications();
		//initializePhysicalReports();
		//initializeSurveys();
		for(int i = 0; i < 11;i++) 
			taken.add("No Answer");
		newLine = System.getProperty("line.separator");
		List<String> bp = new ArrayList<String>();
		bp.add("120");
		bp.add("60");
		medResult = new Result(13, MEDICATION, new LocalDateTime(2014, 4, 16, 10, 23), taken, 1);
		physRepResult = new Result(PhysicalReportEnum.BLOOD_PRESSURE.getId(), PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bp, 0);
		surveyResult = new Result(SurveyEnum.LSN.getId(), SURVEY, new LocalDateTime(2014, 4, 16, 10, 23), taken, 1);
	}
	
	public SMDTestDB(){
		super();
		//this.setName("SMDTestDB");
	}


	// if we make a new LocalDate we need to continuously change our mockdata. Thus I am using endTime instead.
	public void testGetResultsLocalDateTimeTypeEnum() {
		if(test){ 
			resultList = smd.getResultsByTimeSpan(localTime, endTime, MEDICATION);
			for (Result r: resultList){
				TypeEnum m = r.getType();
				if(!(smd.getResultsByTimeSpan(localTime, endTime, MEDICATION).contains(r)) || !(m.equals(MEDICATION))){
					fail("Wrong");
				}
			}
			resultList = smd.getResultsByTimeSpan(localTime, endTime, PHYSICAL_REPORT);
			for (Result r: resultList){
				TypeEnum res = r.getType();
				if(!(resultList.contains(r)) || !(res.equals(PHYSICAL_REPORT))){
					fail("Wrong");
				}
			}
			resultList = smd.getResultsByTimeSpan(localTime, endTime, SURVEY);
			for (Result r: resultList){
				TypeEnum s = r.getType();
				if((!resultList.contains(r)) || !(s.equals(SURVEY))){
					fail("Wrong");
				}
			}
		}
		db.clearData();
	}


	public void testGetResultsTypeEnum() {
		db.clearData();
		db.addResult(medResult);
		db.addResult(physRepResult);
		db.addResult(surveyResult);
		if(test){
			resultList = smd.getResults(MEDICATION);
			assertEquals(1, resultList.size());
			
			assertEquals(medResult, resultList.get(0));
			resultList = smd.getResults(PHYSICAL_REPORT);
			assertEquals(1, resultList.size());
			assertEquals(physRepResult, resultList.get(0));
			resultList = smd.getResults(SURVEY);
			assertEquals(1, resultList.size());
			assertEquals(surveyResult, resultList.get(0));
			
		}
	}
	
	//Identical test to 
//	public void testGetResultsByTimeSpan() {
//		if(test){ 
//			resultList = smd.getResultsByTimeSpan(startTime, endTime, MEDICATION);
//			for (Result r: resultList){
//				TypeEnum m = r.getType();
//				if(!(smd.getResults(MEDICATION).contains(r)) || !(m.equals(MEDICATION))){
//					fail("Wrong");
//				}
//			}
//			resultList = smd.getResultsByTimeSpan(startTime, endTime, PHYSICAL_REPORT);
//			for (Result r: resultList){
//				TypeEnum res = r.getType();
//				if(!(smd.getResults(PHYSICAL_REPORT).contains(r)) || !(res.equals(PHYSICAL_REPORT))){
//					fail("Wrong");
//				}
//			}
//			resultList = smd.getResultsByTimeSpan(startTime, endTime, SURVEY);
//			for (Result r: resultList){
//				TypeEnum s = r.getType();
//				if((!smd.getResults(SURVEY).contains(r)) || !(s.equals(SURVEY))){
//					fail("Wrong");
//				}
//			}
//		}
//	}


	public void testAddResults(){
		db.clearData();
		if(test){
			 Result r1 = new Result(13, MEDICATION, new LocalDateTime(2014, 4, 17, 10, 23), taken, 1);
			 Result r2 = new Result(14, MEDICATION, new LocalDateTime(2014, 4, 18, 22, 23), taken, 1);
			 assertTrue(smd.addResult(r1));
			 assertTrue(smd.addResult(r2));
			 resultList = smd.getResults(MEDICATION);
			 String list = "( ";
			 for(Result r: resultList)
				 list += r.getID() + " ";
			 list += ")";
			 assertTrue(r1.getID() + " not in " + list, resultList.contains(r1));
			 assertTrue(r2.getID() + " not in " + list, resultList.contains(r2));
		}
		db.clearData();
	}

	
	public void testAddResult() {
		db.clearData();
		if(test){
			Result temp;
			
			// MEDICATION
			
			temp = new Result(13, MEDICATION, new LocalDateTime(2014, 4, 16, 10, 23), taken, 1);
			smd.addResult(temp);
			resultList = smd.getResults(MEDICATION);
			assertNotNull(resultList);
			if (!resultList.contains(temp))
				fail("Failed inserting medication result");
			
			// PHYSICAL_REPORT
			List<String> bpHigh = new ArrayList<String>();
			bpHigh.add("120");
			bpHigh.add("80");
			temp = new Result(PhysicalReportEnum.BLOOD_PRESSURE.getId(), PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpHigh, 0);
			smd.addResult(temp);
			resultList = smd.getResults(PHYSICAL_REPORT);
			assertNotNull(resultList);
			String list = "(  ";
			for(Result r: resultList)
				list += r.getID() + "|" + r.getParentID() + "  ";
			list += ")";
			if(!resultList.contains(temp)) 
				fail(temp.getID() + "|" + temp.getParentID() + " is not in " + list);
			
			// SURVEY
			temp = new Result(SurveyEnum.LSN.getId(), SURVEY, new LocalDateTime(2014, 4, 16, 10, 23), taken, 0);
			smd.addResult(temp);
			resultList = smd.getResults(SURVEY);
			assertNotNull(resultList);
			assert(0 != resultList.size());
			list = "(  ";
			for(Result r: resultList)
				list += r.getID() + "|" + r.getParentID() + "  ";
			list += ")";
			if(!resultList.contains(temp)) 
				fail(temp.getID() + "|" + temp.getParentID() + " is not in " + list);
		}
		db.clearData();
	}

	
	public void testGetCSVs() {
		db.clearData();
		if(test){	
			//build the expected csv string
			List<String> csvs = new ArrayList<String>();
			String medRes = "Name, Date, Time" + newLine;
			String BarthelRes = "Date, Time, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15"
			+ newLine;
			String IADLSRes = "Date, Time, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8"
			+ newLine;
			String SoCRes = "Date, Time, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15"
			+ newLine;
			String JADSRes = "Date, Time, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17, Q18"
			+ newLine;
			String LSNRes = "Date, Time, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11"
			+ newLine;
			String AttitudeRes = "Date, Time, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14"
			+ newLine;
			String BPRes = "Date, Time, Systolic, Diastolic" + newLine;
			String PulseRes = "Date, Time, Result" + newLine;
			String WeightRes = "Date, Time, Result" + newLine;
			String MovementRes = "Date, Time, Result" + newLine;
			String O2Res = "Date, Time, Result" + newLine;
			String BSRes = "Date, Time, Result" + newLine;
			csvs.add(medRes+buildCSV(medResult)+newLine);
			csvs.add(BarthelRes);
			csvs.add(IADLSRes);
			csvs.add(SoCRes);
			csvs.add(JADSRes);
			csvs.add(LSNRes+buildCSV(surveyResult)+newLine);
			csvs.add(AttitudeRes);
			csvs.add(BPRes+buildCSV(physRepResult)+newLine);
			csvs.add(PulseRes);
			csvs.add(WeightRes);
			csvs.add(MovementRes);
			csvs.add(O2Res);
			csvs.add(BSRes);
			
			//insert the data and get the CSVs
			db.addResult(medResult);
			db.addResult(physRepResult);
			db.addResult(surveyResult);
			String[] results = db.getCSVs();
			String temp = "";
			for(String s: csvs)
				temp += s;
			for(String s: results){
				if(!csvs.contains(s)){
					fail("Could not find: " + s + " IN " + newLine + newLine + temp);
				}
			}
			//assertArrayEquals(csvs.toArray(new String[] {}), db.getCSVs());
		}
	}
	
	private String buildCSV(Result r){
		boolean med = r.getType() == TypeEnum.MEDICATION;
		String ret = (med ? "," : "");
		ret += r.getTime().toString(TBDatabase.SQL_DATE_PATTERN)+",";
		ret += r.getTime().toString(TBDatabase.SQL_TIME_PATTERN)+(med ? "" : ",");
		if(!med){
			List<String> res = r.getResults();
			for(int i=0; i<res.size(); i++)
				ret+=res.get(i) + (i < res.size()-1 ? "," : "");
		}
		return ret;
	}

}
