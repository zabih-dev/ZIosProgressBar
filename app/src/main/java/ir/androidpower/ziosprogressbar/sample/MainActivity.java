package ir.androidpower.ziosprogressbar.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zHelper.view.ZIosProgressBar;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ZIosProgressBar progressBar = findViewById(R.id.progressBar);
    progressBar.startAnimating();

    ZIosProgressBar progressBar2 = findViewById(R.id.progressBar2);
    progressBar2.startAnimating();

  }
}
