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

import org.neo4j.graphdb.*;

import java.util.List;

/**
 * @author mh
 * @since 25.03.15
 */
public class Neo4jEntity<T extends PropertyContainer> {

    protected final T entity;

    public Neo4jEntity(T entity) {
        this.entity = entity;
    }

    public long getId() { return entity instanceof Node ? ((Node) entity).getId() : ((Relationship) entity).getId(); }
    public Iterable<String> getKeys() { return entity.getPropertyKeys(); }
    public Object getProperty(String name) { return entity.getProperty(name); }
    public Object getProperty(String name, Object defaultValue) { return entity.getProperty(name, defaultValue); }
    public void setProperty(String name, Object value)  { entity.setProperty(name, value); }
    public Object removeProperty(String name)  { return entity.removeProperty(name); }
    public boolean hasProperty(String name)  { return entity.hasProperty(name); }
    public void delete()  { if (entity instanceof Node) ((Node)entity).delete(); else ((Relationship)entity).delete(); }
}
