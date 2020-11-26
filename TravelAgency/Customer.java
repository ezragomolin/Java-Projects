
public class Customer {

	private String name;
	private int balance;
	private Basket reservationCus;

	public Customer(String name, int balance) {
		reservationCus=new Basket();
		this.name = name;
		this.balance = balance;

	}

	public String getName() {
		return this.name;
	}

	public int getBalance() {
		return this.balance;
	}

	public Basket getBasket() {
		return reservationCus;
	}

	public int addFunds(int q) {
		int initBalance = getBalance();
		if (q >= 0) {
			initBalance = initBalance + q;
			this.balance = initBalance;
		} else {
			throw new IllegalArgumentException("An Error Has Occured: cannot add negative money to balance");
		}

		return this.balance;
	}

	public int addToBasket(Reservation g) {
		int numberOfResInBas;
		if (this.getName() == g.reservationName()) {
			reservationCus.add(g);
			numberOfResInBas =reservationCus.getNumOfReservations();
			return numberOfResInBas;
		}

		else {
			throw new IllegalArgumentException("An Error Has Occured: name of reservation does not match name of customer.");
		}
		
	}

	public int addToBasket(Hotel a, String b, int c, boolean d) {
		int x;
		if(d) {
			BnBReservation newRes= new BnBReservation(this.name, a,b,c);
			x= reservationCus.add(newRes);
		}
		
		else {
			HotelReservation newRes= new HotelReservation(this.name,a,b,c);
			x=reservationCus.add(newRes);
		}
		return x;
	}
	

	public int addToBasket(Airport one, Airport two) {
		int w;
		if(one!=two) {
			FlightReservation newReser= new FlightReservation(this.name,one,two);
		    w=reservationCus.add(newReser);
		}
		else {
			w=reservationCus.getNumOfReservations();
		}
		return w;
	}
	

	public boolean removeFromBasket(Reservation e) {
		boolean x=getBasket().remove(e);
		return x;
	}

	public int checkOut() {
		
		if(this.getBalance()>=reservationCus.getTotalCost()) {
			int finalBalance=this.getBalance()-reservationCus.getTotalCost();
			reservationCus.clear();
			return finalBalance;
		}
		
		else {
			throw new IllegalArgumentException("An Error Has Occured: insufficient balance.");
		}
		
	}

}
