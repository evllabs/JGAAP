/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.gui.generics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.font.FontRenderContext;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * Simple class that extends JPanel and represents a single step of a multi-step
 * process. The class allows for an unlimited number of steps, and will change
 * its visual layout appropriately.
 * 
 * @author Chuck Liddell
 */
public class StepProgressIndicator extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID      = 1L;
    // global constants
    public final int          LINE_HEIGHT           = 8;
    public final int          OVER_HEIGHT           = 1;
    public final int          TEXT_SPACER           = 15;    
    public final int          STEP_EDGE_LENGTH      = 28;
    public final Color        ACCENT_COLOR          = Color.RED;
    public final Color        FONT_COLOR            = Color.BLACK;
    public final Font         font                  = new Font("Serif",
                                                            Font.PLAIN, 16);
    public final Font         boldFont              = new Font("Serif",
                                                            Font.ITALIC, 16);
    public final boolean      DEBUG_MODE            = true;

    /**
     * Array of step names to be displayed. The progress indicator will be
     * dynamically created and sized to fit the step names (within reason).
     */
    private Vector<String>    stepNames;

    /**
     * Keeps track of which step is currently active. Numbering starts at zero.
     */
    private int               currentStep           = 0;

    /**
     * Basic constructor. No step names exist at this point.
     */
    public StepProgressIndicator() {
        this( new String[0] );
    }

    /**
     * Constructor allowing for some, or all, of the step names to be declared.
     * 
     * @param initStepNames
     *            the names of the steps in the process.
     */
    public StepProgressIndicator(String[] initStepNames) {
        super();
        stepNames = new Vector<String>();
        for (int i = 0; i < initStepNames.length; i++) {
            stepNames.add(initStepNames[i]);
        }
        updateProgress();
    }

    /**
     * Add a step to the list of steps displayed by this StepProgressIndicator.
     * 
     * @param newStep
     *            a new step to be added to the current list.
     */
    public void addStep(String newStep) {
        stepNames.add(newStep);
        updateProgress();
    }

    /**
     * Get the preferred dimension size for this component.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 80);
    }
  
    /**
     * Advance to the next step.
     */
    public void nextStep() {
        if (currentStep < stepNames.size() - 1) {
            currentStep++;
        }
        updateProgress();
        if (DEBUG_MODE) {
            System.out.println("Proceeding to next step.");
        }
    }
    

    /**
     * Go back to the previous step.
     */
    public void previousStep() {
        if (currentStep > 0) {
            currentStep--;
        }
        updateProgress();
        if (DEBUG_MODE) {
            System.out.println("Returning to previous step.");
        }
    }

    /**
     * Update the visual representation of the step progression.
     */
    private void updateProgress() {
        this.repaint();
    }

    /**
     * Method that is called in order to render this component.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int height = this.getHeight(), width = this.getWidth(); 

        g.setFont(font);
        FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();

        // Wipe the slate clean, but don't overwrite current paint color
        Paint tempPaint = g2d.getPaint();
        g2d.setPaint(getBackground());
        g2d.fillRect(0, 0, width, height);
        g2d.setPaint(tempPaint);

        /**
         * Here we will perform a series of straightforward calculations in
         * order to properly orient the step names and lines. All of it will
         * be vertically centered, and evenly distributed horizontally.
         */

        int sumNameWidths = 0;
        int biggestNameHeight = 0;

        // Iterate through the step names
        for (String stepName : stepNames) {
            // Sum the widths of the different step names
            sumNameWidths += font.getStringBounds(stepName, frc).getWidth();

            // Determine the height of the tallest step name
            biggestNameHeight = Math.max(biggestNameHeight, (int) font
                    .getStringBounds(stepName, frc).getHeight());
        }

        // Determine spacing between names, and between name row and line
        int horizSpacing = (width - sumNameWidths)
                / (stepNames.size() + 1);
        int spaceBetweenRows = biggestNameHeight / 2;

        // Initialize starting x and y text drawing location
        int currentX = horizSpacing;
        int currentY = (height - (biggestNameHeight + spaceBetweenRows + STEP_EDGE_LENGTH)) / 2;

        // Get ready to calculate the ending points for both lines
        int endOfCompleteLine = 0;
        int endOfIncompleteLine = 0;

        // Draw the step names
        for (int i = 0; i < stepNames.size(); i++) {
            if (i == currentStep) { // special case, current step
                g.setFont(boldFont);
                g.setColor(ACCENT_COLOR);
            }
            g.drawString(stepNames.get(i), currentX, currentY + TEXT_SPACER);
            if (i == currentStep) {
                g.setFont(font);
                g.setColor(FONT_COLOR);
            }

            int stepWidth = (int) font.getStringBounds(stepNames.get(i), frc)
                    .getWidth();

            // Shift X pointer appropriately
            currentX += stepWidth + horizSpacing;

            // Set the end-of-line location for complete and incomplete lines
            if (i == currentStep) {
                endOfCompleteLine = currentX - horizSpacing - (stepWidth / 2);
                //TODO: Draw endcap
            }
            if (i == stepNames.size() - 1) {
                endOfIncompleteLine = currentX - horizSpacing - (stepWidth / 2);
                //TODO: Draw endcap
            }
        }

        // Prepare the X,Y location pointers for line drawing
        currentX = 0;
        currentY += biggestNameHeight + spaceBetweenRows;

        int gradientSwitch = (int)(LINE_HEIGHT * 0.4);
        Color outlineColor = new Color( 0x9c9c9c );
                
        // Calculate gradients and paint them for the line designating incomplete tasks
        Color under_startColor = new Color(0xdf1f1f1);
        Color under_midColor = new Color(0xd4d4d4);
        Color under_endColor = new Color(0x6b6b6b);
        
        GradientPaint
        p1 = new GradientPaint(currentX, currentY, under_startColor,
                currentX, currentY + gradientSwitch, under_midColor ),
        p2 = new GradientPaint(currentX, currentY + gradientSwitch, under_midColor,
                currentX, currentY + LINE_HEIGHT, under_endColor );
        
        // Draw the top part of the line designating incomplete tasks
        g2d.setPaint(p1);
        g2d.fillRect(currentX, currentY, endOfIncompleteLine, gradientSwitch);
        g2d.fillArc( endOfIncompleteLine - LINE_HEIGHT / 2, currentY, LINE_HEIGHT, LINE_HEIGHT, 90, -90);
        g2d.setColor( outlineColor );
        g2d.drawLine(currentX, currentY, endOfIncompleteLine, currentY);        

        // Draw the bottom part of the line designating incomplete tasks
        g2d.setPaint(p2);
        g2d.fillRect(currentX, currentY + gradientSwitch, endOfIncompleteLine, LINE_HEIGHT - gradientSwitch);
        g2d.fillArc( endOfIncompleteLine - LINE_HEIGHT / 2, currentY, LINE_HEIGHT, LINE_HEIGHT, 0, -90);
        g2d.setColor( outlineColor );
        g2d.drawLine(currentX, currentY + LINE_HEIGHT, endOfIncompleteLine, currentY + LINE_HEIGHT);
        g2d.drawArc( endOfIncompleteLine - LINE_HEIGHT / 2, currentY, LINE_HEIGHT, LINE_HEIGHT, 90, -180);
        
        // Calculate gradients and paint them for the line designating complete tasks        
        Color over_startColor = new Color(0xd0d0ff);
        Color over_midColor = new Color(0x2b2bc6);
        Color over_endColor = new Color(0x2a2a4d);
        
        p1 = new GradientPaint(currentX, currentY - OVER_HEIGHT, over_startColor,
                currentX, currentY + gradientSwitch, over_midColor );
        p2 = new GradientPaint(currentX, currentY + gradientSwitch, over_midColor,
                currentX, currentY + LINE_HEIGHT + OVER_HEIGHT, over_endColor );
        
        // Draw the top part of the line designating complete tasks
        g2d.setPaint(p1);
        g2d.fillRect(currentX, currentY - OVER_HEIGHT, endOfCompleteLine, gradientSwitch + OVER_HEIGHT);
        
        
        g2d.fillArc( endOfCompleteLine - (LINE_HEIGHT + OVER_HEIGHT * 2) / 2, currentY - OVER_HEIGHT, 
        		LINE_HEIGHT, LINE_HEIGHT + OVER_HEIGHT * 2, 90, -90);
        
        
        g2d.setColor( outlineColor );
        g2d.drawLine(currentX, currentY - OVER_HEIGHT, endOfCompleteLine, currentY - OVER_HEIGHT);

        // Draw the bottom part of the line designating complete tasks
        g2d.setPaint(p2);
        g2d.fillRect(currentX, currentY + gradientSwitch, endOfCompleteLine, LINE_HEIGHT - gradientSwitch + OVER_HEIGHT);
        
        
        g2d.fillArc( endOfCompleteLine - (LINE_HEIGHT + OVER_HEIGHT * 2) / 2, currentY - OVER_HEIGHT, 
        		LINE_HEIGHT, LINE_HEIGHT + OVER_HEIGHT * 2, 0, -90);
        
        
        g2d.setColor( outlineColor );
        g2d.drawLine(currentX, currentY + LINE_HEIGHT + OVER_HEIGHT, 
        		endOfCompleteLine, currentY + LINE_HEIGHT + OVER_HEIGHT);
        g2d.setColor( outlineColor );
        g2d.drawArc( endOfCompleteLine - (LINE_HEIGHT + OVER_HEIGHT * 2) / 2, currentY - OVER_HEIGHT, 
        		LINE_HEIGHT, LINE_HEIGHT + OVER_HEIGHT * 2, 90, -180);
        
        g2d.setPaint( tempPaint );
        g2d.dispose();
    }
}
