# Generate a csv results file from a set of JGAAP results.
# All numeric fields are %'s (i.e. a raw score of 50.0 indicates that 49 of 98
# predictions were correct).  
BEGIN { 
  print "Canonicizers,Event Set,Analysis Method,AAAC Score,Raw Score,A,B,C,D,E,F,G,H,I,J,K,L,M"
  canonicizers = ""
  eventSet = ""
  analysisMethod = ""
  rawScore = 0
  aaacScore = 0
}

/^Canonicizers/ {
  canonicizers = ""
  for(i = 2; i <= NF; i++) {
    canonicizers = canonicizers " " $i
  }
}

/^Event/ {
  eventSet = ""
  for(i = 3; i <= NF; i++) {
    eventSet = eventSet " " $i
  }
}

/^Analysis/ {
  analysisMethod = ""
  for(i = 3; i <= NF; i++) {
    analysisMethod = analysisMethod " " $i
  }
}

/^Score:/ {
  if($4 != 0)
    rawScore = $2 / $4 * 100.0
  else
    rawScore = -1
}

/^Problem/ {
  split($4, tempProblem, "/")
  if(tempProblem[2] != 0)
    problems[$2] = tempProblem[1] / tempProblem[2] * 100.0
  else
    problems[$2] = -1
}

/^AAAC/ {
  aaacScore = $4
  printf("%s,%s,%s,%4.2f,%2.2f,", canonicizers, eventSet, analysisMethod, aaacScore, rawScore);
  printf("%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f,%2.2f\n", problems["A"], problems["B"], problems["C"], problems["D"], problems["E"], problems["F"], problems["G"], problems["H"], problems["I"], problems["J"], problems["K"], problems["L"], problems["M"]);
}
