package zHelper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;

import ir.androidpower.ziosprogressbar.R;


/**
 * Created by zabih on 2020-03-10.
 */
public class ZIosProgressBar extends FrameLayout {
  protected final String TAG = ZIosProgressBar.class.getSimpleName();
  protected final Handler handler = new Handler();
  protected final int def_numberOfBars = 12;
  protected final int def_tintColor;
  //
  protected final AttributeSet attrs;
  protected final int defStyleAttr;
  //
  protected ArrayList<IndicatorBarView> arrBars;
  protected float radius = Utils.dpToPx(16);//
  protected int numberOfBars = def_numberOfBars;
  protected boolean isAnimating;
  protected int currentFrame;
  protected Runnable playFrameRunnable;
  //
  protected int tintColor;
  protected @Nullable Drawable barDrawable;
  protected long animationDuration = 1000;


  public ZIosProgressBar(Context context) {
    this(context, null);
  }


  public ZIosProgressBar(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.zIosProgressBarStyle);
  }


  public ZIosProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.attrs = attrs;
    this.defStyleAttr = defStyleAttr;
    this.def_tintColor = getResources().getColor(R.color.colorAccent);
    this.tintColor = def_tintColor;
    init();
  }


  public ZIosProgressBar(Context context, float radius) {
    this(context);
    setRadius(radius);
  }


  protected void init() {

    if (attrs != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZIosProgressBar, defStyleAttr, 0);
      tintColor = typedArray.getColor(R.styleable.ZIosProgressBar_barColor, def_tintColor);
      barDrawable = typedArray.getDrawable(R.styleable.ZIosProgressBar_barDrawable);
      typedArray.recycle();
    }


    removeAllViews();
    initViews();
    initLayouts();
    addViews();
    spreadBars();
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int www1 = getMeasuredWidth();
    int www2 = getWidth();
    //
    int hhh1 = getMeasuredHeight();
    int hhh2 = getHeight();
    //
    radius = Math.min(www1, hhh1);
    radius = radius / 2f;
    initLayouts();
    spreadBars();
  }


  protected void initViews() {
    arrBars = new ArrayList<>();

    for (int i = 0; i < numberOfBars; i++) {
      IndicatorBarView bar = new IndicatorBarView(getContext());
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        bar.setId(View.generateViewId());
      }
      bar.setBarColor(tintColor);
      arrBars.add(bar);
    }
  }


  protected Size getBarSize() {
    int barHeight = (int) (radius / 2);
    int barWith = (int) (barHeight / 3.5f);
    return new Size(barWith, barHeight);
  }


  protected void initLayouts() {
    int size = (int) (radius * 2);
    setMinimumWidth(size);
    setMinimumHeight(size);

    Size barSize = getBarSize();
    int barWith = barSize.getWidth();
    int barHeight = barSize.getHeight();
    int marginTop = -(barHeight + barHeight / 2);
    String jjj = "";

    for (int i = 0; i < arrBars.size(); i++) {
      IndicatorBarView bar = arrBars.get(i);

      if (barDrawable != null) {
        bar.setBarDrawable(barDrawable);
      } else {
        float barRadius = barSize.getWidth() / 2f;
        bar.setBarCornerRadius(barRadius);//update bar radius
      }

      FrameLayout.LayoutParams barLayoutParams = new FrameLayout.LayoutParams(barWith, barHeight);
      barLayoutParams.topMargin = marginTop;
      barLayoutParams.gravity = Gravity.CENTER;

      bar.setLayoutParams(barLayoutParams);
    }

    invalidate();
    requestLayout();
  }


  protected void addViews() {
    for (int i = 0; i < arrBars.size(); i++) {
      IndicatorBarView bar = arrBars.get(i);
      addView(bar);
    }
  }


  protected void spreadBars() {
    int degrees = 0;
//    int step = 360 / 12;
    int step = 360 / numberOfBars;

    for (int i = 0; i < arrBars.size(); i++) {
      IndicatorBarView bar = arrBars.get(i);
      rotateBar(bar, degrees);
      degrees += step;
    }
  }


  protected void rotateBar(IndicatorBarView bar, float degrees) {
    RotateAnimation animation = new RotateAnimation(0, degrees, radius / 15f, radius);
//    animation.setDuration(500); // pretty nice anim on init view
    animation.setDuration(0);
    animation.setFillAfter(true);

    bar.setAnimation(animation);
    animation.start();
  }


  public void setRadius(float radiusDp) {
    this.radius = Utils.dpToPx((int) radiusDp);
    init();
  }


  public void setTintColor(int tintColor) {
    this.tintColor = tintColor;
    init();
  }


  public void setTintColorRes(int tintColorRes) {
    setTintColor(getResources().getColor(tintColorRes));
  }


  public void startAnimating() {
    setAlpha(1.0f);

    isAnimating = true;

    playFrameRunnable = new Runnable() {
      @Override
      public void run() {
        playFrame();
      }
    };

    // recursive function until isAnimating is false
    playFrame();
  }


  public void stopAnimating() {
    isAnimating = false;
    setAlpha(0.0f);
    invalidate();
    playFrameRunnable = null;
  }


  //get delay base on animationDuration
  protected long getHandlerDelay() {
    return animationDuration / 12;
  }


  public void setAnimationDuration(long durationMillis) {
    this.animationDuration = durationMillis;
  }


  protected void playFrame() {
    if (isAnimating) {
      resetAllBarAlpha();
      updateFrame();

      handler.postDelayed(playFrameRunnable, getHandlerDelay());
    }
  }


  protected void updateFrame() {
    if (isAnimating) {
      showFrame(currentFrame);
      currentFrame += 1;

      if (currentFrame > numberOfBars - 1) {
        currentFrame = 0;
      }
    }
  }


  protected void resetAllBarAlpha() {
    for (int i = 0; i < arrBars.size(); i++) {
      IndicatorBarView bar = arrBars.get(i);
      bar.setAlpha(0.5f);
    }
  }


  protected void showFrame(int frameNumber) {
    int[] indexes = getFrameIndexesForFrameNumber(frameNumber);
    gradientColorBarSets(indexes);
  }


  protected int[] getFrameIndexesForFrameNumber(int frameNumber) {
    int length = numberOfBars / 3;
    int[] indexes = new int[length];

    for (int i = 0; i < length; i++) {
      if (i == 0) {
        indexes[i] = frameNumber;
      } else {
        int before = indexes[i - 1];
        int nnn = before - 1;
        if (nnn < 0) {
          nnn = nnn + numberOfBars;
        }
        indexes[i] = nnn;
      }
    }

//    StringBuilder builder = new StringBuilder();
//    for (int index : indexes) {
//      builder.append(index).append(", ");
//    }
//    Log.i(TAG, "--- jjj indexes = "+builder.toString());

    return indexes;

//    if (frameNumber == 0) {
//      return indexesFromNumbers(0, 11, 10, 9);
//    } else if (frameNumber == 1) {
//      return indexesFromNumbers(1, 0, 11, 10);
//    } else if (frameNumber == 2) {
//      return indexesFromNumbers(2, 1, 0, 11);
//    } else if (frameNumber == 3) {
//      return indexesFromNumbers(3, 2, 1, 0);
//    } else if (frameNumber == 4) {
//      return indexesFromNumbers(4, 3, 2, 1);
//    } else if (frameNumber == 5) {
//      return indexesFromNumbers(5, 4, 3, 2);
//    } else if (frameNumber == 6) {
//      return indexesFromNumbers(6, 5, 4, 3);
//    } else if (frameNumber == 7) {
//      return indexesFromNumbers(7, 6, 5, 4);
//    } else if (frameNumber == 8) {
//      return indexesFromNumbers(8, 7, 6, 5);
//    } else if (frameNumber == 9) {
//      return indexesFromNumbers(9, 8, 7, 6);
//    } else if (frameNumber == 10) {
//      return indexesFromNumbers(10, 9, 8, 7);
//    } else {
//      return indexesFromNumbers(11, 10, 9, 8);
//    }
  }


  protected int[] indexesFromNumbers(int i1, int i2, int i3, int i4) {
    int[] indexes = {i1, i2, i3, i4};
    return indexes;
  }


  protected void gradientColorBarSets(int[] indexes) {
    float alpha = 1.0f;

    for (int i = 0; i < indexes.length; i++) {
      int barIndex = indexes[i];
      IndicatorBarView barView = arrBars.get(barIndex);
      barView.setAlpha(alpha);
      alpha -= 0.125f;
    }

    invalidate();
  }
}
