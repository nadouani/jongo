/*
 * Copyright (C) 2011 Benoit GUEROUT <bguerout at gmail dot com> and Yves AMSELLEM <amsellem dot yves at gmail dot com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jongo.marshall.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.LazyWriteableDBObject;
import org.bson.LazyBSONCallback;
import org.jongo.marshall.Marshaller;
import org.jongo.marshall.MarshallingException;
import org.jongo.marshall.Unmarshaller;
import org.jongo.marshall.bson.BsonByteFactory;
import org.jongo.marshall.bson.BsonByte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BsonProcessor implements Unmarshaller, Marshaller {

    protected static final ObjectMapperFactory OBJECT_MAPPER_FACTORY = new ObjectMapperFactory();

    private final ObjectIdFieldLocator fieldLocator;
    private ObjectMapper mapper;

    public BsonProcessor() {
        this(OBJECT_MAPPER_FACTORY.createBsonMapper());
    }

    public BsonProcessor(ObjectMapper mapper) {
        this.mapper = mapper;
        this.fieldLocator = new ObjectIdFieldLocator();
    }

    public <T> T unmarshall(DBObject document, Class<T> clazz) throws MarshallingException {

        BsonByte bson = BsonByteFactory.fromDBObject(document);
        try {
            return mapper.readValue(bson.getData(), bson.getOffset(), bson.getSize(), clazz);
        } catch (IOException e) {
            String message = String.format("Unable to unmarshall result to %s from content %s", clazz, document.toString());
            throw new MarshallingException(message, e);
        }
    }

    public DBObject marshall(Object obj) throws MarshallingException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            mapper.writeValue(output, obj);
        } catch (IOException e) {
            throw new MarshallingException("Unable to marshall " + obj + " into bson", e);
        }

        return convertToDBObject(output.toByteArray());
    }

    protected DBObject convertToDBObject(byte[] bytes) {
        return new LazyWriteableDBObject(bytes, new LazyBSONCallback());
    }

    public void setDocumentGeneratedId(Object target, Object id) {
        fieldLocator.findFieldAndUpdate(target, id);
    }

}