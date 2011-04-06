// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


/**
 * This event set is all "words" (NaiveWordEventDriver) with M <= length <= N (M
 * and N being parameters "M" and "N" respectively)
 * 
 * @since 4.1
 **/
public class MNLetterWordEventDriver extends EventDriver {

	JLabel MLabel = new JLabel();
        JComboBox MBox = new JComboBox();
        JLabel NLabel = new JLabel();
        JComboBox NBox = new JComboBox();

        @Override
	public String displayName() {
		return "M--N letter words";
	}

	@Override
	public String tooltipText() {
		return "Words with between M and N letters";
	}

	@Override
	public String longDescription() {
		return "Words with between M and N letters (M and N are given as parameters)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

        @Override
        public GroupLayout getGUILayout(JPanel panel){

            MLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            MLabel.setText("M");

            MBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
            String temp = this.getParameter("M");
            if (temp.equals(""))
            {
                this.setParameter("M", 2);
            }
            MBox.setSelectedIndex(Integer.parseInt(this.getParameter("M")) - 1);
            MBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    MBoxActionPerformed(evt);
                }
            });
            
            NLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            NLabel.setText("N");

            NBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
            temp = this.getParameter("N");
            if (temp.equals(""))
            {
                this.setParameter("N", 3);
            }
            NBox.setSelectedIndex(Integer.parseInt(this.getParameter("N")) - 1);
            NBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    NBoxActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);

            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MLabel)
                        .addComponent(MBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NLabel)
                        .addComponent(NBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(423, Short.MAX_VALUE))
            );

            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(MLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(MBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(NLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(NBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(366, Short.MAX_VALUE))
            );
            return layout;
        }

        private void NBoxActionPerformed(java.awt.event.ActionEvent evt) {
            this.setParameter("N", NBox.getSelectedIndex()+1);
        }

        private void MBoxActionPerformed(java.awt.event.ActionEvent evt) {
            this.setParameter("M", MBox.getSelectedIndex()+1);
        }

	/** Underlying EventDriver from which Events are drawn. */
	public EventDriver underlyingevents = new NaiveWordEventDriver();

	/** Limits on characters per word */
	public int M = 2;
	public int N = 3;

	@Override
	public EventSet createEventSet(Document ds) {

		// Extract local field values based on parameter settings
		String param;

		// lots of error checking
		if (!(param = (getParameter("N"))).equals("")) {
			try {
				int value = Integer.parseInt(param);
				setN(value);
			} catch (NumberFormatException e) {
				System.out.println("Warning: cannot parse N(upper bound):"
						+ param + " as int");
				System.out.println(" -- Using default value (3)");
				setN(3);
			}
		}
		if (!(param = (getParameter("M"))).equals("")) {
			try {
				int value = Integer.parseInt(param);
				setM(value);
			} catch (NumberFormatException e) {
				System.out.println("Warning: cannot parse M(lower bound):"
						+ param + " as int");
				System.out.println(" -- Using default value (2)");
				setM(2);
			}
		}

		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
				/*
				 * TODO: If ever use Event Sets that are not part of
				 * com.jgaap.eventSets, this will need to be changed. You can
				 * catch the first exception, try appending com.jgaap.eventSets,
				 * then catch a second exception if even that doesn't work, but
				 * since all our eventSets are in one place right now, I didn't
				 * do it that way -- JN 04/26/09
				 */
				Object o = Class.forName("com.jgaap.eventDrivers." + param)
						.newInstance();
				if (o instanceof EventDriver) {
					setEvents((EventDriver) o);
				} else {
					throw new ClassCastException();
				}
			} catch (Exception e) {
				// System.out.println("Error: cannot create EventDriver " +
				// param);
				// System.out.println(" -- Using NaiveWordEventDriver");
				setEvents(new NaiveWordEventDriver());
			}
		}
		EventSet es = underlyingevents.createEventSet(ds);
		EventSet newEs = new EventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());
		String s;

		/* Negative upper bounds mean no upper bound */
		if (N < 0)
			N = Integer.MAX_VALUE;

		/**
		 * Check length of each event and accept if in range
		 */
		for (Event e : es) {
			s = e.toString();
// System.out.println("Event: "+s);
			if (s.length() >= M && s.length() <= N) {
				// should we clone event before adding? PMJ
				newEs.addEvent(e);
			}
		}
		return newEs;
	}

	/**
	 * Get EventDriver for relevant Events *
	 * 
	 * @return underlying EventDriver
	 */
	public EventDriver getEvents() {
		return underlyingevents;
	}

	/* Parameter settings */
	/**
	 * Get N (upper bound)
	 * 
	 * @return length upper bound
	 */
	public int getN() {
		return N;
	};

	/* Parameter settings */
	/**
	 * Get M (lower bound)
	 * 
	 * @return length lower bound
	 */
	public int getM() {
		return M;
	};

	/**
	 * Set EventDriver for relevant Events *
	 * 
	 * @param underlyingevents
	 *            underlying EventDriver
	 */
	public void setEvents(EventDriver underlyingevents) {
		this.underlyingevents = underlyingevents;
	}

	/**
	 * Set upper bound on length
	 * 
	 * @param N
	 *            length upper bound
	 */
	public void setN(int N) {
		this.N = N;
	}

	/**
	 * Set lower bound on length
	 * 
	 * @param M
	 *            length lower bound
	 */
	public void setM(int M) {
		this.M = M;
	}
}
