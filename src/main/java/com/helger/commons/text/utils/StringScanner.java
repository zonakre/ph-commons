/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.commons.text.utils;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Simple string scanner.
 *
 * @author Philip Helger
 * @deprecated Use {@link com.helger.commons.string.utils.StringScanner}
 *             instead.
 */
@Deprecated
@NotThreadSafe
public final class StringScanner extends com.helger.commons.string.utils.StringScanner
{
  public StringScanner (@Nonnull final String sInput)
  {
    super (sInput);
  }
}
