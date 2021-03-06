import java.util.Arrays;
import java.util.Random;

public class TrainLine {
	

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		int counter = 0;
		TrainStation node = leftTerminus;
		while (node != null) {
			counter++;
			node = node.getRight();
		}
		return counter; // return 0; change this!
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot
	// make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) throws StationNotFoundException {

		if (this.equals(current.getLine())) {

			if (current.hasConnection && previous != current.getTransferStation()) {
				return current.getTransferStation();
			} 
			
			else {
			
				return getNext(current);
			}
		}
		throw new StationNotFoundException(lineName);
	}

	// You can modify the header to this method to handle an exception. You cannot
	// make any other change to the header.
	public TrainStation getNext(TrainStation station)  throws StationNotFoundException{

		if (this.equals(station.getLine())) {

			if (goingRight) {
				if (station.equals(rightTerminus)) {
					this.reverseDirection();
					return station.getLeft();
				}
				return station.getRight();
			} else {
				if (station.equals(leftTerminus)) {
					this.reverseDirection();
					return station.getRight();
				}
				return station.getLeft();
			}
		}
		throw new StationNotFoundException(lineName);
	}

	// You can modify the header to this method to handle an exception. You cannot
	// make any other change to the header.
	public TrainStation findStation(String name) throws StationNotFoundException {

		// YOUR CODE GOES HERE
		TrainStation temp = leftTerminus;

		while (temp != null) {
			if (temp.getName().equals(name)) {
				return temp;
			} else {
				temp = temp.getRight();
			}
		}
		throw new StationNotFoundException(lineName);
	}
	

	public void sortLine() {
		
		for(TrainStation node=leftTerminus ; !node.isRightTerminal(); node=node.getRight()) {
			
				for(TrainStation temp=node.getRight(); !temp.isRightTerminal(); temp=temp.getRight()) {
					
					if(node.getName().compareTo(temp.getName())>0) {
						

					//This performs the swapping
						
						if(node.isLeftTerminal()) {
							node.setRight(temp.getRight());
							temp.setLeft(null);
							temp.setLeftTerminal();
							node.setNonTerminal();
							
						}
						
						else if (temp.isRightTerminal()) {
							node.setRight(null);
							node.setRightTerminal();
							temp.setLeft(node.getLeft());
							temp.setNonTerminal();
							
						}
						
						//if neither node or temp are terminals
						
						else {
							node.setRight(temp.getRight());
							temp.getRight().setLeft(node);
							temp.setLeft(node.getLeft());
							node.getLeft().setRight(temp);
						}
						
					// this is always the case regardless of terminals or not
					node.setLeft(temp);
					temp.setRight(node);
						
					}
				}	
			}
	
		this.lineMap=this.getLineArray();
	}

		
	
	

	public TrainStation[] getLineArray() {
		TrainStation pointer = leftTerminus;
		int counter2 = this.getSize();

		TrainStation[] linestat = new TrainStation[counter2];
		for (int i = 0; i < counter2; i++) {
			linestat[i] = pointer;
			pointer = pointer.getRight();
		}
		return linestat;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		// YOUR CODE GOES HERE
		leftTerminus = shuffledArray[0];
		TrainStation pointer3 = leftTerminus;
		leftTerminus.setLeftTerminal();
		pointer3.setLeft(null);
		pointer3.setRight(shuffledArray[1]);
		pointer3 = pointer3.getRight();
		for (int i = 1; i < shuffledArray.length - 1; i++) {
			pointer3 = shuffledArray[i];
			TrainStation stat=pointer3.getRight();
			pointer3.setRight(shuffledArray[i + 1]);
			pointer3.setLeft(shuffledArray[i - 1]);
			pointer3.setNonTerminal();
			pointer3 = stat;
		}
		rightTerminus = shuffledArray[shuffledArray.length - 1];
		rightTerminus.setRightTerminal();
		rightTerminus.setRight(null);
		rightTerminus.setLeft(shuffledArray[shuffledArray.length - 2]);

	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}



//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
