package com.umass.healthos.database;
import java.util.List;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.umass.healthos.objects.DayEnum;
import com.umass.healthos.objects.Result;
import com.umass.healthos.objects.TypeEnum;
public class TestController extends Controller{
	
	private List<Result> results;
	private TypeEnum MEDICATION;
	private TypeEnum PHYSICAL_REPORT;
	private TypeEnum SURVEY;
	
	List<String> taken = new ArrayList<String>();
	
	private List<Result> surveys;
	private List<Result> medications;
	private List<Result> physicalReports;
	
	public void initializeSurveys(){
		surveys = new ArrayList<Result>();
		surveys.add(new Result(1, SURVEY, new LocalDateTime(2014, 4, 12, 10, 23), taken, 1));
		surveys.add(new Result(1, SURVEY, new LocalDateTime(2014, 4, 12, 22, 23), taken, 0));
		surveys.add(new Result(1, SURVEY, new LocalDateTime(2014, 4, 13, 10, 23), taken, 2));
		surveys.add(new Result(1, SURVEY, new LocalDateTime(2014, 4, 13, 22, 23), taken, 3));
		surveys.add(new Result(1, SURVEY, new LocalDateTime(2014, 4, 14, 10, 23), taken, 0));
		surveys.add(new Result(1, SURVEY, new LocalDateTime(2014, 4, 15, 10, 23), taken, 0));
	}
	
	public void initializePhysicalReports(){
		physicalReports = new ArrayList<Result>();
		List<String> bpHigh = new ArrayList<String>();
		List<String> bpLow = new ArrayList<String>();
		bpHigh.add("120");
		bpLow.add("90");
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpHigh, 0));
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 00, 23), bpLow, 0));
		bpHigh.add("119");
		bpLow.add("92");
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 12, 23), bpHigh, 0));
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 9, 12, 23), bpLow, 0));
		bpHigh.add("121");
		bpLow.add("93");
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 10, 00, 23), bpHigh, 0));
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 10, 00, 23), bpLow, 0));
		bpHigh.add("118");
		bpLow.add("89");
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 15, 12, 23), bpHigh, 0));
		physicalReports.add(new Result(1, PHYSICAL_REPORT, new LocalDateTime(2014, 4, 15, 12, 23), bpLow, 0));
	}
	
	public void initializeMedications(){
		medications = new ArrayList<Result>();
		medications.add(new Result(1, MEDICATION, new LocalDateTime(2014, 4, 11, 10, 23), taken, 1));
		medications.add(new Result(2, MEDICATION, new LocalDateTime(2014, 4, 12, 22, 23), taken, 0));
		medications.add(new Result(1, MEDICATION, new LocalDateTime(2014, 4, 13, 10, 23), taken, 2));
		medications.add(new Result(2, MEDICATION, new LocalDateTime(2014, 4, 14, 22, 23), taken, 3));
		medications.add(new Result(1, MEDICATION, new LocalDateTime(2014, 4, 15, 10, 23), taken, 0));
	}
	
	public List<Result> getMedications(){
		return medications;
	}
	
	public List<Result> getReports(){
		return physicalReports;
	}
	
	public List<Result> getSurveys(){
		return surveys;
	}
	
	public void initializeResults(){
		results = new ArrayList<Result>();
		initializeSurveys();
		initializePhysicalReports();
		initializeMedications();
		for (Result r : surveys)
			results.add(r);
		for (Result r : physicalReports)
			results.add(r);
		for (Result r : medications)
			results.add(r);
	}
	
	//Result(int pid, TypeEnum ty, LocalDateTime t, List<String> d, int ts)//
	public TestController(){
		taken.add("taken");
		initializeResults();
	}
	
	public List<Result> getResults(TypeEnum type){
		List<Result> li = new ArrayList<Result>();
		for (Result r : results)
			if (r.getType() == type) li.add(r);
		return li;
	}
	
	public List<Result> getResults(LocalDateTime date_time, TypeEnum type){
		List<Result> li = new ArrayList<Result>();
		for (Result r : results)
			if (r.getTime().isEqual(date_time) || r.getTime().isAfter(date_time) && (r.getType() == type)) 
				li.add(r);
		return li;
	}

	
	public List<Result> getResultsByTimeSpan(LocalDateTime startDate,
			LocalDateTime endDate, TypeEnum type) {
		List<Result> li = new ArrayList<Result>();
		for (Result r : results)
			if( r.getType() == type && 
				(r.getTime().equals(startDate) 
					|| (r.getTime().isAfter(startDate) && r.getTime().isBefore(endDate))) ){
				li.add(r);
			}
		return li;
	}

	public boolean addResults(List<Result> results) {
		for (Result r : results)
			if(!results.add(r)) return false;
		return true;
	}


	public boolean addResult(Result r) {
		return results.add(r);
	}

	public String getCSV() {
		return "C,S,V,h,e,h,e";
	
	}
}

