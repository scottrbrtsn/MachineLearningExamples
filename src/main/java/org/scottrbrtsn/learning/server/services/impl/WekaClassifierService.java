package org.scottrbrtsn.learning.server.services.impl;

import org.scottrbrtsn.learning.server.services.IWekaClassifierService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WekaClassifierService implements IWekaClassifierService {

    private static final Logger LOGGER = Logger.getLogger(WekaClassifierService.class.getName());

    ArrayList<Attribute> fvWekaAttributes = setupAttributes();


    public double[] runClassifierExample() throws Exception{
        LOGGER.info("Start Classifier Example");
        //1
        Instances isTrainingInstances = setupInstances("model.txt");
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);
        isTrainingInstances.forEach(instance -> isTrainingSet.add(instance));
        isTrainingSet.setClass(fvWekaAttributes.get(3));


        Instances isTestingInstances = setupInstances("data.txt");
        Instances isTestingSet = new Instances("Rel", fvWekaAttributes, 10);
        isTestingInstances.forEach(instance -> isTestingSet.add(instance));
        isTestingSet.setClass(fvWekaAttributes.get(3));

        // Create a naïve bayes classifier
        Classifier cModel = new NaiveBayes();
        cModel.buildClassifier(isTrainingSet);

        //3 Test the classifier
        // Test the model
        Evaluation eTest = new Evaluation(isTrainingSet);
        eTest.evaluateModel(cModel, isTestingSet);

        // Print the result à la Weka explorer:
        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);

        // Get the confusion matrix
        double[][] cmMatrix = eTest.confusionMatrix();

        //4 use the classifier
        // Specify that the instance belong to the training set
        // in order to inherit from the set description
        Instance iUse = setupInstances("instance.txt").firstInstance();
        iUse.setDataset(isTrainingSet);

        // Get the likelihood of each classes
        // fDistribution[0] is the probability of being “positive”
        // fDistribution[1] is the probability of being “negative”
        double [] toReturn = cModel.distributionForInstance(iUse);
        LOGGER.info("Probability the model set is positive: " + toReturn[0]);
        LOGGER.info("Probability the model set is negative: " + toReturn[1]);

        return toReturn;
    }

    private Instances setupInstances(String fileName) throws IOException{
        //create more instances via csv, or read from db
        //convert csv to arff
        String path = "src\\main\\resources\\data\\";
        BufferedReader reader = new BufferedReader(new FileReader(path + fileName));
        return new Instances(reader);
    }

    private ArrayList<Attribute> setupAttributes(){
        // Declare two numeric attributes
        Attribute attribute1 = new Attribute("firstNumeric", 0);

        Attribute attribute2 = new Attribute("secondNumeric", 1);

        // Declare a nominal attribute along with its values
        List<String> fvNominalVal = new ArrayList<>(3);
        fvNominalVal.add("blue");
        fvNominalVal.add("gray");
        fvNominalVal.add("black");
        Attribute attribute3 = new Attribute("aNominal", fvNominalVal, 2);

        // Declare the class attribute along with its values
        List<String> fvClassVal = new ArrayList<>(2);
        fvClassVal.add("positive");
        fvClassVal.add("negative");
        Attribute ClassAttribute = new Attribute("theClass", fvClassVal, 3);

        // Declare the feature vector
        ArrayList<Attribute> fvWekaAttributes = new ArrayList<>(4);
        fvWekaAttributes.add(attribute1);
        fvWekaAttributes.add(attribute2);
        fvWekaAttributes.add(attribute3);
        fvWekaAttributes.add(ClassAttribute);

        return fvWekaAttributes;

    }
}
