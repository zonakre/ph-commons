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
package com.helger.commons.collections.iterate;

import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.UnsupportedOperation;
import com.helger.commons.collections.ArrayHelper;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * This is a small helper class for iterating over arrays of boolean.
 *
 * @author Philip Helger
 */
public final class ArrayIteratorBoolean
{
  private final boolean [] m_aArray;
  private int m_nIndex;

  public ArrayIteratorBoolean (@Nonnull final boolean... aArray)
  {
    ValueEnforcer.notNull (aArray, "Array");
    m_nIndex = 0;
    m_aArray = ArrayHelper.getCopy (aArray);
  }

  /**
   * Private constructor with offset and length
   *
   * @param aArray
   *        Source array
   * @param nOfs
   *        Offset. Must be &ge; 0.
   * @param nLength
   *        Lenght. Must be &ge; 0.
   */
  private ArrayIteratorBoolean (@Nonnull final boolean [] aArray,
                                @Nonnegative final int nOfs,
                                @Nonnegative final int nLength)
  {
    ValueEnforcer.isArrayOfsLen (aArray, nOfs, nLength);

    m_nIndex = 0;
    m_aArray = ArrayHelper.getCopy (aArray, nOfs, nLength);
  }

  public boolean hasNext ()
  {
    return m_nIndex < m_aArray.length;
  }

  public boolean next ()
  {
    if (!hasNext ())
      throw new NoSuchElementException ();
    return m_aArray[m_nIndex++];
  }

  @UnsupportedOperation
  @Deprecated
  public void remove ()
  {
    throw new UnsupportedOperationException ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ArrayIteratorBoolean rhs = (ArrayIteratorBoolean) o;
    return EqualsHelper.equals (m_aArray, rhs.m_aArray) && m_nIndex == rhs.m_nIndex;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aArray).append (m_nIndex).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("array", Arrays.toString (m_aArray))
                                       .append ("index", m_nIndex)
                                       .toString ();
  }

  @Nonnull
  public static ArrayIteratorBoolean createOfsLen (@Nonnull final boolean [] aArray,
                                                   @Nonnegative final int nOfs,
                                                   @Nonnegative final int nLength)
  {
    return new ArrayIteratorBoolean (aArray, nOfs, nLength);
  }

  @Nonnull
  public static ArrayIteratorBoolean createBeginEnd (@Nonnull final boolean [] aArray,
                                                     @Nonnegative final int nBegin,
                                                     @Nonnegative final int nEnd)
  {
    if (nEnd < nBegin)
      throw new IllegalArgumentException ("Begin (" + nBegin + ") must be between 0 and < end (" + nEnd + ")");
    return createOfsLen (aArray, nBegin, nEnd - nBegin);
  }
}
