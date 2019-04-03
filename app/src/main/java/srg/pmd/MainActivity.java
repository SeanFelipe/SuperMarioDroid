package srg.pmd;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    final String TAG = "MarioMainActivity";

    Point screenSize;
    RelativeLayout layout;
    Typeface marioFont;
    TextView welcomeText;
    TextView startText;
    ImageView mushroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);

        setupScreen();
        setupGraphics();
    }


    private void setupScreen() {
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        marioFont = Typeface.createFromAsset(getAssets(),  "fonts/mario_font.ttf");
        welcomeText = ( TextView ) findViewById(R.id.welcome);
        welcomeText.setContentDescription("welcome");
        welcomeText.setTypeface(marioFont);
        welcomeText.setTextSize(44);
        welcomeText.setText("Welcome to Super Mario Droid");


        layout = findViewById(R.id.main_layout);
    }

    private void setupGraphics() {
        mushroom = new ImageView(this);
        mushroom.setContentDescription("mushroom_main");
        mushroom.setImageDrawable(getDrawable(R.drawable.mushroom_green));
        float mushroomx = screenSize.x / 2 - (mushroom.getDrawable().getIntrinsicWidth() / 2);
        float ypad = 0;
        float mushroomy = screenSize.y - mushroom.getDrawable().getIntrinsicHeight() - ypad;
        mushroom.setX(mushroomx);
        mushroom.setY(mushroomy);
//        mushroom.setX(50);
//        mushroom.setY(50);
//        mushroom.setOnClickListener(new View.OnClickListener() {

        layout.addView(mushroom);

        startText = new TextView(this);
        startText.setContentDescription("start_text");
        startText.setTypeface(marioFont);
        startText.setTextSize(18);
        startText.setText("Press mushroom to begin");
        startText.measure(0,0);
        startText.setTextColor(Color.WHITE);
        Log.d(TAG, "startText GMW: " + startText.getMeasuredWidth());
        float startTextX = ( screenSize.x - startText.getMeasuredWidth() ) / 2;
        float startTextY = screenSize.y - mushroom.getDrawable().getIntrinsicHeight() - 100;
        startText.setX(startTextX);
        startText.setY(startTextY);

        Log.d(TAG, "startText: " + startText.toString());

        layout.addView(startText);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
