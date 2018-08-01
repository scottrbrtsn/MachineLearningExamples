package org.scottrbrtsn.learning.server.controllers;

import org.scottrbrtsn.learning.server.services.impl.A3CALEService;
import org.scottrbrtsn.learning.server.services.impl.WekaClassifierService;
import org.scottrbrtsn.learning.server.services.impl.WekaTimeSeriesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/learn")
public class LearningController {

    private static final Logger LOGGER = Logger.getLogger(LearningController.class.getName());

    @Autowired
    WekaClassifierService wekaClassifierServiceService;

    @Autowired
    WekaTimeSeriesService wekaTimeSeriesService;

    @Autowired
    A3CALEService a3CALEService;

    @RequestMapping(value = "/classifier", method = RequestMethod.GET)
    public ResponseEntity<double[]> runClassifier() throws Exception{
        LOGGER.debug("runClassifier");
        return new ResponseEntity<>(wekaClassifierServiceService.runClassifierExample(), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/timeSeries", method = RequestMethod.GET)
    public ResponseEntity<List<List<Double>>> runTimeSeries(){
        LOGGER.debug("runTimeseries");
        return new ResponseEntity<>(wekaTimeSeriesService.runTimeSeriesExample(), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/a3cale", method = RequestMethod.GET)
    public ResponseEntity<String> runA3cale(){
        LOGGER.debug("runA3cale");
        return new ResponseEntity<>(a3CALEService.runA3CALE(), new HttpHeaders(), HttpStatus.OK);
    }

}