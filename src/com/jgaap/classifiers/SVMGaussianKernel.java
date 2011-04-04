/**
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
