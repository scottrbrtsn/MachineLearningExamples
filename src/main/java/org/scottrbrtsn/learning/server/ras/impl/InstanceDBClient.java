package org.scottrbrtsn.learning.server.ras.impl;

import org.scottrbrtsn.learning.server.ras.IInstanceDBClient;
import org.springframework.stereotype.Component;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 * This class is not finished, but once data is stored in a DB,
 * a file would not need to be stored
 *
 */
@Component
public class InstanceDBClient implements IInstanceDBClient {

    @Override
    public Instances getInstancesfromDB()throws Exception{
        InstanceQuery query = new InstanceQuery();
        query.setUsername("sa");
        query.setPassword("");
        query.setQuery("select * from ");
        // You can declare that your data set is sparse
        // query.setSparseData(true);
        return query.retrieveInstances();
    }
}
