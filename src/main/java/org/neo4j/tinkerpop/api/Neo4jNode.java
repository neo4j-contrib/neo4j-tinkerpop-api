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

import java.util.Set;

public interface Neo4jNode extends Neo4jEntity {
    Set<String> labels();
    boolean hasLabel(String label);
    void addLabel(String label);
    void removeLabel(String label);

    int degree(Neo4jDirection direction, String type);
    Iterable<Neo4jRelationship> relationships(Neo4jDirection direction, String...types);
    Neo4jRelationship connectTo(Neo4jNode node, String type);
}
