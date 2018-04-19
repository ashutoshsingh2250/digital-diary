package com.girnar.online_digital_diary.beans;

public class Items
{
  private String id;
  private boolean isChecked;
  private String name;
  private int visible;

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public int getVisible()
  {
    return this.visible;
  }

  public boolean isChecked()
  {
    return this.isChecked;
  }

  public void setChecked(boolean paramBoolean)
  {
    this.isChecked = paramBoolean;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setVisible(int paramInt)
  {
    this.visible = paramInt;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.beans.Items
 * JD-Core Version:    0.6.0
 */