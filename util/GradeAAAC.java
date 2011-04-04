/**
 **/
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;

public class GradeAAAC {
    public static void main(String[] args) throws Exception {
	HashMap<String, String> correctAnswers = new HashMap<String, String>();
	Scanner answers = new Scanner(new File("../docs/aaac/correct.txt"));
	while(answers.hasNextLine()) {
	    String s = answers.nextLine();
	    if(s.trim().length() <= 1)
		continue;
	    String[] sArray = s.split("\\s");
	    if(sArray.length >= 2) 
		correctAnswers.put(sArray[0], sArray[1]);
	}

	int numQuestions = 0;
	int numberRight = 0;
	int correctPerProblem[] = new int[13];
	int numPerProblem[] = new int[13];
	double scorePerProblem[] = new double[13];
	Scanner keyboard = new Scanner(System.in);
	while(keyboard.hasNextLine()) {
	    String s = keyboard.nextLine();
	    if(s.indexOf("problem") < 0) continue;
	    numQuestions++;

	    String problem = s.substring(22, 35);
	    String answer = s.substring(38, 46);

	    numPerProblem[(int)(problem.charAt(0)) - (int)'A']++;
	    
	    String rightAnswer = correctAnswers.get(problem);

	    if(answer.equals(rightAnswer.trim())) {
		numberRight++;
		correctPerProblem[(int)(problem.charAt(0)) - (int)'A']++;
	    }

	}

	for(int i = 0; i < 13; i++) {
	    System.out.println("Problem " + ((char)((int)'A' + i)) + " : " + correctPerProblem[i] + "/" + numPerProblem[i] + " (" + (scorePerProblem[i] = (((double)(correctPerProblem[i]) / (double)(numPerProblem[i])) * 100.0)) + "%)");
	}
	System.out.println("Score: " + numberRight + " / " + numQuestions + " (" + ((double)numberRight / (double)numQuestions * 100.0) + "%)");

	double aaacScore = 0.0;
	for (int z = 0; z < 13; z++)
	    aaacScore += scorePerProblem[z];

	System.out.println("AAAC Final Score: " + aaacScore + "%");
    }
}
