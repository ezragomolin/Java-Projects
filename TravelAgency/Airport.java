
public class Airport {

	private int xPosition;
	private int yPosition;
	private int airFees;

	public Airport(int xPosition, int yPosition, int airFees) {

		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.airFees = airFees;

	}

	public int getFees() {
		return this.airFees;
	}

	public static int getDistance(Airport oNe, Airport tWo) {
		int x1 = oNe.xPosition;
		int x2 = tWo.xPosition;
		int y1 = oNe.yPosition;
		int y2 = tWo.yPosition;
		double k=((x2 - x1));
		double p=k*k;
		double s=((y2 - y1));
		double g=s*s;
		double w= Math.sqrt(g+p);
		int distance= (int) Math.ceil(w);
		return distance;
	}
	

}
