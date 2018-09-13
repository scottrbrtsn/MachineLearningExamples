# Weka Machine Learning Server: Example

## Description
This is a sample project to demonstrate the Weka Machine Learning libraries. 

## To Run The Java App
@requires
- Java
- Maven
- Postgres
- Must be run on a Raspbery Pi
- mvn spring-boot:run

## Current API
localhost:8080/weka/classifier
- runs a classifier example

localhost:8080/weka/timeSeries
- runs an example timeseries prediction 
- based on 15 years of monthly data, predicts the next 12 months of wine sales for Fortified, and Dry-White wines

localhost:8080/geneticAlgorithm/run
- Runs an example for a genetic algorithm where the 'genes' are replaced with actual 'traits'
- This is an experimental idea leveraged from : http://github.com/scottrbrtsn/cognitive-health-toolshed

## Python

@requires
Jupyter notebook

`brew install jupyter`

TODO: Verify data file structure configuration for examples.  
