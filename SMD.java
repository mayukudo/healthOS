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
public class SMD {

	private Controller instance;
	
	private SMD() {
		instance = Controller.getInstance();
	}
	
	
	//TEST
	SMD(Controller instance){
		this.instance = instance;
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
		return instance.getResultsByType(type);
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
	public List<Result> getResultsByTimeSpan(LocalDateTime startDate,
			LocalDateTime endDate, TypeEnum type) {
		if (endDate.isBefore(startDate)){
			return null;
		}
		return instance.getResultsByTimeSpan(startDate, endDate,
				type);
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
			if (!instance.addResult(n)) // if add fails, report it but continue to
									// attempt adding results
				ret = false; // maybe throw exception if already failed once &
								// fails again?
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
		return instance.addResult(r);
	}

	public String getCSV() {
		return instance.getCSV();
	}
}
