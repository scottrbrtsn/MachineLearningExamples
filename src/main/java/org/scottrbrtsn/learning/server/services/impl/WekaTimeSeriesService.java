package org.scottrbrtsn.learning.server.services.impl;

import org.scottrbrtsn.learning.server.services.IWekaTimeSeriesService;
import org.springframework.stereotype.Service;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class WekaTimeSeriesService implements IWekaTimeSeriesService {


    /**
     * TimeSeries data typically is from a single unit or user
     * So, use the historical data to predict future data.
     * This example uses wine.arff, the Australian wine sales from
     * c. 1980-1995 and then predicts 12 months of sales for the 2 types ("Fortified, Dry-White")
     *
     * Attributes:
     *
     * @attribute Fortified numeric
     * @attribute Dry-white numeric
     * @attribute Sweet-white numeric
     * @attribute Red numeric
     * @attribute Rose numeric
     * @attribute Sparkling numeric
     * @attribute Date date 'yyyy-MM-dd'
     * @return a list predicted sales for the forecasted fields
     */
    public List<List<Double>>  runTimeSeriesExample(){
        List<List<Double>> toReturn = new ArrayList<>();

        try {
            // path to the Australian wine data included with the time series forecasting
            // package
            String pathToWineData = weka.core.WekaPackageManager.PACKAGES_DIR.toString()
                    + File.separator + "timeseriesForecasting" + File.separator + "sample-data"
                    + File.separator + "wine.arff";

            // load the wine data
            Instances wine = new Instances(new BufferedReader(new FileReader(pathToWineData)));

            // new forecaster
            WekaForecaster forecaster = new WekaForecaster();

            // set the targets we want to forecast. This method calls
            // setFieldsToLag() on the lag maker object for us
            forecaster.setFieldsToForecast("Fortified,Dry-white");

            // default underlying classifier is SMOreg (SVM) - we'll use
            // gaussian processes for regression instead
            forecaster.setBaseForecaster(new GaussianProcesses());

            forecaster.getTSLagMaker().setTimeStampField("Date"); // date time stamp
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12); // monthly data

            // add a month of the year indicator field
            forecaster.getTSLagMaker().setAddMonthOfYear(true);

            // add a quarter of the year indicator field
            forecaster.getTSLagMaker().setAddQuarterOfYear(true);

            // build the model
            forecaster.buildForecaster(wine, System.out);

            // prime the forecaster with enough recent historical data
            // to cover up to the maximum lag. In our case, we could just supply
            // the 12 most recent historical instances, as this covers our maximum
            // lag period
            forecaster.primeForecaster(wine);

            // forecast for 12 units (months) beyond the end of the
            // training data
            List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);

            // output the predictions. Outer list is over the steps; inner list is over
            // the targets
            double counter = 1.0;
            for (int i = 0; i < 12; i++) {
                List<NumericPrediction> predsAtStep = forecast.get(i);
                List<Double> set = new ArrayList<>();
                set.add(counter);
                for (int j = 0; j < 2; j++) {
                    NumericPrediction predForTarget = predsAtStep.get(j);
                    set.add(predForTarget.predicted());
                }
                toReturn.add(set);
                counter ++;
            }

            // we can continue to use the trained forecaster for further forecasting
            // by priming with the most recent historical data (as it becomes available).
            // At some stage it becomes prudent to re-build the model using current
            // historical data.

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return toReturn;
    }
}
