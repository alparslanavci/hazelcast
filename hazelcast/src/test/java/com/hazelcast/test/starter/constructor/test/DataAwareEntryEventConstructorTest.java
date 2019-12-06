/*
 * Copyright (c) 2008-2019, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.test.starter.constructor.test;

import com.hazelcast.core.EntryEventType;
import com.hazelcast.cluster.Member;
import com.hazelcast.internal.serialization.impl.DefaultSerializationServiceBuilder;
import com.hazelcast.map.impl.DataAwareEntryEvent;
import com.hazelcast.internal.serialization.Data;
import com.hazelcast.internal.serialization.SerializationService;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.annotation.ParallelJVMTest;
import com.hazelcast.test.annotation.QuickTest;
import com.hazelcast.test.starter.constructor.DataAwareEntryEventConstructor;
import com.hazelcast.internal.util.UuidUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelJVMTest.class})
public class DataAwareEntryEventConstructorTest {

    @Test
    public void testConstructor() {
        SerializationService serializationService = new DefaultSerializationServiceBuilder().build();
        String key = "key";
        String newValue = "newValue";
        String oldValue = "oldValue";
        String mergingValue = "mergingValue";

        Member from = mock(Member.class);
        int eventType = EntryEventType.MERGED.getType();
        String source = UuidUtil.newUnsecureUuidString();
        Data dataKey = serializationService.toData(key);
        Data dataNewValue = serializationService.toData(newValue);
        Data dataOldValue = serializationService.toData(oldValue);
        Data dataMergingValue = serializationService.toData(mergingValue);

        DataAwareEntryEvent dataAwareEntryEvent = new DataAwareEntryEvent(from, eventType, source, dataKey, dataNewValue,
                dataOldValue, dataMergingValue, serializationService);

        DataAwareEntryEventConstructor constructor = new DataAwareEntryEventConstructor(DataAwareEntryEvent.class);
        DataAwareEntryEvent clonedDataAwareEntryEvent = (DataAwareEntryEvent) constructor.createNew(dataAwareEntryEvent);

        assertEquals(dataAwareEntryEvent.getName(), clonedDataAwareEntryEvent.getName());
        assertEquals(dataAwareEntryEvent.getMember(), clonedDataAwareEntryEvent.getMember());
        assertEquals(dataAwareEntryEvent.getEventType(), clonedDataAwareEntryEvent.getEventType());
        assertEquals(dataAwareEntryEvent.getSource(), clonedDataAwareEntryEvent.getSource());
        assertEquals(dataAwareEntryEvent.getKey(), clonedDataAwareEntryEvent.getKey());
        assertEquals(dataAwareEntryEvent.getKeyData(), clonedDataAwareEntryEvent.getKeyData());
        assertEquals(dataAwareEntryEvent.getValue(), clonedDataAwareEntryEvent.getValue());
        assertEquals(dataAwareEntryEvent.getNewValueData(), clonedDataAwareEntryEvent.getNewValueData());
        assertEquals(dataAwareEntryEvent.getOldValue(), clonedDataAwareEntryEvent.getOldValue());
        assertEquals(dataAwareEntryEvent.getOldValueData(), clonedDataAwareEntryEvent.getOldValueData());
        assertEquals(dataAwareEntryEvent.getMergingValue(), clonedDataAwareEntryEvent.getMergingValue());
        assertEquals(dataAwareEntryEvent.getMergingValueData(), clonedDataAwareEntryEvent.getMergingValueData());
    }
}
