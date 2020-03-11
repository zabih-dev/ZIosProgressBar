package zHelper.view;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;


class Utils {
  private static Utils instance;
  public Context context;

  private Utils() {}

  public synchronized static Utils getInstance()
  {
    if(instance == null) {
      instance = new Utils();
    }
    return instance;
  }



  public static int pxToDp(int px) {
    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
  }

  public static int dpToPx(int dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }


  public static ShapeDrawable roundedCornerRectOutlineWithColor(int color, float cornerRadius,
                                                                float strokeWidth)
  {
    float[] radii = new float[] {
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius
    };

    RoundRectShape roundedCornerShape = new RoundRectShape(radii, null, null);

    ShapeDrawable shape = new ShapeDrawable();
    shape.getPaint().setColor(color);
    shape.setShape(roundedCornerShape);
    shape.getPaint().setStrokeWidth(strokeWidth);
    shape.getPaint().setStyle(Paint.Style.STROKE);

    return shape;
  }

  public static ShapeDrawable roundedCornerRectWithColor(int color, float cornerRadius)
  {
    float[] radii = new float[] {
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius,
      cornerRadius, cornerRadius
    };

    RoundRectShape roundedCornerShape = new RoundRectShape(radii, null, null);

    ShapeDrawable shape = new ShapeDrawable();
    shape.getPaint().setColor(color);
    shape.setShape(roundedCornerShape);

    return shape;
  }

  public static ShapeDrawable roundedCornerRectWithColor(int color, float topLeftRadius, float
    topRightRadius, float bottomRightRadius, float bottomLeftRadius)
  {
    float[] radii = new float[] {
      topLeftRadius, topLeftRadius,
      topRightRadius, topRightRadius,
      bottomRightRadius, bottomRightRadius,
      bottomLeftRadius, bottomLeftRadius
    };

    RoundRectShape roundedCornerShape = new RoundRectShape(radii, null, null);

    ShapeDrawable shape = new ShapeDrawable();
    shape.getPaint().setColor(color);
    shape.setShape(roundedCornerShape);

    return shape;
  }

  public static int getScreenWidth()
  {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
  }

  public static int getScreenHeight()
  {
    return Resources.getSystem().getDisplayMetrics().heightPixels;
  }

  public static int getScreenOrientation(Context context)
  {
    return context.getResources().getConfiguration().orientation;
  }

  public static boolean isLandscapeOrientation(Context context)
  {
    return getScreenOrientation(context) == Configuration.ORIENTATION_LANDSCAPE;
  }

}
