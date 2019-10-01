package io.quarkus.jdbc.oracle.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class OracleProcessor {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("oracle-jdbc");
    }

    /*
     * @BuildStep
     * 
     * @Record(STATIC_INIT)
     * MQLNTAdapter buildAdapter() {
     * return null;
     * }
     */
}
