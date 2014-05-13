package com.umass.healthos.database;

import java.util.ArrayList;
import org.joda.time.LocalDateTime;
import java.util.List;

import com.umass.healthos.objects.TypeEnum;
import com.umass.healthos.objects.Result;

/**
 * SMD should be used to statically retrieve Result based on a time span and/or
 * TypeEnum, as well as to add a Result. SMD should never be instantiated.
 * 
 * @see Result
 */

/*
 * Ted from tenacious turtles; sorry to change code again, but SMD wasn't static and getInstance wasn't either.
 * Is there a way to use these without changing them? Those were the only two lines I changed, let me know if I fucked up.
 * 
 * --Ted
 */
public class SMD {

	private TBDatabase db;
	private static SMD INSTANCE;
	
	private SMD(){
		db = TBDatabase.getInstance();
	}
	
	public static SMD getInstance(){
		if(INSTANCE == null)
			INSTANCE = new SMD();
		return INSTANCE;
	}
	
	//TESTING PURPOSES ONLY
	public SMD(TBDatabase instance){
		this.db = instance;
	}
	
	/**
	 * A convenience method, identical to getResultsByTimeSpan(date_time, new
	 * LocalDateTime(), type). Retrieves all Result that were recorded after a
	 * given LocalDateTime of the given TypeEnum. If null is given for type
	 * param then all Result that were recorded after the LocalDateTime will be
	 * returned, regardless of TypeEnum.
	 * 
	 * @param date_time
	 *            - the LocalDateTime after which Result should be returned
	 * @param type
	 *            - the desired TypeEnum of the returned Result
	 * @return List<Result> of all Result of TypeEnum that were recorded after
	 *         date_time
	 * @throws Exception 
	 */
	public List<Result> getResults(LocalDateTime date_time, TypeEnum type) {
		return getResultsByTimeSpan(date_time, new LocalDateTime(), type);
	}

	/**
	 * Returns all Result of the given TypeEnum. If null is passed, then all
	 * Result will be returned, regardless of TypeEnum.
	 * 
	 * @param type
	 *            - the TypeEnum of Result desired
	 * @return List<Result> of all Result of the given TypeEnum
	 */
	
	public List<Result> getResults(TypeEnum type){
		List<Result> ret = new ArrayList<Result>();
		Result temp;
		if(type == null){
			ret.addAll(getResults(TypeEnum.MEDICATION));
			ret.addAll(getResults(TypeEnum.SURVEY));
			ret.addAll(getResults(TypeEnum.PHYSICAL_REPORT));
		}
		else{
			for (String[] s : db.getResultsByType(type)){
				int resultID = Integer.parseInt(s[0]);
				int parentID = Integer.parseInt(s[1]);
				LocalDateTime dateTime = new LocalDateTime(s[2].replace(" ", "T"));
				buildResult:
				switch(type){
					case MEDICATION:
						int timesSnoozed = Integer.parseInt(s[3]);
						temp = new Result(parentID, type, dateTime, null, timesSnoozed);
						temp.setID(resultID);
						ret.add(temp);
						break buildResult;
					case PHYSICAL_REPORT:
					case SURVEY:
						List<String> d = new ArrayList<String>();
						for (int i = 3; i < s.length; i++){
							d.add(s[i]);
						}
						temp = new Result(parentID, type, dateTime, d, 0);
						temp.setID(resultID);
						ret.add(temp);
						break buildResult;
					}
			}
		}
		return ret;
	}

	/**
	 * Gets all Result that occurred within the time span given by startDate and
	 * endDate of the specified TypeEnum.
	 * 
	 * @param startDate
	 *            - the start of the desired time span
	 * @param endDate
	 *            - the end of the desired time span
	 * @param type
	 *            - the desired TypeEnum of the returned Result
	 * @return List<Result> of all Result matching the input specifications
	 * @throws Exception 
	 */
	public List<Result> getResultsByTimeSpan(LocalDateTime startDate, LocalDateTime endDate, TypeEnum type) {
		List<Result> ret = new ArrayList<Result>();
		if(type == null){
			ret.addAll(getResultsByTimeSpan(startDate, endDate, TypeEnum.MEDICATION));
			ret.addAll(getResultsByTimeSpan(startDate, endDate, TypeEnum.SURVEY));
			ret.addAll(getResultsByTimeSpan(startDate, endDate, TypeEnum.PHYSICAL_REPORT));
		}
		else if(!endDate.isBefore(startDate)){
			for (String[] s : db.getResultsByTimeSpan(startDate, endDate, type)){
				int resultID = Integer.parseInt(s[0]);
				int parentID = Integer.parseInt(s[1]);
				LocalDateTime dateTime = new LocalDateTime(s[2].replace(" ", "T"));
				switch (type){
				case MEDICATION:
						int timesSnoozed = Integer.parseInt(s[3]);
						Result med = new Result(parentID, type, dateTime, null, timesSnoozed);
						med.setID(resultID);
						ret.add(med);
					break;
				case PHYSICAL_REPORT:
				case SURVEY:
					List<String> d = new ArrayList<String>();
					for (int i = 3; i < s.length; i++){
						d.add(s[i]);
					}
					Result res = new Result(parentID, type, dateTime, d, 0);
					res.setID(resultID);
					ret.add(res);
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * Add a List<Result>. A convenience method, equivalent to looping through
	 * the given List and calling addResult() on each Result in the List.
	 * 
	 * @param results
	 *            - the List<Result> to be added to the database
	 * @return boolean success value
	 */
	public boolean addResults(List<Result> results) {
		boolean ret = true;
		for (Result n : results) {
			// If add fails, report it but continue to attempt adding results
			if (!addResult(n))
				ret = false; 
		}
		return ret;
	}

	/**
	 * Adds the given Result to the database.
	 * 
	 * @param r
	 *            - Result to be added
	 * @return boolean success value
	 */
	public boolean addResult(Result r) {
		if(r.getID() > 12 && r.getID() < 16){
			return db.addLog(r);
		}
		return db.addResult(r);
	}

	public String[] getCSVs() {
		return db.getCSVs();
	}
}
