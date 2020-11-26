
public class HotelReservation extends Reservation {

	private Hotel location;
	private String typeOfRoom;
	private int nightsSpent;
	private int pricePerNight;

	public HotelReservation(String name, Hotel location, String typeOfRoom, int nightsSpent) {
		super(name);
		this.location = location;
		this.typeOfRoom = typeOfRoom;
		this.nightsSpent = nightsSpent;
		pricePerNight=this.location.reserveRoom(this.typeOfRoom);
		
		
	}

	public int getNumOfNights() {
		return this.nightsSpent;
	}

	
	public int getCost() {
		return (pricePerNight*getNumOfNights());
	}


	public boolean equals(Object t) {
		if (t instanceof HotelReservation) {
			HotelReservation b = (HotelReservation) t;

			if ((b.reservationName().equals(this.reservationName())) && b.location.equals(this.location)
					&& (b.typeOfRoom.equals(this.typeOfRoom)) && (b.nightsSpent == this.nightsSpent)
					&& (b.getCost() == this.getCost())) {
				return true;
			}

		}

		return false;

	}
}
