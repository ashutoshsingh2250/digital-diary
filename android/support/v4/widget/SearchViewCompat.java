package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class SearchViewCompat
{
  private static final SearchViewCompatImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new SearchViewCompatHoneycombImpl();
      return;
    }
    IMPL = new SearchViewCompatStubImpl();
  }

  private SearchViewCompat(Context paramContext)
  {
  }

  public static View newSearchView(Context paramContext)
  {
    return IMPL.newSearchView(paramContext);
  }

  public static void setOnQueryTextListener(View paramView, OnQueryTextListenerCompat paramOnQueryTextListenerCompat)
  {
    IMPL.setOnQueryTextListener(paramView, paramOnQueryTextListenerCompat.mListener);
  }

  public static abstract class OnQueryTextListenerCompat
  {
    final Object mListener = SearchViewCompat.IMPL.newOnQueryTextListener(this);

    public boolean onQueryTextChange(String paramString)
    {
      return false;
    }

    public boolean onQueryTextSubmit(String paramString)
    {
      return false;
    }
  }

  static class SearchViewCompatHoneycombImpl extends SearchViewCompat.SearchViewCompatStubImpl
  {
    public Object newOnQueryTextListener(SearchViewCompat.OnQueryTextListenerCompat paramOnQueryTextListenerCompat)
    {
      return SearchViewCompatHoneycomb.newOnQueryTextListener(new SearchViewCompatHoneycomb.OnQueryTextListenerCompatBridge(paramOnQueryTextListenerCompat)
      {
        public boolean onQueryTextChange(String paramString)
        {
          return this.val$listener.onQueryTextChange(paramString);
        }

        public boolean onQueryTextSubmit(String paramString)
        {
          return this.val$listener.onQueryTextSubmit(paramString);
        }
      });
    }

    public View newSearchView(Context paramContext)
    {
      return SearchViewCompatHoneycomb.newSearchView(paramContext);
    }

    public void setOnQueryTextListener(Object paramObject1, Object paramObject2)
    {
      SearchViewCompatHoneycomb.setOnQueryTextListener(paramObject1, paramObject2);
    }
  }

  static abstract interface SearchViewCompatImpl
  {
    public abstract Object newOnQueryTextListener(SearchViewCompat.OnQueryTextListenerCompat paramOnQueryTextListenerCompat);

    public abstract View newSearchView(Context paramContext);

    public abstract void setOnQueryTextListener(Object paramObject1, Object paramObject2);
  }

  static class SearchViewCompatStubImpl
    implements SearchViewCompat.SearchViewCompatImpl
  {
    public Object newOnQueryTextListener(SearchViewCompat.OnQueryTextListenerCompat paramOnQueryTextListenerCompat)
    {
      return null;
    }

    public View newSearchView(Context paramContext)
    {
      return null;
    }

    public void setOnQueryTextListener(Object paramObject1, Object paramObject2)
    {
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.SearchViewCompat
 * JD-Core Version:    0.6.0
 */