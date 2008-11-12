package edu.cornell.dendro.corina.io;

public interface MeasurementReceiver {
	public void receiverUpdateStatus(String status); 
	public void receiverNewMeasurement(Integer value); // a new value
}
