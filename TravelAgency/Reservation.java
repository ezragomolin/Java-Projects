
public abstract class Reservation {

	private String name;

	public Reservation(String name) {
		this.name = name;
	}

	public final String reservationName() {
		return this.name;
	}

	// Add two abstract classes

	public abstract int getCost();

	public abstract boolean equals(java.lang.Object t);

}
