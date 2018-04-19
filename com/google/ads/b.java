package com.google.ads;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class b
{
  private static b c = null;
  private final BigInteger a = d();
  private BigInteger b = BigInteger.ONE;

  public static b a()
  {
    monitorenter;
    try
    {
      if (c == null)
        c = new b();
      b localb = c;
      return localb;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private static byte[] a(long paramLong)
  {
    return BigInteger.valueOf(paramLong).toByteArray();
  }

  private static BigInteger d()
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      UUID localUUID = UUID.randomUUID();
      localMessageDigest.update(a(localUUID.getLeastSignificantBits()));
      localMessageDigest.update(a(localUUID.getMostSignificantBits()));
      byte[] arrayOfByte = new byte[9];
      arrayOfByte[0] = 0;
      System.arraycopy(localMessageDigest.digest(), 0, arrayOfByte, 1, 8);
      BigInteger localBigInteger = new BigInteger(arrayOfByte);
      return localBigInteger;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
    }
    throw new RuntimeException("Cannot find MD5 message digest algorithm.");
  }

  public BigInteger b()
  {
    monitorenter;
    try
    {
      BigInteger localBigInteger = this.a;
      monitorexit;
      return localBigInteger;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public BigInteger c()
  {
    monitorenter;
    try
    {
      BigInteger localBigInteger = this.b;
      this.b = this.b.add(BigInteger.ONE);
      monitorexit;
      return localBigInteger;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.ads.b
 * JD-Core Version:    0.6.0
 */