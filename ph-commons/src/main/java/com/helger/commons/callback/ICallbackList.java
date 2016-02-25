/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
package com.helger.commons.callback;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.state.EContinue;

/**
 * Read-only interface for a list of {@link ICallback} objects.<br>
 * Note: Does not implement Iterable because the returned iterator would either
 * be an Iterator over the list in which case you can use
 * {@link #getAllCallbacks()} directly or the returned Iterator would not be
 * thread-safe and that is not an option for this type.
 *
 * @author Philip Helger
 * @param <CALLBACKTYPE>
 *        The callback type.
 */
public interface ICallbackList <CALLBACKTYPE extends ICallback> extends Serializable
{
  /**
   * @return A list of all callbacks. Never <code>null</code> and only
   *         containing non-<code>null</code> elements.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsList <CALLBACKTYPE> getAllCallbacks ();

  /**
   * Get the callback at the specified index.
   *
   * @param nIndex
   *        The index to be retrieved. Should be &ge; 0.
   * @return <code>null</code> if the provided index is invalid.
   */
  @Nullable
  CALLBACKTYPE getCallbackAtIndex (@Nonnegative int nIndex);

  /**
   * @return The number of contained callbacks. Always &ge; 0.
   */
  @Nonnegative
  int getCallbackCount ();

  /**
   * @return <code>true</code> if at least a single callback is present,
   *         <code>false</code> otherwise.
   */
  boolean hasCallbacks ();

  /**
   * Invoke all registered callbacks in a safe manner.
   *
   * @param aConsumer
   *        The action to be performed with the respective callback. This method
   *        cannot return a value. May not be <code>null</code>.
   */
  void forEach (@Nonnull Consumer <CALLBACKTYPE> aConsumer);

  /**
   * Invoke all registered callbacks in a safe manner.
   *
   * @param aFunction
   *        The action to be performed with the respective callback. The first
   *        callback that returns {@link EContinue#BREAK} stops the process. May
   *        not be <code>null</code>.
   * @return {@link EContinue#CONTINUE} if all callbacks returned
   *         {@link EContinue#CONTINUE}, {@link EContinue#BREAK} if at least one
   *         invocation returned {@link EContinue#BREAK}.
   */
  @Nonnull
  EContinue forEachWithReturn (@Nonnull Function <CALLBACKTYPE, EContinue> aFunction);
}
