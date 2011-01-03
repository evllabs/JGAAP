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
import java.util.Scanner;
import java.util.Vector;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class NewGrader {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Vector<String> docList = new Vector<String>();
        while(input.hasNextLine()){
            String curr = input.nextLine();
	    //System.out.println(i);
	    curr = curr.replace(" ", "\\ ");
	    curr = curr.replaceAll("\\(\\d\\d:\\d\\d:\\d\\d\\)","*");
	    curr=curr.replace("001.txt", "*.txt");
	    docList.add(curr);
	    //System.out.println(curr);
        }
        for(String iterator:docList){
	    if(iterator.contains("*.txt")){
		System.out.println("cat "+iterator + " | head -4");
		System.out.println("cat "+iterator + " | java GradeAAAC | tail -2");
	    }
	    }
    }
}
