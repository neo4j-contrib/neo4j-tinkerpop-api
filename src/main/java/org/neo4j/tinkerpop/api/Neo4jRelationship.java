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

import org.neo4j.graphdb.Relationship;

/**
 * @author mh
 * @since 25.03.15
 */
public class Neo4jRelationship extends Neo4jEntity<Relationship> {

    public Neo4jRelationship(Relationship rel) {
        super(rel);
    }

    public String type() { return entity.getType().name(); }
    public Neo4jNode start() { return new Neo4jNode(entity.getStartNode()); }
    public Neo4jNode end() { return new Neo4jNode(entity.getEndNode()); }
    public Neo4jNode other(Neo4jNode node) { return new Neo4jNode(entity.getOtherNode(node.entity)); }
}
