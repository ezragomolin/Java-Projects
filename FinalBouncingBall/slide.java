import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import acm.gui.DoubleField;
import acm.gui.IntField;
import acm.gui.TableLayout;

/**
 * @author ezragomolin
 *This public class is responsible for actually creating the inTFIelds and doubleFields to allow the user to choose values
 *it is responsible for the whole layout of the  right JPannel
 *the slide class is called upon above
 */
	public class slide extends JPanel {

		JLabel mini;
		JLabel maxi;
		JLabel definedVal;
		IntField intval;
		DoubleField doubleval;

		int min;
		int max;
		int defined;
		String namelabel;

		// constructor for  with name, min, max,----- int values
		public slide(String namelabel, int defined, int min, int max) {

			this.setLayout(new TableLayout(1, 5));

			JLabel minla = new JLabel(namelabel); //label saying what intfield represents
			this.add(minla, "width = 80");

			mini = new JLabel(min + ""); //label that tells you min
			this.add(mini, "width = 20");

			intval = new IntField(defined, min, max);   //creates intfield
			this.add(intval, "width = 50");
			intval.setForeground(Color.blue);

			maxi = new JLabel(max + ""); //label that tells you max
			this.add(maxi, "width = 40");

		}

		// constructor for objects with name, min, max,---- double values
		
		public slide(String namelabel, double defined, double min, double max) {

			this.setLayout(new TableLayout(1, 5));

			JLabel minla = new JLabel(namelabel);
			this.add(minla, "width = 80");

			mini = new JLabel(min + "");
			this.add(mini, "width = 20");

			doubleval = new DoubleField(defined, min, max);
			this.add(doubleval, "width = 50");
			doubleval.setForeground(Color.blue);

			maxi = new JLabel(max + "");
			this.add(maxi, "width = 40");

			definedVal = new JLabel(defined + "");
			definedVal.setForeground(Color.blue);

		}

	}
	

