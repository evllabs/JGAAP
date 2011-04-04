/**
 **/
public class GenerateTests {
    public static void main(String[] args) {
	String[] eventSets = { "Characters", "Character BiGrams", "Word Lengths", "Word BiGrams", "Words"};
	String[] analyzers = { "Cosine Distance", "LDA", "Linear SVM", "Gaussian SVM"};
	//String[] canonicizers = { "Smash Case", "Normalize Whitespace", "Strip HTML", "Strip Punctuation", "Smash Case Normalize Whitespace", "Smash Case Strip HTML", "Smash Case Strip Punctuation", "Normalize Whitespace Strip HTML", "Normalize Whitespace Strip Punctuation", "Strip HTML Strip Punctuation", "Smash Case Normalize Whitespace Strip HTML", "Normalize Whitespace Strip HTML Strip Punctuation", "Smash Case Normalize Whitespace Strip Punctuation", "Smash Case Normalize Whitespace Strip HTML Strip Punctuation", "Smash Case Strip HTML Strip Punctuation"};
	
	//	String[] canonicizers = {"Smash Case Normalize Whitespace", "Smash Case Normalize Whitespace Strip Punctuation", "Smash Case Strip Punctuation", "Smash Case", "Strip Punctuation"};
	String[] canonicizers = {"  "};

	for(String e : eventSets) {
	    for(String a : analyzers) {
		for(String c : canonicizers) {
		    System.out.println(c + "," + e + "," + a + ",");
		    System.out.println(c + "," + e + "," + a + ",mc");
		}
	    }
	}
    }
}
