package org.scottrbrtsn.learning.server.domain;

import lombok.Data;
import org.apache.log4j.Logger;

import java.util.Random;

//@Entity
@Data
public class Individual {

    private static final Logger LOGGER = Logger.getLogger(Individual.class.getName());
    private int fitness = 0;
    private int[] traits = new int[12];//break these up into savable individual traits

    //demographics
    private String gender;

    //big 5 personality (0-10)
    private int openness;
    private int conscientiousness;
    private int extroversion;
    private int agreeableness;
    private int neuroticism;
    private int dynamicPersonality;//some argue there is a 6th trait, one where the big-5 are plastic

    //common abnormal (0-10)
    private int depression;//might kill self
    private int anxiety;//might accidentally kill others
    private int agressiveness;//might intentionally kill others

    //positive psychology (0-10)
    private int mindfulness;
    private int flow;
    private int competence;

    private int age;

    //belief
    private String highestCompetency;

    public Individual(int[] fathersTraits, int [] mothersTraits){
        int random;
        for (int i = 0; i < traits.length; i++) {
           random = new Random().nextInt(2);
            if(random == 0){//assuming no dominance, only random
                traits[i] = fathersTraits[i];
                gender = "male";
            }else{
                traits[i] = mothersTraits[i];
                gender = "female";
            }
        }

        openness = traits[0];
        conscientiousness = traits[1];
        extroversion = traits[2];
        agreeableness = traits[3];
        neuroticism = traits[4];
        dynamicPersonality = traits[5];
        depression = traits[6];
        anxiety = traits[7];
        agressiveness = traits[8];
        mindfulness = traits[9];
        flow = traits[10];
        competence = traits[11];

    }

    //Calculate fitness
    public void calcFitness(String userGender, int[] userValues, int [] mateValues) {
        fitness = 120; //total possible points
        if(gender.equals(userGender)){
            for (int i = 0; i < userValues.length; i++) {
                fitness = fitness - Math.abs(userValues[i] - traits[i]);
            }
        }else{
            for (int i = 0; i < userValues.length; i++) {
                fitness = fitness - Math.abs(mateValues[i] - traits[i]);
            }
        }

    }

}
