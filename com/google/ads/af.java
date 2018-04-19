package com.google.ads;

import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.lang.ref.WeakReference;

public class af
  implements Runnable
{
  private WeakReference<d> a;

  public af(d paramd)
  {
    this.a = new WeakReference(paramd);
  }

  public void run()
  {
    d locald = (d)this.a.get();
    if (locald == null)
    {
      b.a("The ad must be gone, so cancelling the refresh timer.");
      return;
    }
    locald.A();
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.ads.af
 * JD-Core Version:    0.6.0
 */