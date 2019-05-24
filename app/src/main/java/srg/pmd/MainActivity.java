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
import android.graphics.Color;


public class MainActivity extends Activity {

    final String TAG = "MarioActivity23May";
    final int CLOCK_TICK_MS = 100;
    final float FADE_INCREMENT = 0.075f;

    Point screenSize;
    RelativeLayout layout;
    Typeface marioFont;
    TextView welcomeText;
    TextView startText;
    ImageView mushroom;
    float startTextAlpha;
    int startTextColor;
    boolean alphaDescreasing;

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
        clockTick();
    }

    public Typeface getMarioFont() {
        return marioFont;
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

        alphaDescreasing = true;
        startTextAlpha = 1f;

        startText = new TextView(this);
        startTextColor = Color.argb(startTextAlpha, 1f, 1f, 1f);
        startText.setContentDescription("start_text");
        startText.setTypeface(marioFont);
        startText.setTextSize(18);
        startText.setTextColor(startTextColor);
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

       private void clockTick() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.d(TAG, "clockTick");
                        update();
                        clockTick();
                    }
                },
                CLOCK_TICK_MS);
    };

    private void update() {

        if ( startTextAlpha <= ( 0 + FADE_INCREMENT ) ) {
            alphaDescreasing = false;
        } else if ( startTextAlpha >= ( 1 - FADE_INCREMENT ) ) {
            alphaDescreasing = true;
        }

        if ( alphaDescreasing ) {
            startTextAlpha -= FADE_INCREMENT;
        } else {
            startTextAlpha += FADE_INCREMENT;
        }

        int newColor = Color.argb(startTextAlpha, 1f, 1f, 1f);
        startText.setTextColor(newColor);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
