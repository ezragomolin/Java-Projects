
public class Hotel {

	private String hotelName;
	private Room[] roomsInHotel;

//Deep Copy --> Elementwise identical	
	public Hotel(String s, Room[] a) {

		this.hotelName = s;
		this.roomsInHotel = new Room[a.length];
		
		for (int i = 0; i < a.length; i++) {
			roomsInHotel[i] = new Room(a[i]); // creating new room thats identical copy
		}
	}

//t: type of room to reserve
	public int reserveRoom(String t) {

		Room s = Room.findAvailableRoom(roomsInHotel, t);
		if (s == null) {
			throw new IllegalArgumentException("An Error Has Occured: No room of this type available");
		} else {
			s.changeAvailability();
			return s.getPrice();
		}
		
	}
	


	public boolean cancelRoom(String t) {
		return Room.makeRoomAvailable(roomsInHotel, t); 
	}

		
	

	

}
