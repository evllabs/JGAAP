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
package com.jgaap.gui.dnd;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * GlassPane class, responsible for rendering an icon
 * under the mouse during a drag and drop.
 * 
 * @author Chuck Liddell
 *
 */
public class IconGlassPane extends JComponent {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point location;
	private Icon icon;
    
    public IconGlassPane() {
    }    
     
    public void updateIconLocation() {
    	Point oldLocation = this.location;
    	this.location = (Point)MouseInfo.getPointerInfo().getLocation().clone();
        SwingUtilities.convertPointFromScreen(location, this);
        
        //Only repaint the new spot where the icon is now, and the
        // old spot where it was on the last draw
        Rectangle newClip = new Rectangle(location.x - icon.getIconWidth() / 2, 
        		location.y - icon.getIconHeight() / 2,
                icon.getIconWidth(), icon.getIconHeight());
        newClip.add(new Rectangle(oldLocation.x - icon.getIconWidth() / 2, 
        		oldLocation.y - icon.getIconHeight() / 2,
                icon.getIconWidth(), icon.getIconHeight()));
        repaint(newClip);
    }
    
    public void hideIcon() {
    	//System.out.println("hideIcon()");
    	icon = null;
    	setVisible(false);
    }
    
    public void initLocation(){
    	this.location = (Point)MouseInfo.getPointerInfo().getLocation().clone();
        SwingUtilities.convertPointFromScreen(location, this);
    }
    
    public void showIcon( Icon icon ) {
    	//System.out.println("showIcon()");
        this.icon = icon;
        Point location = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen( location, this);
        this.location = location; 
        initLocation();
        setVisible(true);
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g; 
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); 
    	
        if (icon != null && location != null) {
        	
            int x = location.x - icon.getIconWidth() / 2;
            int y = location.y - icon.getIconHeight() / 2;
            
            icon.paintIcon(this, g, x, y);
        }
        g2.dispose(); 
    }
     
    /** 
     * If someone adds a mouseListener to the GlassPane or set a new cursor 
     * we expect that he knows what he is doing 
     * and return the super.contains(x, y) 
     * otherwise we return false to respect the cursors 
     * for the underneath components 
     */ 
    public boolean contains(int x, int y) { 
        if (getMouseListeners().length == 0 && getMouseMotionListeners().length == 0 
                && getMouseWheelListeners().length == 0 
                && getCursor() == Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)) { 
            return false; 
        } 
        return super.contains(x, y);
    }

}
