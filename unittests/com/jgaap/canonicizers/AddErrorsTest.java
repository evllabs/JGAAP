/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author darrenvescovi
 *
 */
public class AddErrorsTest {

	/**
	 * Test method for {@link com.jgaap.canonicizers.AddErrors#process(java.util.Vector)}.
	 */
	@Test
	public void testProcess() {
		
				
		int goodTest = 0;
		
		for (int i = 0; i <=99; i++)
		{
			StringBuilder test1 = new StringBuilder();
			
			for (int k = 0; k<= 999; k++)
			{
					test1.append('b');
			}
			
			
			AddErrors thing = new AddErrors(25);
			int notChanged = 0;
			char[] test2 = thing.process(test1.toString().toCharArray());
			for (int j=0; j<test2.length; j++)
			{
				//System.out.println((int)test2.elementAt(j));
				
				if (test2[j]=='b')
				{
					notChanged = notChanged + 1;
				}
				
			}
			
			double probUnchanged;
			double q = (double)notChanged;
			probUnchanged = q/1000.0;
			
			double testStat;
			
			double p;
			p=(double)((q*(1000-q)/1000.0)/1000.0);
			
			testStat = 1.96 * Math.sqrt(p);
			
			if (.75 >= probUnchanged-testStat && .75 <= probUnchanged+testStat )
			{
				
				goodTest = goodTest + 1;
				//System.out.println("       " + goodTest);
			}
			
			//System.out.println(/n);
		}
		
		
		double probGood;
		probGood = goodTest/100.0; // this prob should be greater than .95 because the confidence interval
								 // may not always cover the .62 mark for author Mary
		
		assertTrue(probGood >= .95);
		
		int [][] LA = new int [1000][26];
			for (int i = 0; i <=999; i++)
			{
				StringBuilder test1 = new StringBuilder();
				
				for (int k = 0; k<= 999; k++)
				{
						test1.append('b');
				}
				
				
				AddErrors thing = new AddErrors(100);
				
				char[] test2 = thing.process(test1.toString().toCharArray());
				for (int j=0; j<test2.length; j++)
				{
					//System.out.println((int)test2.elementAt(j));
					
					if (test2[j]!='b')
					{
						LA[i][(int)test2[j]-65]=LA[i][(int)test2[j]-65]+1;
					}
					
				}
				
			}
			
		
		
		
		assertTrue(ANOVA(LA));
			
			//ANOVA(LA);
	}
	
	
	
	public boolean ANOVA(int [][] letterArray)
	{
		boolean result = false;
		
		double ssy =0.0;
		double sst = 0.0;
		double sse;
		double mst;
		double mse;
		double yBar =0.0;
		double F;
		double [] yBars = new double [26];
		
		for (int j =0; j<=25; j++)
		{
			yBars[j] = 0.0;
			for (int i=0; i<=999; i++)
			{
				yBars[j]=yBars[j]+letterArray[i][j];
			}
			yBars[j]=yBars[j]/1000;
			//System.out.println(yBars[j]);
		}
		
		for (int i =0; i<=25; i++)
		{
			yBar = yBar + yBars[i];
		}
		
		yBar = yBar/26;
		//System.out.println("ybar="+yBar);
		
		for(int i=0; i<=999; i++)
		{
			for(int j=0; j<=25; j++)
			{
				ssy = ssy + ((letterArray[i][j]-yBar)*(letterArray[i][j]-yBar));
			}
		}
		
		//System.out.println("ssy="+ssy);
				
		for(int i=0; i<=25; i++)
		{
			sst=sst+100*((yBars[i]-yBar)*(yBars[i]-yBar));
		}
		//System.out.println("sst="+sst);
		
		sse=ssy-sst;
		
		//System.out.println("sse="+sse);
		
		mst=sst/25;
		mse=sse/25974;
		
		//System.out.println("mst="+mst);
		//System.out.println("mse="+mse);
		
		F=mst/mse;
		
		System.out.println("F="+F);
		
		if(Math.abs(F)<1.506524)
		{
			result=true;
		}
		
		
		return result;
	}

}
