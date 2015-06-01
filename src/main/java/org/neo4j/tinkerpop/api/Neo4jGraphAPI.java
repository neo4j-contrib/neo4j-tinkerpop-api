/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.neo4j.tinkerpop.api;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface Neo4jGraphAPI {

    Neo4jNode createNode(String... labels);

    Neo4jNode getNodeById(long id);

    Neo4jRelationship getRelationshipById(long id);

    void shutdown();

    Iterable<Neo4jNode> allNodes();

    Iterable<Neo4jRelationship> allRelationships();

    Iterable<Neo4jNode> findNodes(String property, Object value);

    Iterable<Neo4jNode> findNodes(String label);

    Iterable<Neo4jNode> findNodes(String label, String property, Object value);

    void awaitIndexesOnline(final long time, final TimeUnit timeUnit);

    Neo4jTx tx();

    Iterator<Map<String, Object>> execute(String query, Map<String, Object> params);

    boolean hasSchemaIndex(String label, String property);

    Iterable<String> getKeys();

    Object getProperty(String key);

    boolean hasProperty(String key);

    Object removeProperty(String key);

    void setProperty(String key, Object value);
}
