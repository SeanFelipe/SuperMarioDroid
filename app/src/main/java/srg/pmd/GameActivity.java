package srg.pmd;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends Activity {

    RelativeLayout baseLayout;
    ImageView marioSmall;
    ImageView pipe;
    Typeface marioFont;
    TextView scoreDesc;
    TextView score;
    Point screenSize;
    int centerx, centery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);

        marioFont = Typeface.createFromAsset(getAssets(),  "fonts/mario_font.ttf");
        baseLayout = findViewById(R.id.base_layout);

        pipe = findViewById(R.id.pipe);
        pipe.setImageDrawable(getDrawable(R.drawable.pipe));
        float pipeHeight = pipe.getDrawable().getIntrinsicHeight();

        scoreDesc = findViewById(R.id.scoreDesc);
        scoreDesc.setTypeface(marioFont);
        scoreDesc.setTextSize(24);
        scoreDesc.setText("SCORE");
        scoreDesc.measure(0,0);
        float scoreDescX = screenSize.x - scoreDesc.getMeasuredWidth() - 100;
        scoreDesc.setX(scoreDescX);
        scoreDesc.setY(50);

        score = findViewById(R.id.score);
        score.setTypeface(marioFont);
        score.setTextSize(30);
        score.setText("0");
        score.measure(0,0);
        float scoreX = scoreDescX + ( scoreDesc.getMeasuredWidth() / 2 ) - score.getMeasuredWidth() / 2;
        score.setX(scoreX);
        score.setY(150);

        marioSmall = new ImageView(this);
        marioSmall.setImageDrawable(getDrawable(R.drawable.mario_small));
        float mariox = screenSize.x / 2 - (marioSmall.getDrawable().getIntrinsicWidth() / 2);
        float marioy = screenSize.y - marioSmall.getDrawable().getIntrinsicHeight() - pipeHeight;
        marioSmall.setX(mariox);
        marioSmall.setY(marioy);

        baseLayout.addView(marioSmall);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
