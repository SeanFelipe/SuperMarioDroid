package srg.pmd;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    final String TAG = "MarioMainActivity";

    Point screenSize;
    RelativeLayout baseLayout;
    Typeface marioFont;
    TextView welcomeText;
    ImageView mushroom;
    ImageView marioSmall;

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
        welcomeText.setContentDescription("score");
        welcomeText.setTypeface(marioFont);
        welcomeText.setTextSize(44);
        welcomeText.setText("Welcome to Super Mario Droid");

        baseLayout = findViewById(R.id.base_layout);
    }

    private void setupGraphics() {
        mushroom = new ImageView(this);
        mushroom.setContentDescription("mushroom_main");
        mushroom.setImageDrawable(getDrawable(R.drawable.mushroom));
        float mushroomx = screenSize.x / 2 - (mushroom.getDrawable().getIntrinsicWidth() / 2);
        float ypad = 0;
        float mushroomy = screenSize.y - mushroom.getDrawable().getIntrinsicHeight() - ypad;
        mushroom.setX(mushroomx);
        mushroom.setY(mushroomy);
//        mushroom.setOnClickListener(new View.OnClickListener() {
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
