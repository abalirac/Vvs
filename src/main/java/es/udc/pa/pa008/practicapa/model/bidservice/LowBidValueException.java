package es.udc.pa.pa008.practicapa.model.bidservice;


@SuppressWarnings("serial")
public class LowBidValueException extends IllegalArgumentException{
	
	private double bidValue;

	public LowBidValueException(double bidValue){
		super("Bid value is too low => " + bidValue);
	}
	
	public double getBidValue(){
		return bidValue;
	}
	
}
