// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventHistogram;
import javax.swing.*;


/**
 * This event set is all events occurring only once of an underlying event model
 * * (parameterized as underlyingevents)
 * 
 * @author Patrick Juola
 * @since 5.0
 **/
public class RareWordsEventDriver extends EventDriver {

    	JLabel MLabel = new JLabel();
        JComboBox MBox = new JComboBox();
        JLabel NLabel = new JLabel();
        JComboBox NBox = new JComboBox();

	@Override
	public String displayName() {
		return "Rare Words";
	}

	@Override
	public String tooltipText() {
		return "Rare words such as Words appearing only once or twice per document";
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
            MBox.setSelectedIndex(M-1);
            MBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    MBoxActionPerformed(evt);
                }
            });

            NLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            NLabel.setText("N");

            NBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
            NBox.setSelectedIndex(N-1);
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
            N = NBox.getSelectedIndex()+1;
        }

        private void MBoxActionPerformed(java.awt.event.ActionEvent evt) {
            M = MBox.getSelectedIndex()+1;
        }

	/** Underlying EventDriver from which Events are drawn. */
	public EventDriver underlyingevents = new NaiveWordEventDriver();
	public int M = 1,N = 2;

	@Override
	public EventSet createEventSet(Document ds) {

		String param;
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
				System.out.println("Error: cannot create EventDriver " + param);
				System.out.println(" -- Using NaiveWordEventDriver");
				setEvents(new NaiveWordEventDriver());
			}
		}

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
		EventSet es = underlyingevents.createEventSet(ds);
		EventSet newEs = new EventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		/**
		 * Create histogram with all events from stream
		 */
		EventHistogram hist = new EventHistogram();
		for (int i = 0; i < es.size(); i++) {
			hist.add(es.eventAt(i));
		}

		/**
		 * Re-search event stream for rare events as measured by histogram
		 * count. If count is 1, it's a hapax, etc. 
		 */
		System.out.println("M = " + M + "; N = " + N);
		for (Event e : es) {
			int n = hist.getAbsoluteFrequency(e);
			System.out.println(e.toString() + " " + n);
			if (n >= M && n <= N)
				newEs.addEvent(e);
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

	/**
	 * Set EventDriver for relevant Events *
	 * 
	 * @param underlyingevents
	 *            underlying EventDriver
	 */
	public void setEvents(EventDriver underlyingevents) {
		this.underlyingevents = underlyingevents;
	}

	public int getM() {
		return M;
	}
	public void setM(int M) {
		this.M = M;
	}

	public int getN() {
		return N;
	}
	public void setN(int N) {
		this.N = N;
	}

}
