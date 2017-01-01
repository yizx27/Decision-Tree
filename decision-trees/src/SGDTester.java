package cs446.homework2;

import java.io.File;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import cs446.weka.classifiers.trees.Id3;

public class SGDTester {
    public static void main(String[] args) throws Exception {

	if (args.length != 10) {
	    System.err.println("Usage: 5 tuples of traing+testing sets");
	    System.exit(-1);
	}

	double numCorrect = 0;
	double numTotal = 0;

	// five-fold cross validation
	for(int i=0; i<10; i+=2){
	    Instances train = new Instances(new FileReader(new File(args[i])));
	    Instances test = new Instances(new FileReader(new File(args[i+1])));

	    train.setClassIndex(train.numAttributes() - 1);
	    test.setClassIndex(test.numAttributes() - 1);

	    SGD classifier = new SGD();
	    classifier.buildClassifier(train);

	    Evaluation evaluation = new Evaluation(test);
	    evaluation.evaluateModel(classifier, test);
	    System.out.println(evaluation.toSummaryString());

	    numCorrect += evaluation.correct();
	    numTotal += evaluation.correct() + evaluation.incorrect();	
	}

	System.out.println("SGD numCorrect percentage across cv is: " + numCorrect / numTotal * 100 + " %");
    }
}
