/*
 * Copyright 2023 The Fury authors
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fury;

import static org.testng.Assert.assertEquals;

import io.fury.Fury;
import io.fury.FuryTestBase;
import io.fury.Language;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OutOfBandTest extends FuryTestBase {

  @DataProvider(name = "referenceTrackingConfig")
  public static Object[][] referenceTrackingConfig() {
    return new Object[][] {{false}, {true}};
  }

  @Test(dataProvider = "referenceTrackingConfig")
  public void testByteBuffer(boolean referenceTracking) {
    Fury.FuryBuilder builder =
        Fury.builder()
            .withLanguage(Language.JAVA)
            .withReferenceTracking(referenceTracking)
            .disableSecureMode();
    Fury fury1 = builder.build();
    Fury fury2 = builder.build();
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    byteBuffer.putInt(100);
    byteBuffer.rewind();
    AtomicInteger counter = new AtomicInteger(0);
    assertEquals(serDeOutOfBand(counter, fury1, fury1, byteBuffer), byteBuffer);
    assertEquals(serDeOutOfBand(counter, fury1, fury2, byteBuffer), byteBuffer);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    assertEquals(serDeOutOfBand(counter, fury1, fury1, byteBuffer), byteBuffer);
    assertEquals(serDeOutOfBand(counter, fury1, fury2, byteBuffer), byteBuffer);
  }
}