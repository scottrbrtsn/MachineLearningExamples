package org.scottrbrtsn.learning.server.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Entity
@Data
public class Population {

    //TODO customize size of population, or add population growth
    private int popSize = 10;
    private List<Individual> individuals = new ArrayList<>();
    private int [] initialFather = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    private int [] initialMother = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

    public void initializePopulation() {//get values from user
      
//        for (int i = 0; i < 10; i++) {//the first population offspring of mother and father
//            individuals.add(new Individual(initialFather, initialMother));
//        }
        Random rn = new Random();
        int [] initDad = {rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11)};
        int [] initMom = {rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11),
                rn.nextInt(11), rn.nextInt(11)};
        for (int i = 0; i < 10; i++) {//the first population offspring of mother and father
            individuals.add(new Individual(initDad, initMom));
        }
    }

    //TODO get Fittest Male
    public Individual getFittestMale() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        Individual ind;
        for (int i = 0; i < individuals.size(); i++) {
            ind = individuals.get(i);
            if(ind.getGender().equals("male")) {
                if (maxFit <= individuals.get(i).getFitness()) {
                    maxFit = individuals.get(i).getFitness();
                    maxFitIndex = i;
                }
            }
        }
        return individuals.get(maxFitIndex);
    }

    //TODO get Fittest Female
    public Individual getFittestFemale() {
        int maxFit = 0;
        Individual ind;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.size(); i++) {
            ind = individuals.get(i);
            if(ind.getGender().equals("female")) {
                if (maxFit <= individuals.get(i).getFitness()) {
                    maxFit = individuals.get(i).getFitness();
                    maxFitIndex = i;
                }
            }
        }
        return individuals.get(maxFitIndex);
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.size(); i++) {
            if (minFitVal >= individuals.get(i).getFitness()) {
                minFitVal = individuals.get(i).getFitness();
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    //Calculate fitness of each individual
    //TODO calculate fitness based on trait values
    public void calculateFitness() {

        for (int i = 0; i < individuals.size(); i++) {
            individuals.get(i).calcFitness("male", initialFather, initialMother);
        }
    }

    public void calculateIndividualConsequences(){
        //a single highly "fit" individual, might be killed for some reason
        //
    }

    //Average all traits from all individuals so that the population can be measured
    public void calculatePopulationFitness(){

    }

    public void executePopulationConsequences(){
        //unbalanced society will lead to destruction
        //
    }

}
