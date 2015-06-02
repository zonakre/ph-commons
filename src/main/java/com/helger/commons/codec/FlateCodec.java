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
package com.helger.commons.codec;

import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.streams.NonBlockingByteArrayInputStream;
import com.helger.commons.io.streams.NonBlockingByteArrayOutputStream;
import com.helger.commons.io.streams.StreamHelper;

/**
 * Encoder and decoder for flate compression
 *
 * @author Philip Helger
 */
public class FlateCodec extends AbstractByteArrayCodec
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FlateCodec.class);

  public FlateCodec ()
  {}

  public static boolean isZlibHead (@Nonnull final byte [] buf)
  {
    if (buf.length >= 2)
    {
      final int b0 = buf[0] & 0xff;
      final int b1 = buf[1] & 0xff;

      if ((b0 & 0xf) == 8)
        if ((b0 >> 4) + 8 <= 15)
          if ((((b0 << 8) + b1) % 31) == 0)
            return true;
    }
    return false;
  }

  @Nullable
  public static byte [] getDecodedFlate (@Nullable final byte [] aEncodedBuffer)
  {
    if (aEncodedBuffer == null)
      return null;

    if (!isZlibHead (aEncodedBuffer))
      s_aLogger.warn ("ZLib header not found");

    final InflaterInputStream aDecodeIS = new InflaterInputStream (new NonBlockingByteArrayInputStream (aEncodedBuffer));
    final NonBlockingByteArrayOutputStream aBAOS = new NonBlockingByteArrayOutputStream ();
    try
    {
      if (StreamHelper.copyInputStreamToOutputStream (aDecodeIS, aBAOS).isFailure ())
        throw new DecodeException ("Failed to flate decode!");
      return aBAOS.toByteArray ();
    }
    finally
    {
      StreamHelper.close (aBAOS);
    }
  }

  @Nullable
  public byte [] getDecoded (@Nullable final byte [] aEncodedBuffer)
  {
    return getDecodedFlate (aEncodedBuffer);
  }

  @Nullable
  public static byte [] getEncodedFlate (@Nullable final byte [] aBuffer)
  {
    if (aBuffer == null)
      return null;

    final NonBlockingByteArrayOutputStream aBAOS = new NonBlockingByteArrayOutputStream ();
    final DeflaterOutputStream aEncodeOS = new DeflaterOutputStream (aBAOS);
    if (StreamHelper.copyInputStreamToOutputStreamAndCloseOS (new NonBlockingByteArrayInputStream (aBuffer), aEncodeOS)
                   .isFailure ())
      throw new EncodeException ("Failed to flate encode!");
    return aBAOS.toByteArray ();
  }

  @Nullable
  public byte [] getEncoded (@Nullable final byte [] aBuffer)
  {
    return getEncodedFlate (aBuffer);
  }
}
