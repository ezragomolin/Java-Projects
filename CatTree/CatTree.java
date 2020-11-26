import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatTree implements Iterable<CatInfo> {
	public CatNode root;

	public CatTree(CatInfo c) {
		this.root = new CatNode(c);
	}

	private CatTree(CatNode c) {
		this.root = c;
	}

	public void addCat(CatInfo c) {
		this.root = root.addCat(new CatNode(c));
	}

	public void removeCat(CatInfo c) {
		this.root = root.removeCat(c);
	}

	public int mostSenior() {
		return root.mostSenior();
	}

	public int fluffiest() {
		return root.fluffiest();
	}

	public CatInfo fluffiestFromMonth(int month) {
		return root.fluffiestFromMonth(month);
	}

	public int hiredFromMonths(int monthMin, int monthMax) {
		return root.hiredFromMonths(monthMin, monthMax);
	}

	public int[] costPlanning(int nbMonths) {
		return root.costPlanning(nbMonths);
	}

	public Iterator<CatInfo> iterator() {
		return new CatTreeIterator();
	}

	class CatNode {

		CatInfo data;
		CatNode senior;
		CatNode same;
		CatNode junior;

		public CatNode(CatInfo data) {
			this.data = data;
			this.senior = null;
			this.same = null;
			this.junior = null;
		}

		public String toString() {
			String result = this.data.toString() + "\n";
			if (this.senior != null) {
				result += "more senior " + this.data.toString() + " :\n";
				result += this.senior.toString();
			}
			if (this.same != null) {
				result += "same seniority " + this.data.toString() + " :\n";
				result += this.same.toString();
			}
			if (this.junior != null) {
				result += "more junior " + this.data.toString() + " :\n";
				result += this.junior.toString();
			}
			return result;
		}

		private CatNode addCatRecursively(CatNode current, CatInfo info) {
			if (current == null)
				return new CatNode(info);

			if (current.data.monthHired < info.monthHired) {
				// Info more junior than current
				current.junior = addCatRecursively(current.junior, info);
			} else if (current.data.monthHired > info.monthHired) {
				// Info more senior than current
				current.senior = addCatRecursively(current.senior, info);
			} else {
				// Current seniority same as info
				if (current.data.furThickness > info.furThickness) {
					current.same = addCatRecursively(current.same, info);
				} else {
					CatNode temp = current;
					current = new CatNode(info);
					current.same = temp;
					current.junior = current.same.junior;
					current.senior = current.same.senior;
					current.same.junior = null;
					current.same.senior = null;
				}
			}

			return current;
		}

		public CatNode addCat(CatNode c) {
			return addCatRecursively(this, c.data);
		}
		
		private CatNode removeCatRecursively(CatNode current, CatInfo toRemove) {
			if (current == null)
				return null;
			
			if (toRemove.monthHired < current.data.monthHired) {
				// Move left
				current.senior = removeCatRecursively(current.senior, toRemove);
			} else if (toRemove.monthHired > current.data.monthHired) {
				// Move right
				current.junior = removeCatRecursively(current.junior, toRemove);
			} else {
				if (toRemove.furThickness < current.data.furThickness) {
					// Move down
					current.same = removeCatRecursively(current.same, toRemove);
				} else {
					// It's this one
					CatNode newNode = null;
					CatNode oldNode = current;
					
					if (current.same != null) {
						newNode = new CatNode(current.same.data);
						newNode.senior = current.senior;
						newNode.same = current.same.same;
						newNode.junior = current.junior;
					} else if (current.senior != null && current.junior == null) {
						newNode = current.senior;
					} else if (current.junior != null && current.senior == null) {
						newNode = current.junior;
					} else if (current.junior != null && current.senior != null) {
						newNode = new CatNode(current.senior.data);
						newNode.senior = current.senior.senior;
						newNode.same = current.senior.same;
						addDescendantsRecursively(newNode, current.senior.junior);
						addDescendantsRecursively(newNode, current.junior);
					}

					if (newNode == null)
						return null;
					current.data = newNode.data;
					current.senior = newNode.senior;
					current.same = newNode.same;
					current.junior = newNode.junior;
					return oldNode;
				}
			}
			
			return current;
		}


		private void addDescendantsRecursively(CatNode addTo, CatNode parent) {
			if (parent == null) return;
			addTo.addCat(parent);
			addDescendantsRecursively(addTo, parent.senior);
			addDescendantsRecursively(addTo, parent.same);
			addDescendantsRecursively(addTo, parent.junior);
		}

		public CatNode removeCat(CatInfo c) {
			removeCatRecursively(this, c);
			return this;
		}
		
		private CatNode helper_getMostSenior(CatNode current) {
			if (current.senior != null)
				return helper_getMostSenior(current.senior);
			return current;
		}

		public int mostSenior() {
			return helper_getMostSenior(this).data.monthHired;
		}

		private int helper_fluffiest(CatNode current, int max) {
			if (current.senior != null)
				max = Math.max(max, helper_fluffiest(current.senior, max));
			if (current.same != null)
				max = Math.max(max, helper_fluffiest(current.same, max));
			if (current.junior != null)
				max = Math.max(max, helper_fluffiest(current.junior, max));
			return Math.max(max, current.data.furThickness);
		}

		public int fluffiest() {
			return helper_fluffiest(this, this.data.furThickness);
		}

		private int helper_hiredFromMonths(CatNode current, int monthMin, int monthMax) {
			int ret = 0;
			
			if (current.data.monthHired <= monthMax && current.data.monthHired >= monthMin) {
				ret++;
				if (current.same != null)
					ret += helper_hiredFromMonths(current.same, monthMin, monthMax);
			}
			
			if (current.senior != null)
				ret += helper_hiredFromMonths(current.senior, monthMin, monthMax);
			if (current.junior != null)
				ret += helper_hiredFromMonths(current.junior, monthMin, monthMax);
			
			return ret;
		}

		public int hiredFromMonths(int monthMin, int monthMax) {
			if (monthMin > monthMax)
				return 0;
			return helper_hiredFromMonths(this, monthMin, monthMax);
		}

		public CatInfo fluffiestFromMonth(int month) {
			// ADD YOUR CODE HERE
			CatNode current = this;
			while (current.data.monthHired != month) {
				if (current.data.monthHired > month)
					current = current.senior;
				else
					current = current.junior;
			}
			return current.data;
		}

		public int[] costPlanning(int nbMonths) {
			int[] ret = new int[nbMonths];

			for (int i = 0; i < ret.length; i++)
				ret[i] = 0;
			
			for (CatInfo info : new CatTree(this)) {
				int monthIndex = info.nextGroomingAppointment - 243;
				if (monthIndex < ret.length)
					ret[monthIndex] += info.expectedGroomingCost;
			}

			return ret;
		}

	}
	
	private class CatTreeIterator implements Iterator<CatInfo> {
		int i = -1;
		ArrayList<CatInfo> sorted = new ArrayList<CatInfo>();
		
		public CatTreeIterator() {
			traverse(CatTree.this.root);
		}
		
		private void traverse(CatNode current) {
			if (current == null)
				return;
			
			traverse(current.senior);
			traverse(current.same);
			sorted.add(current.data);
			traverse(current.junior);
		}
		
		public CatInfo next() {
			i++;
			return sorted.get(i);
		}
		
		public boolean hasNext() {
			return i < sorted.size() - 1;
		}
	}

}
