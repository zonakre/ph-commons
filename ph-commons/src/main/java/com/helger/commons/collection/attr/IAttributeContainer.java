/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.commons.collection.attr;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.CGlobal;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.ICommonsCollection;
import com.helger.commons.collection.ext.ICommonsIterable;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.lang.GenericReflection;
import com.helger.commons.typeconvert.TypeConverter;

/**
 * Base interface for a generic read-only attribute container. It maps keys to
 * values.
 *
 * @author Philip Helger
 * @param <KEYTYPE>
 *        Key type
 * @param <VALUETYPE>
 *        Value type
 */
public interface IAttributeContainer <KEYTYPE, VALUETYPE> extends ICommonsIterable <Map.Entry <KEYTYPE, VALUETYPE>>
{
  /**
   * @return The number of contained attributes. Always &ge; 0.
   */
  @Nonnegative
  int getAttributeCount ();

  /**
   * @return <code>true</code> if this attribute container does not contain any
   *         attribute at all, <code>false</code> if at least one attribute is
   *         contained.
   */
  boolean isEmpty ();

  default boolean isNotEmpty ()
  {
    return !isEmpty ();
  }

  /**
   * Check if an attribute of the given name is contained.
   *
   * @param aName
   *        name of the attribute to check
   * @return <code>true</code> if the attribute is contained, <code>false</code>
   *         otherwise
   */
  boolean containsAttribute (@Nullable KEYTYPE aName);

  /**
   * @return The non-<code>null</code> map with all contained attributes.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsMap <KEYTYPE, VALUETYPE> getAllAttributes ();

  /**
   * Perform an operation on all contained attributes. Use this method only for
   * read-only operations, other you will most likely end up in a
   * ConcurrentModificationException!
   *
   * @param aConsumer
   *        The consumer to be invoked.
   */
  void forAllAttributes (@Nonnull final BiConsumer <? super KEYTYPE, ? super VALUETYPE> aConsumer);

  /**
   * Perform an operation on all contained attribute values. Use this method
   * only for read-only operations, other you will most likely end up in a
   * ConcurrentModificationException!
   *
   * @param aConsumer
   *        The consumer to be invoked.
   */
  void forAllAttributeValues (@Nonnull final Consumer <? super VALUETYPE> aConsumer);

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return <code>null</code> if no such value exists
   */
  @Nullable
  VALUETYPE getAttributeObject (@Nullable KEYTYPE aName);

  /**
   * Get the attribute value associated to the given attribute name. If the type
   * of the attribute in the scope does not match, a
   * {@link java.lang.ClassCastException} is thrown! If you just want to
   * retrieve a String, use {@link #getAttributeAsString(Object)}!<br>
   * This call is identical to the call
   * <code>getCastedAttribute(aName, null)</code>
   *
   * @param <DATATYPE>
   *        return type
   * @param aName
   *        the attribute name
   * @return <code>null</code> if no such value exists
   */
  @Nullable
  default <DATATYPE> DATATYPE getCastedAttribute (@Nullable final KEYTYPE aName)
  {
    return GenericReflection.uncheckedCast (getAttributeObject (aName));
  }

  /**
   * Get the attribute value associated to the given attribute name. If the type
   * of the attribute in the scope does not match, a
   * {@link java.lang.ClassCastException} is thrown! If you just want to
   * retrieve a String, use {@link #getAttributeAsString(Object, String)}!
   *
   * @param <DATATYPE>
   *        return type
   * @param aName
   *        The attribute name.
   * @param aDefault
   *        The default value to be returned if no such attribute exists
   * @return The passed default value if no such attribute exists
   */
  @Nullable
  default <DATATYPE> DATATYPE getCastedAttribute (@Nullable final KEYTYPE aName, @Nullable final DATATYPE aDefault)
  {
    final DATATYPE aValue = this.<DATATYPE> getCastedAttribute (aName);
    return aValue == null ? aDefault : aValue;
  }

  /**
   * Get the attribute value associated to the given attribute name. If the type
   * of the attribute in the scope does not match, the TypeConverter is invoked!
   * If you just want to retrieve a String, use
   * {@link #getAttributeAsString(Object)}!<br>
   * This call is identical to the call
   * <code>getTypedAttribute(aName, null)</code>
   *
   * @param <DATATYPE>
   *        return type
   * @param aName
   *        the attribute name
   * @param aDstClass
   *        The destination class to convert to. May not be <code>null</code>.
   * @return <code>null</code> if no such value exists
   */
  @Nullable
  default <DATATYPE> DATATYPE getTypedAttribute (@Nullable final KEYTYPE aName,
                                                 @Nonnull final Class <DATATYPE> aDstClass)
  {
    return TypeConverter.convertIfNecessary (getAttributeObject (aName), aDstClass);
  }

