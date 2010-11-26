package com.jgaap.generics;

public abstract class DivergenceFunction extends DistanceFunction {

	
	abstract public String displayName();

	@Override
	public double distance(EventSet es1, EventSet es2) {
		double dist = 0;
		double first = 0;
		double second = 0;
		String divergenceOptionStr = getParameter("divergenceOption");
		int divergenceOption = Integer.parseInt(divergenceOptionStr.equalsIgnoreCase("")? "0":divergenceOptionStr);
		switch(divergenceOption){
		case 1:
			dist = (divergence(es1,es2)+divergence(es2,es1))/2.0;
			break;
		case 2:
			first = divergence(es1,es2);
			second = divergence(es1, es2);
			dist = (first > second ? first : second);
			break;
		case 3:
			first = divergence(es1,es2);
			second = divergence(es1, es2);
			dist = (first < second ? first : second);
			break;
		case 4:
			dist = divergence(es2, es1);
			break;
		case 5:
			first = divergence(es1,es2);
			second = divergence(es1, es2);
			dist = first * second;
		case 0:
		default:
			dist = divergence(es1, es2);
			break;
		}
		return dist;
	}
	
	abstract protected double divergence(EventSet es1, EventSet es2);

	@Override
	abstract public boolean showInGUI();

	@Override
	abstract public String tooltipText();

}
