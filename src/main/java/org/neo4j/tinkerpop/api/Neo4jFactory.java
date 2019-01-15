/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.neo4j.tinkerpop.api;

import java.util.Map;


public interface Neo4jFactory {

    Neo4jGraphAPI newGraphDatabase(String path, Map<String, String> config);

    class Builder {
        public static Neo4jGraphAPI open(String path, Map<String, String> config) {
            try {
                Neo4jFactory factory = (Neo4jFactory) Class.forName("org.neo4j.tinkerpop.api.impl.Neo4jFactoryImpl").newInstance();
                return factory.newGraphDatabase(path, config);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException("Error instantiating Neo4j Database for "+path,e);
            }
        }
    }
}
