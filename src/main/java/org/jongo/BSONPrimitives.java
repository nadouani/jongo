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

package org.jongo;

import com.mongodb.DBObject;
import com.mongodb.DBRefBase;
import org.bson.types.*;

import java.util.*;
import java.util.regex.Pattern;

public class BSONPrimitives {

    private static final Set<Class<?>> PRIMITIVES;

    static {
        PRIMITIVES = new HashSet<Class<?>>();
        PRIMITIVES.add(String.class);
        PRIMITIVES.add(Number.class);
        PRIMITIVES.add(Boolean.class);
        PRIMITIVES.add(MinKey.class);
        PRIMITIVES.add(MaxKey.class);
        PRIMITIVES.add(ObjectId.class);
        PRIMITIVES.add(Pattern.class);
        PRIMITIVES.add(BSONTimestamp.class);
        PRIMITIVES.add(Date.class);
        PRIMITIVES.add(UUID.class);
        PRIMITIVES.add(Code.class);
        PRIMITIVES.add(DBObject.class);
        PRIMITIVES.add(DBRefBase.class);
        PRIMITIVES.add(CodeWScope.class);
        PRIMITIVES.add(byte[].class);
        PRIMITIVES.add(Binary.class);
    }

    public static Set<Class<?>> getPrimitives() {
        return Collections.unmodifiableSet(PRIMITIVES);
    }
}
