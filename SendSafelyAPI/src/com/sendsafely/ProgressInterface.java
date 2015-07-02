package com.sendsafely;

/**
 * An interface, which can be implemented to track the progress of the file currently being uploaded. 
 * @author Erik Larsson
 *
 */
public interface ProgressInterface {

	/**
	 * @description Implement this function in order to get progress information which can be used to update the user on the progress of the current file uploads. Will return a double between 0 and 1.
	 * @param progress
	 */
	public void updateProgress(double progress);
	
}
