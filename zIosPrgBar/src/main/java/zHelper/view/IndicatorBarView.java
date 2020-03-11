package zHelper.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ir.androidpower.ziosprogressbar.R;


/**
 * Created by zabih on 2020-03-10.
 */
public class IndicatorBarView extends FrameLayout {
  private float cornerRadius;
  private int barColor;
  private @Nullable Drawable barDrawable;
  private boolean isInternalSetBackground;


  public IndicatorBarView(Context context) {
    this(context, null);
  }


  public IndicatorBarView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }


  public IndicatorBarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //
    barColor = getResources().getColor(R.color.colorAccent);
    updateBar();
  }


  public IndicatorBarView(Context context, float cornerRadius) {
    this(context);
    setBarCornerRadius(cornerRadius);
  }


  @Override
  public void setBackground(Drawable background) {
    super.setBackground(background);

    if (!isInternalSetBackground) {
      this.barDrawable = background;
      updateBar();
    }
  }


  protected void updateBar() {

    if (barDrawable == null) {
      setBackgroundInternal(Utils.roundedCornerRectWithColor(barColor, cornerRadius));
    }

    setAlpha(0.5f);
  }


  protected void setBackgroundInternal(Drawable drawable) {
    isInternalSetBackground = true;
    setBackground(drawable);
    isInternalSetBackground = false;
  }


  public void setBarCornerRadius(float radius) {
    this.cornerRadius = radius;
    updateBar();
  }


  public void setBarColor(int barColor) {
    this.barColor = barColor;
    updateBar();
  }


  public void setBarColorRes(int barColorRes) {
    setBarColor(getResources().getColor(barColorRes));
  }


  public void setBarDrawable(@Nullable Drawable barDrawable) {
    setBackground(barDrawable);
  }

}

