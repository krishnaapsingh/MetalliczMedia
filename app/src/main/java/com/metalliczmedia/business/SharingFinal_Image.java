package com.metalliczmedia.business;

import android.graphics.Bitmap;

public class SharingFinal_Image
{
  static Bitmap ageingbitmap;
  static Bitmap comparisionBitmap;
  static Bitmap originalBitmap;

  public static Bitmap getCam_originalBitmap() {
    return cam_originalBitmap;
  }

  public static void setCam_originalBitmap(Bitmap cam_originalBitmap) {
    SharingFinal_Image.cam_originalBitmap = cam_originalBitmap;
  }

  static Bitmap cam_originalBitmap;

  public static Bitmap getAgeingbitmap()
  {
    return ageingbitmap;
  }

  public static Bitmap getComparisionBitmap()
  {
    return comparisionBitmap;
  }

  public static Bitmap getOriginalBitmap()
  {
	  
    return originalBitmap;
  }

  public static void setAgeingbitmap(Bitmap paramBitmap)
  {

  ageingbitmap = paramBitmap;
}

  public static void setComparisionBitmap(Bitmap paramBitmap)
  {
    comparisionBitmap = paramBitmap;
  }

  public static void setOriginalBitmap(Bitmap paramBitmap)
  {
    originalBitmap = paramBitmap;
  }
}

