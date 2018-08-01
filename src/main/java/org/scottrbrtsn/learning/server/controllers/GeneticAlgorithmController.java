package org.scottrbrtsn.learning.server.controllers;

import org.apache.log4j.Logger;
import org.scottrbrtsn.learning.server.services.IGeneticAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/geneticAlgorithm")
public class GeneticAlgorithmController {

    private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithmController.class.getName());

    @Autowired
    IGeneticAlgorithm geneticAlgorithm;

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public ResponseEntity<String> runGeneticAlgorithm() {
        LOGGER.debug("runClassifier");
        return new ResponseEntity<>(geneticAlgorithm.runGeneticAlgorithm(), new HttpHeaders(), HttpStatus.OK);
    }

}
