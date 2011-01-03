import java.util.List;

import com.jgaap.backend.API;
import com.jgaap.backend.CSVIO;
import com.jgaap.backend.Utils;
import com.jgaap.generics.Document;

public class Experiment {

    public static void main(String[] args) {
		String[] documentSets = { "../docs/aaac/Demos/loadA.csv",
				"../docs/aaac/Demos/loadB.csv", "../docs/aaac/Demos/loadC.csv",
				"../docs/aaac/Demos/loadD.csv", "../docs/aaac/Demos/loadE.csv",
				"../docs/aaac/Demos/loadF.csv", "../docs/aaac/Demos/loadG.csv",
				"../docs/aaac/Demos/loadH.csv", "../docs/aaac/Demos/loadI.csv",
				"../docs/aaac/Demos/loadJ.csv", "../docs/aaac/Demos/loadK.csv",
				"../docs/aaac/Demos/loadL.csv", "../docs/aaac/Demos/loadM.csv" };
		String[] canonicizers = { "Unify Case", "Normalize Whitespace" };
		String[] eventDrivers = { "Words", "Characters" };
		String[] analysisDrivers = { "Cosine Distance","Kullback Leibler Distance" };
		for (String documentSet : documentSets) {
			try {
				API api = new API();
				List<Document> documents = Utils.getDocumentsFromCSV(CSVIO.readCSV(documentSet));
				for (Document document : documents) {
					api.addDocument(document);
				}
				for (String canonicizer : canonicizers) {
					api.addCanonicizer(canonicizer);
				}
				for (String eventDriver : eventDrivers) {
					api.addEventDriver(eventDriver);
				}
				for (String analysisDriver : analysisDrivers) {
					api.addAnalysisDriver(analysisDriver);
				}
				api.execute();
				List<Document> unknownDocuments = api.getUnknownDocuments();
				for (Document unknownDocument : unknownDocuments) {
					System.out.println(unknownDocument.getResult());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
