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

package io.fury.format.encoder;

import io.fury.test.bean.BeanA;
import io.fury.test.bean.BeanB;
import io.fury.test.bean.Foo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.testng.Assert.assertTrue;

public class CodecBuilderTest {
  @Test
  public void genCode() {
    new RowEncoderBuilder(Foo.class).genCode();
    new RowEncoderBuilder(BeanA.class).genCode();
    new RowEncoderBuilder(BeanB.class).genCode();
  }

  @Test
  public void loadOrGenRowCodecClass() {
    assertTrue(RowEncoder.class.isAssignableFrom(Encoders.loadOrGenRowCodecClass(BeanA.class)));
    assertTrue(RowEncoder.class.isAssignableFrom(Encoders.loadOrGenRowCodecClass(BeanB.class)));
    assertTrue(RowEncoder.class.isAssignableFrom(Encoders.loadOrGenRowCodecClass(AtomicLong.class)));
  }
}