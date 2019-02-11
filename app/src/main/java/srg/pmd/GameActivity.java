package srg.pmd;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
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

    final String TAG = "MarioActivity";

    RelativeLayout baseLayout;
    ImageView marioSmall;
    ImageView pipe;
    Typeface marioFont;
    TextView scoreDesc;
    int score = 0;
    TextView scoreText;
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

        setupGraphics();
    }

    private void setupGraphics() {
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

        scoreText = findViewById(R.id.score);
        scoreText.setTypeface(marioFont);
        scoreText.setTextSize(30);
        scoreText.setText(Integer.toString(score));
        scoreText.measure(0,0);
        float scoreX = scoreDescX + ( scoreDesc.getMeasuredWidth() / 2 ) - scoreText.getMeasuredWidth() / 2;
        scoreText.setX(scoreX);
        scoreText.setY(150);

        marioSmall = new ImageView(this);
        marioSmall.setImageDrawable(getDrawable(R.drawable.mario_small));
        float mariox = screenSize.x / 2 - (marioSmall.getDrawable().getIntrinsicWidth() / 2);
        float marioy = screenSize.y - marioSmall.getDrawable().getIntrinsicHeight() - pipeHeight;
        marioSmall.setX(mariox);
        marioSmall.setY(marioy);
        marioSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "marioClicked()");
                int newScore = score++;
                scoreText.setText(Integer.toString(newScore));
            }
        });

        baseLayout.addView(marioSmall);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
