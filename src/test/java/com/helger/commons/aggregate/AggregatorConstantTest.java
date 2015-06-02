/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.commons.aggregate;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.helger.commons.collections.CollectionHelper;
import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link AggregatorConstant}.
 *
 * @author Philip Helger
 */
public final class AggregatorConstantTest
{
  @Test
  public void testAll ()
  {
    final AggregatorConstant <String, String> a1 = new AggregatorConstant <String, String> ("foo");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a1, new AggregatorConstant <String, String> ("foo"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a1,
                                                                     new AggregatorConstant <String, String> ("bar"));

    assertEquals ("foo", a1.aggregate (CollectionHelper.newList ("a", "b")));
    assertEquals ("foo", a1.aggregate (new ArrayList <String> ()));
  }
}
