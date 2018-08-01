package org.scottrbrtsn.learning.server.ras;

import weka.core.Instances;

public interface IInstanceDBClient {

    Instances getInstancesfromDB()throws Exception;

}
