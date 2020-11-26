
public class FlightReservation extends Reservation {

	private Airport departure;
	private Airport arrival;

	public FlightReservation(String name, Airport departure, Airport arrival) {

		super(name);
		if (departure.equals(arrival)) {
			throw new IllegalArgumentException("An Error Has Occured:departure airport is the same as arrival airport");
		}
		this.departure = departure;
		this.arrival = arrival;

	}

	@Override
	public int getCost() {
		double costOfRes = Airport.getDistance(departure, arrival) * (1 / (167.52)) * ((1.24 * 100)) + (53.75 * 100)
				+ (departure.getFees()) + (arrival.getFees());

		return (int) Math.ceil(costOfRes);
	}

	@Override
	public boolean equals(Object t) {
		if (t instanceof FlightReservation) {
			FlightReservation b = (FlightReservation) t;

			if ((b.reservationName().equals(this.reservationName())) && b.arrival.equals(this.arrival)
					&& (b.departure.equals(this.departure))) {
				return true;
			}

		}

		return false;

	}
}
