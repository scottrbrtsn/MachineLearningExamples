package org.scottrbrtsn.learning.server.services.impl;

import org.deeplearning4j.rl4j.learning.HistoryProcessor;
import org.deeplearning4j.rl4j.learning.async.a3c.discrete.A3CDiscrete;
import org.deeplearning4j.rl4j.learning.async.a3c.discrete.A3CDiscreteConv;
import org.deeplearning4j.rl4j.mdp.ale.ALEMDP;
import org.deeplearning4j.rl4j.network.ac.ActorCriticFactoryCompGraphStdConv;
import org.deeplearning4j.rl4j.util.DataManager;
import org.nd4j.linalg.learning.config.Adam;
import org.scottrbrtsn.learning.server.services.IA3CALEService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class A3CALEService implements IA3CALEService {


    public String runA3CALE() {
       try {
           //record the training data in rl4j-data in a new folder
           DataManager manager = new DataManager(true);

           //setup the emulation environment through ALE, you will need a ROM file
           ALEMDP mdp = null;
           try {
               mdp = new ALEMDP("pong.bin");
           } catch (UnsatisfiedLinkError e) {
               System.out.println("To run this example, uncomment the \"ale-platform\" dependency in the pom.xml file.");
           }

           //setup the training
           A3CDiscreteConv<ALEMDP.GameScreen> a3c = new A3CDiscreteConv(mdp, ALE_NET_A3C, ALE_HP, ALE_A3C, manager);

           //start the training
           a3c.train();

           //save the model at the end
           a3c.getPolicy().save("ale-a3c.model");

           //close the ALE env
           mdp.close();
           return "Success";
       }catch(IOException e){
           e.printStackTrace();
           return "Failed";
       }


    }

    public static HistoryProcessor.Configuration ALE_HP =
            new HistoryProcessor.Configuration(
                    4,       //History length
                    84,      //resize width
                    110,     //resize height
                    84,      //crop width
                    84,      //crop height
                    0,       //cropping x offset
                    0,       //cropping y offset
                    4        //skip mod (one frame is picked every x
            );

    public static A3CDiscrete.A3CConfiguration ALE_A3C =
            new A3CDiscrete.A3CConfiguration(
                    123,            //Random seed
                    10000,          //Max step By epoch
                    8000000,        //Max step
                    8,              //Number of threads
                    32,             //t_max
                    500,            //num step noop warmup
                    0.1,            //reward scaling
                    0.99,           //gamma
                    10.0            //td-error clipping
            );

    public static final ActorCriticFactoryCompGraphStdConv.Configuration ALE_NET_A3C =
            new ActorCriticFactoryCompGraphStdConv.Configuration(
                    0.000,   //l2 regularization
                    new Adam(0.00025), //learning rate
                    null, false
            );
}