  /**
   * Get the attribute value associated to the given attribute name. If the type
   * of the attribute in the scope does not match, the TypeConverter is invoked!
   * If you just want to retrieve a String, use
   * {@link #getAttributeAsString(Object, String)}!<br>
   *
   * @param <DATATYPE>
   *        return type
   * @param aName
   *        The attribute name.
   * @param aDstClass
   *        The destination class to convert to. May not be <code>null</code>.
   * @param aDefault
   *        The default value to be returned if no such attribute exists
   * @return The passed default value if no such attribute exists
   */
  @Nullable
  default <DATATYPE> DATATYPE getTypedAttribute (@Nullable final KEYTYPE aName,
                                                 @Nonnull final Class <DATATYPE> aDstClass,
                                                 @Nullable final DATATYPE aDefault)
  {
    final DATATYPE aValue = this.<DATATYPE> getTypedAttribute (aName, aDstClass);
    return aValue == null ? aDefault : aValue;
  }

  /**
   * Get the attribute value associated to the given attribute name.<br>
   * This is a shortcut for the following call:
   * <code>getAttributeAsString (aName, null)</code>
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or <code>null</code> if no such attribute
   *         exists
   */
  @Nullable
  default String getAttributeAsString (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsString (aName, null);
  }

  /**
   * Get the attribute value associated to the given attribute name or the
   * passed default value if no such attribute is present.
   *
   * @param aName
   *        the attribute name
   * @param sDefault
   *        The default value to be returned, if the passed attribute name is
   *        unknown
   * @return The attribute value or <code>sDefault</code> if no such attribute
   *         exists
   */
  @Nullable
  default String getAttributeAsString (@Nullable final KEYTYPE aName, @Nullable final String sDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsString (aName, aValue, sDefault);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or
   *         {@link com.helger.commons.CGlobal#ILLEGAL_UINT} if no such
   *         attribute exists
   */
  default int getAttributeAsInt (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsInt (aName, CGlobal.ILLEGAL_UINT);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @param nDefault
   *        the default value to be returned if the value is not present or not
   *        convertible
   * @return The attribute value or <code>nDefault</code> if no such attribute
   *         exists
   */
  default int getAttributeAsInt (@Nullable final KEYTYPE aName, final int nDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsInt (aName, aValue, nDefault);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or
   *         {@link com.helger.commons.CGlobal#ILLEGAL_ULONG} if no such
   *         attribute exists
   */
  default long getAttributeAsLong (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsLong (aName, CGlobal.ILLEGAL_ULONG);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @param nDefault
   *        the default value to be returned if the value is not present or not
   *        convertible
   * @return The attribute value or <code>nDefault</code> if no such attribute
   *         exists
   */
  default long getAttributeAsLong (@Nullable final KEYTYPE aName, final long nDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsLong (aName, aValue, nDefault);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or
   *         {@link com.helger.commons.CGlobal#ILLEGAL_UINT} if no such
   *         attribute exists
   */
  default double getAttributeAsDouble (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsDouble (aName, CGlobal.ILLEGAL_UINT);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @param dDefault
   *        the default value to be returned if the value is not present or not
   *        convertible
   * @return The attribute value or <code>nDefault</code> if no such attribute
   *         exists
   */
  default double getAttributeAsDouble (@Nullable final KEYTYPE aName, final double dDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsDouble (aName, aValue, dDefault);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or <code>false</code> if no such attribute
   *         exists
   */
  default boolean getAttributeAsBoolean (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsBoolean (aName, false);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @param bDefault
   *        the default value to be returned if the value is not present or not
   *        convertible
   * @return The attribute value or <code>bDefault</code> if no such attribute
   *         exists
   */
  default boolean getAttributeAsBoolean (@Nullable final KEYTYPE aName, final boolean bDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsBoolean (aName, aValue, bDefault);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or <code>null</code> if no such attribute
   *         exists
   */
  @Nullable
  default BigInteger getAttributeAsBigInteger (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsBigInteger (aName, null);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @param aDefault
   *        the default value to be returned if the value is not present or not
   *        convertible
   * @return The attribute value or <code>aDefault</code> if no such attribute
   *         exists
   */
  @Nullable
  default BigInteger getAttributeAsBigInteger (@Nullable final KEYTYPE aName, @Nullable final BigInteger aDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsBigInteger (aName, aValue, aDefault);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @return The attribute value or <code>null</code> if no such attribute
   *         exists
   */
  @Nullable
  default BigDecimal getAttributeAsBigDecimal (@Nullable final KEYTYPE aName)
  {
    return getAttributeAsBigDecimal (aName, null);
  }

  /**
   * Get the attribute value associated to the given attribute name.
   *
   * @param aName
   *        the attribute name
   * @param aDefault
   *        the default value to be returned if the value is not present or not
   *        convertible
   * @return The attribute value or <code>aDefault</code> if no such attribute
   *         exists
   */
  @Nullable
  default BigDecimal getAttributeAsBigDecimal (@Nullable final KEYTYPE aName, @Nullable final BigDecimal aDefault)
  {
    final Object aValue = getAttributeObject (aName);
    return AttributeValueConverter.getAsBigDecimal (aName, aValue, aDefault);
  }

  /**
   * @return A non-<code>null</code> set of all attribute names.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsSet <KEYTYPE> getAllAttributeNames ();

  /**
   * @return A non-<code>null</code> collection of all attribute values.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsCollection <VALUETYPE> getAllAttributeValues ();

  /**
   * @return An iterator over all entries.
   */
  @Nonnull
  Iterator <Map.Entry <KEYTYPE, VALUETYPE>> iterator ();
}
