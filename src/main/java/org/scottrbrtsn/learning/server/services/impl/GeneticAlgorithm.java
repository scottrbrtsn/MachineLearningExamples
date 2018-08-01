package org.scottrbrtsn.learning.server.services.impl;

import org.apache.log4j.Logger;
import org.scottrbrtsn.learning.server.domain.Individual;
import org.scottrbrtsn.learning.server.domain.Population;
import org.scottrbrtsn.learning.server.services.IGeneticAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GeneticAlgorithm implements IGeneticAlgorithm {
    private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class.getName());

    private Population population;
    private Individual fittestMale;
    private Individual fittestFemale;
    private int generationCount = 0;

    @Override
    public String runGeneticAlgorithm() {

        Random rn = new Random();
        population = new Population();
        //Initialize population
        population.initializePopulation();

        //Calculate fitness of each individual
        population.calculateFitness();

        LOGGER.info("Generation: " + generationCount + " FittestMale: " + population.getFittestMale());
        LOGGER.info("Generation: " + generationCount + " FittestFemale: " + population.getFittestFemale());
        String toReturn = "Starting Fittest Male: " + population.getFittestMale()
                + "Starting Fittest Female: " + population.getFittestFemale();

        //While population does not get an individual with maximum fitness
        while (population.getFittestMale().getFitness() < 120
                && population.getFittestFemale().getFitness() < 120
                && population.getIndividuals().size() < 20000) {
            ++generationCount;

            //Do selection
            selection();


            //Add fittest offspring to population
            addFittestOffspring();

            //Calculate new fitness value
            //  population.calculateFitness();

            LOGGER.info("Generation: " + generationCount + " FittestMale: " + population.getFittestMale());
            LOGGER.info("Generation: " + generationCount + " FittestFemale: " + population.getFittestFemale());
            LOGGER.info("Generation: " + generationCount + " PopulationSize: " + population.getIndividuals().size());

        }
        toReturn = toReturn + "After " + population.getIndividuals().size() + "Generations, "
                + "Ending Fittest Male: " + population.getFittestMale()
                + "Ending Fittest Female: " + population.getFittestFemale();

        return toReturn;

    }

    //Selection
    //Assumes asexuality
    //TODO: make gender differences, so, get fittestMale, getFittestFemale
    private void selection() {//based on gender of the user
        //Select the fittest individual
        fittestMale = population.getFittestMale();

        //Select the second fittest individual
        fittestFemale = population.getFittestFemale();
    }

    //Mutation
    private void mutation(Individual ind) {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.getIndividuals().get(0).getTraits().length);
        LOGGER.info("MutationPoint: " + mutationPoint);

        //Mutate values at the mutation point
        ind.getTraits()[mutationPoint] = rn.nextInt(11);

    }

    //Get fittest offspring
    private Individual getFittestOffspring() {
        if (fittestMale.getFitness() > fittestFemale.getFitness()) {
            return fittestMale;
        }
        return fittestFemale;
    }

    //Replace least fittest individual from most fittest offspring
    private void addFittestOffspring() {
        Individual ind = new Individual(
                fittestMale.getTraits(), fittestFemale.getTraits());

        Random rn = new Random();
        if (rn.nextInt() % 7 < 5) {
            mutation(ind);
        }
        ind.calcFitness("male",
                population.getInitialFather(),
                population.getInitialMother());

        population.getIndividuals().add(ind);
    }

}
