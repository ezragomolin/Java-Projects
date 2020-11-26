
public class Room {

	private String roomType;
	private int priceOfroom;
	private boolean isAvailable;

	public Room(String a) {

		if (a.equalsIgnoreCase("double")) {
			priceOfroom = (90 * 100);
			this.roomType="double";
			isAvailable = true;
		}

		else if (a.equalsIgnoreCase("queen")) {
			priceOfroom = (110 * 100);
			this.roomType="queen";
			isAvailable = true;
		}

		else if (a.equalsIgnoreCase("king")) {
			priceOfroom = (150 * 100);
			this.roomType="king";
			isAvailable = true;
		}

		else {
			throw new IllegalArgumentException("An Error Has Occured: No room of such type");
		}

	}

	public Room(Room a) {

		this.roomType =a.roomType;
		this.priceOfroom = a.priceOfroom;
		this.isAvailable = a.isAvailable;

	}

	public String getType() {

		return this.roomType;
	}

	public int getPrice() {

		return this.priceOfroom;
	}

	public void changeAvailability() {
		
		isAvailable = !isAvailable;

	}

	public static Room findAvailableRoom(Room[] a, String b) {
		if(a.length==0) {
			return null;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i].isAvailable && a[i].getType().equalsIgnoreCase(b))
				return a[i];
		}
	
		return null; 
		

	}

	public static boolean makeRoomAvailable(Room[] f, String g) {
		
		for (int i = 0; i < f.length; i++) {
			if (!f[i].isAvailable && f[i].getType().equalsIgnoreCase(g)) { //not operates on last statement thing (isavailable)
				f[i].isAvailable=true;
				return true;}
		}
		return false;

	}
}
