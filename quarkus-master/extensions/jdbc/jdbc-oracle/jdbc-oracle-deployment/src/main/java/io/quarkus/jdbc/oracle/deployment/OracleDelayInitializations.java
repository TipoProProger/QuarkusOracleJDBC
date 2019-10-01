/*
 * Copyright 2019 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.quarkus.jdbc.oracle.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.substrate.RuntimeInitializedClassBuildItem;
import io.quarkus.deployment.builditem.substrate.SubstrateResourceBuildItem;

/**
 *
 * @author mvolkov
 */
public class OracleDelayInitializations {

    @BuildStep
    RuntimeInitializedClassBuildItem oraclejdbcDriverInitialization() {
        return new RuntimeInitializedClassBuildItem(oracle.jdbc.driver.OracleDriver.class.getCanonicalName());
    }

    @BuildStep
    RuntimeInitializedClassBuildItem oracleDriverInitialization() {
        return new RuntimeInitializedClassBuildItem(oracle.jdbc.OracleDriver.class.getCanonicalName());
    }

    @BuildStep
    RuntimeInitializedClassBuildItem driverInitialization() {
        return new RuntimeInitializedClassBuildItem(java.sql.DriverManager.class.getCanonicalName());
    }

    @BuildStep
    RuntimeInitializedClassBuildItem oracleTimeoutThreadPerVMInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.jdbc.driver.OracleTimeoutThreadPerVM");
    }

    @BuildStep
    RuntimeInitializedClassBuildItem LnxLibServerInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.sql.LnxLibServer");
    }

    @BuildStep
    RuntimeInitializedClassBuildItem loadCorejavaInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.sql.LoadCorejava");
    }

    @BuildStep
    RuntimeInitializedClassBuildItem timeoutHandlerTypeInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.net.nt.TimeoutInterruptHandler");
    }

    /*
     * @BuildStep
     * RuntimeInitializedClassBuildItem t4ConnectionInitialization() {
     * return new RuntimeInitializedClassBuildItem("oracle.jdbc.driver.T4CConnection");
     * }
     */

    @BuildStep
    RuntimeInitializedClassBuildItem blockSourceInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.jdbc.driver.BlockSource");
    }

    @BuildStep
    RuntimeInitializedClassBuildItem blockSourceThreadedInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.jdbc.driver.BlockSource$ThreadedCachingBlockSource");
    }

    @BuildStep
    RuntimeInitializedClassBuildItem blockSourceReleaserThreadedInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.jdbc.driver.BlockSource$ThreadedCachingBlockSource$BlockReleaser");
    }

    @BuildStep
    SubstrateResourceBuildItem xmlTypeInitialization() {
        return new SubstrateResourceBuildItem("oracle.xdb.XMLType");
    }

    @BuildStep
    RuntimeInitializedClassBuildItem nlsrTypeInitialization() {
        return new RuntimeInitializedClassBuildItem("oracle.net.jdbc.nl.mesg.NLSR");
    }

}