package com.jgaap.generics;

public abstract class DivergenceFunction extends DistanceFunction {

	abstract public String displayName();

	@Override
	public double distance(EventSet es1, EventSet es2) {
		double dist;
		double first;
		double second;
		String divergenceOptionStr = getParameter("divergenceOption");
		int divergenceOption = Integer.parseInt(divergenceOptionStr
				.equalsIgnoreCase("") ? "0" : divergenceOptionStr);
		switch (divergenceOption) {
		case 1:
			dist = (divergence(es1, es2) + divergence(es2, es1)) / 2.0;
			break;
		case 2:
			first = divergence(es1, es2);
			second = divergence(es1, es2);
			dist = (first > second ? first : second);
			break;
		case 3:
			first = divergence(es1, es2);
			second = divergence(es1, es2);
			dist = (first < second ? first : second);
			break;
		case 4:
			dist = divergence(es2, es1);
			break;
		case 5:
			first = divergence(es1, es2);
			second = divergence(es1, es2);
			dist = first * second;
            break;
		case 0:
		default:
			dist = divergence(es1, es2);
			break;
		}
		return dist;
	}

	public String getDivergenceType() {
		String result = "";
		String divergenceString = getParameter("divergenceType");
		int divergenceType = Integer
				.parseInt((divergenceString.equals("") ? "0" : divergenceString));
		switch (divergenceType) {
		case 1:
			result += " (Average)";
			break;
		case 2:
			result += " (Max)";
			break;
		case 3:
			result += " (Min)";
			break;
		case 4:
			result += " (Reverse)";
			break;
		case 5:
			result += " (Cross)";
			break;
		default:
		}
		return result;
	}

	abstract protected double divergence(EventSet es1, EventSet es2);

	@Override
	abstract public boolean showInGUI();

	@Override
	abstract public String tooltipText();

}
