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
package com.helger.commons.name;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.compare.AbstractCollatingComparator;

/**
 * This is a collation {@link java.util.Comparator} for objects that implement
 * the {@link IHasName} interface.
 *
 * @author Philip Helger
 * @param <DATATYPE>
 *        The type of elements to be compared.
 */
@NotThreadSafe
public class CollatingComparatorHasName <DATATYPE extends IHasName> extends AbstractCollatingComparator <DATATYPE>
{
  public CollatingComparatorHasName (@Nullable final Locale aSortLocale)
  {
    super (aSortLocale);
  }

  @Override
  protected String getPart (@Nonnull final DATATYPE aObject)
  {
    return aObject.getName ();
  }
}