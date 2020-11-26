public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

	public TrainNetwork(int nLines) {
		this.networkLines = new TrainLine[nLines];
	}

	public void addLines(TrainLine[] lines) {
		this.networkLines = lines;
	}

	public TrainLine[] getLines() {
		return this.networkLines;
	}

	public void dance() {
		System.out.println("The tracks are moving!");

		for (int i = 0; i < getLines().length; i++) {
			if (getLines()[i] != null) {
				getLines()[i].shuffleLine();
			}
		}
	}

	public void undance() {

		for (int i = 0; i < getLines().length; i++) {
			if (getLines()[i] != null) {
				getLines()[i].sortLine();
			}
		}
	}

	public int travel(String startStation, String startLine, String endStation, String endLine) {

		TrainLine curLine = null; // use this variable to store the current line.
		TrainStation curStation = null; // use this variable to store the current station.

		int hoursCount = 0;
		System.out.println("Departing from " + startStation);

		// YOUR CODE GOES HERE

		curLine = getLineByName(startLine);
		curStation = curLine.findStation(startStation);
		int dancecounter = 0;
		TrainStation prevStation = null;

		while (curStation.getName() != endStation) {

			if (curStation.getTransferStation() != null) {

				if (prevStation != curStation.getTransferStation()) {
					prevStation = curStation;
					curStation = curStation.getTransferStation();
					curLine = curStation.getLine();

				}

				else {
					prevStation = curStation;
					curStation = curLine.getNext(curStation);

				}

			}

			else {
				prevStation = curStation;
				curStation = curLine.getNext(curStation);

			}

			dancecounter++;
			hoursCount++;

			if (dancecounter == 2) {
				this.dance();
				dancecounter = 0;
			}

			if (hoursCount == 168) {
				System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
				return hoursCount;
			}

			// prints an update on your current location in the network.
			System.out.println("Traveling on line " + curLine.getName() + ":" + curLine.toString());
			System.out.println("Hour " + hoursCount + ". Current station: " + curStation.getName() + " on line "
					+ curLine.getName());
			System.out.println("=============================================");

		}

		System.out.println("Arrived at destination after " + hoursCount + " hours!");

		return hoursCount;

	}

	public TrainLine getLineByName(String lineName) throws LineNotFoundException {

		TrainLine[] linebn = getLines();
		for (int i = 0; i < linebn.length; i++) {
			if (linebn[i].getName().equals(lineName)) {
				return linebn[i];
			}
		}

		throw new LineNotFoundException(lineName);

	}

	// prints a plan of the network for you.
	public void printPlan() {
		System.out.println("CURRENT TRAIN NETWORK PLAN");
		System.out.println("----------------------------");
		for (int i = 0; i < this.networkLines.length; i++) {
			System.out.println(this.networkLines[i].getName() + ":" + this.networkLines[i].toString());
		}
		System.out.println("----------------------------");
	}
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	String name;

	public LineNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "LineNotFoundException[" + name + "]";
	}
}