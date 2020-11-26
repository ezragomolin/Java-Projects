
public class Basket {

	private Reservation[] reservations;
	private int size;
	
	public Basket() {
		this.size=0;
		reservations=new Reservation[15];
	}

	public Reservation[] getProducts() {
		Reservation[] shallowCopy=new Reservation[size];
		for(int i=0;i<shallowCopy.length;i++) {
		shallowCopy[i]=reservations[i];
		}
		return shallowCopy;
	}
	

	public int add(Reservation p) {
		if (this.reservations.length==size) {
			//This increases the size of the array
			Reservation[] reservations2=new Reservation[reservations.length*2];
			for(int i=0;i<this.reservations.length;i++) {
				reservations2[i]=reservations[i];
				
			}
			reservations=reservations2;
			}
			
			reservations[size]=p;
			this.size++;
		
		return this.size;
	}
	
	

	public boolean remove(Reservation s) {
		for (int i = 0; i < this.size; i++) {
			if (reservations[i].equals(s)) {                //cast to object
				reservations[i] = null;

				for (int p = i; p < (this.size - 1); p++) {

					reservations[p] = reservations[p+1];
				}
				size--;
				return true;
			}

		}

		return false;
	}

	public void clear() {
		for (int i = 0; i < this.size; i++) {
			reservations[i] = null;
		}
		size=0;

	}

	public int getNumOfReservations() {
		return this.size;
	}
	

	public int getTotalCost() {
		int cost = 0;
		for (int i = 0; i < this.size; i++) {
			if (reservations[i] != null) {                        //will never be in as the array up until size does not contain empty slots
				int resCost = reservations[i].getCost();
				cost = cost + resCost;
			}
		}

		return cost;
	}

}
