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

import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.Icon;

/**
 * Simple class that allows for rectangular gradient icons.
 * 
 * @author Chuck Liddell
 *
 */
public class GradientIcon implements Icon {

	private final int height;
	private final int width;
	private final Color color;
	
	private float startMultiplier;
	private float endMultiplier;
	private float switchLocation;
	
	/**
	 * Simple constructor that accepts color as a single integer value. 
	 * 
	 * Creates an opaque sRGB color with the specified combined RGB value 
	 * consisting of the red component in bits 16-23, the green component 
	 * in bits 8-15, and the blue component in bits 0-7. The actual color 
	 * used in rendering depends on finding the best match given the color 
	 * space available for a particular output device. Alpha is defaulted 
	 * to 255. 
	 * 
	 * @param width
	 * 		The desired width of the icon
	 * @param height
	 * 		The desired height of the icon
	 * @param color
	 * 		Integer value used to create the color of the icon
	 */
	public GradientIcon( int width, int height, int color){
		this( height, width, new Color(color), 0.7f, 0.3f );
	}
	
	/**
	 * Simple constructor that accepts a Color instance and uses it
	 * to draw the gradient.
	 * 
	 * @param width
	 * 		The desired width of the icon
	 * @param height
	 * 		The desired height of the icon
	 * @param color
	 * 		The desired color of the icon
	 */
	public GradientIcon( int width, int height, Color color){
		this( height, width, color, 0.7f, 0.3f );
	}
	
	/**
	 * Complex constructor that accepts several additional parameters. Start and end
	 * intensity settings should be a float value from 0.0f to 1.0f, where a higher
	 * number results in a brighter white at the top of the gradient and a darker
	 * black at the bottom of the gradient. Smaller intensities will produce softer
	 * color transitions in the gradient.
	 * 
	 * For a description of how a Color is created from an integer, 
	 * @see GradientIcon#GradientIcon(int height, int width, int color)
	 * 
	 * @param width
	 * 		The desired width of the icon
	 * @param height
	 * 		The desired height of the icon
	 * @param color
	 * 		The desired color of the icon, represented as an int value.
	 * @param startIntensity
	 * 		The intensity of the white at the top of the gradient. A high intensity 
	 * 		will result in a harsher color change from the primary color to the white.
	 * @param endIntensity
	 * 		The intensity of the black at the bottom of the gradient. A high intensity 
	 * 		will result in a harsher color change from the primary color to the black.
	 */
	public GradientIcon( int width, int height, int color, float startIntensity, float endIntensity ){
		this(height, width, new Color( color ), startIntensity, endIntensity);
	}
	
	/**
	 * Complex constructor that accepts several additional parameters. Start and end
	 * intensity settings should be a float value from 0.0f to 1.0f, where a higher
	 * number results in a brighter white at the top of the gradient and a darker
	 * black at the bottom of the gradient. Smaller intensities will produce softer
	 * color transitions in the gradient.
	 * 
	 * @param width
	 * 		The desired width of the icon
	 * @param height
	 * 		The desired height of the icon
	 * @param color
	 * 		The desired color of the icon
	 * @param startIntensity
	 * 		The intensity of the white at the top of the gradient. A high intensity 
	 * 		will result in a harsher color change from the primary color to the white.
	 * @param endIntensity
	 * 		The intensity of the black at the bottom of the gradient. A high intensity 
	 * 		will result in a harsher color change from the primary color to the black.
	 */
	public GradientIcon( int width, int height, Color color, float startIntensity, float endIntensity ){
		this.height = height;
		this.width = width;
		this.color = new Color( color.getRGB() ); //defensively copy
		this.startMultiplier = 1.0f - startIntensity;
		this.endMultiplier = 1.0f - endIntensity;
		this.switchLocation = 0.4f;
	}
	
	/**
	 * @return
	 * 		Height of the icon.
	 */
	public int getIconHeight(){
		return height;
	}

	/**
	 * @return
	 * 		Width of the icon.
	 */
	public int getIconWidth(){
		return width;
	}
	
	/**
	 * Called when the icon needs to be painted on a component.
	 * 
	 * @param component
	 * 		The component to paint the icon onto
	 * @param g
	 * 		The Graphics instance to use for painting
	 * @param x
	 * 		The x coordinate on the component's painting surface where
	 * 		this icon should be drawn
	 * @param y
	 * 		The y coordinate on the component's painting surface where
	 * 		this icon should be drawn
	 */
	public void paintIcon(Component component, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		
		int gradientStart  = (int)(y - height * startMultiplier), 
        gradientSwitch = (int)(height * switchLocation), 
        gradientEnd    = (int)(y + height + height * endMultiplier);
		
        GradientPaint
        p1 = new GradientPaint(x, gradientStart, new Color(0xffffff),
                x, y + gradientSwitch, color),
        p2 = new GradientPaint(x, y + gradientSwitch, color,
                x, gradientEnd, new Color(0x000000));
        Paint oldPaint = g2.getPaint();
        g2.setPaint(p1);
        g2.fillRect(x, y, width, gradientSwitch);
        g2.setPaint(p2);
        g2.fillRect(x, y + gradientSwitch, width, height - gradientSwitch);
        g2.setPaint(oldPaint);
	}

}
