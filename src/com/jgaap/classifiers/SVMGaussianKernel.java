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
package com.jgaap.classifiers;

/**
 * Wrapper for using SVM with a Gaussian Kernel
 * (This is a dummy class since autopopulation cannot
 * detect parameters at this point)
 * Warning: HIC SVNT DRACONES
 */
public class SVMGaussianKernel extends SVM {
	public String displayName(){
	    return "Gaussian SVM";
	}

	public String tooltipText(){
	    return "Radial Basis Kernel Support Vector Machine Classification";
	}

	public boolean showInGUI(){
	    return true;
	}
    public SVMGaussianKernel() {
        super(2);
    }
}
