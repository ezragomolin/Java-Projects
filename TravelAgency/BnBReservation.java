
public class BnBReservation extends HotelReservation {
	
	
	
	
	public BnBReservation(String name, Hotel location, String typeOfRoom, int nightsSpent) {
		super(name, location, typeOfRoom, nightsSpent);
		

	}

	public int getCost() {
		
        return super.getCost()+ 1000*getNumOfNights();

	}
}