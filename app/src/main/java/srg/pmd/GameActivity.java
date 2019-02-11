package srg.pmd;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends Activity {

    final String TAG = "MarioActivity";
    final int CLOCK_TICK_MS = 100;
    float MARIO_BOUND_Y;
    float MARIO_START_Y, MARIO_START_Y_REVERSED;
    float marioIncrementY;

    float ANIMATION_TOTAL_TIME = 400;
    float JUMP_DISTANCE;
    float lastEasingValue;
    boolean jumpIncrementedScore = false;

    final int COIN_BOX_OFFSET = 700;

    RelativeLayout baseLayout;
    ImageView marioSmall;
    ImageView pipe;
    ImageView coinBox;
    Typeface marioFont;
    TextView scoreDesc;
    int score = 0;
    TextView scoreText;
    Point screenSize;

    int animationElapsedTime = 0;

    float marioy;
    boolean marioJumping = false;
    String marioDirection = "up";

    TimerTask clockTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        setupGraphics();
        clockTick();
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
        scoreDesc.setTextSize(30);
        scoreDesc.setText("SCORE");
        scoreDesc.measure(0,0);
        float scoreDescX = screenSize.x - scoreDesc.getMeasuredWidth() - 100;
        scoreDesc.setX(scoreDescX);
        scoreDesc.setY(50);

        scoreText = findViewById(R.id.score);
        scoreText.setTypeface(marioFont);
        scoreText.setTextSize(44);
        scoreText.setText(Integer.toString(score));
        scoreText.measure(0,0);
        float scoreX = scoreDescX + ( scoreDesc.getMeasuredWidth() / 2 ) - scoreText.getMeasuredWidth() / 2;
        scoreText.setX(scoreX);
        scoreText.setY(150);

        marioSmall = new ImageView(this);
        marioSmall.setContentDescription("mario_small");
        marioSmall.setImageDrawable(getDrawable(R.drawable.mario_small));
        float mariox = screenSize.x / 2 - (marioSmall.getDrawable().getIntrinsicWidth() / 2);
        MARIO_START_Y = screenSize.y - marioSmall.getDrawable().getIntrinsicHeight() - pipeHeight;
        marioy = MARIO_START_Y;
        marioSmall.setX(mariox);
        marioSmall.setY(marioy);
        marioSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mario clicked");
                beginMarioJump();
            }
        });

        baseLayout.addView(marioSmall);

        coinBox = new ImageView(this);
        coinBox.setImageDrawable(getDrawable(R.drawable.coin_box));
        float coinx = screenSize.x / 2 - (coinBox.getDrawable().getIntrinsicWidth() / 2);
        float coiny =  screenSize.y - marioSmall.getDrawable().getIntrinsicHeight() - pipeHeight - COIN_BOX_OFFSET;
        coinBox.setX(coinx);
        coinBox.setY(coiny);
        coinBox.measure(0,0);
        baseLayout.addView(coinBox);

        MARIO_BOUND_Y = COIN_BOX_OFFSET + coinBox.getMeasuredHeight();
        JUMP_DISTANCE = MARIO_START_Y - MARIO_BOUND_Y ;
        Log.d(TAG, "JUMP_DISTANCE: " + JUMP_DISTANCE);
        MARIO_START_Y_REVERSED = screenSize.y - MARIO_START_Y;
        Log.d(TAG, "MARIO_START_Y_REVERSED: " + MARIO_START_Y_REVERSED);
     }

    private void clockTick() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
//                        Log.d(TAG, "clockTick");
                        updateGame();
                        clockTick();
                    }
                },
                CLOCK_TICK_MS);
    };

    private void updateGame() {
//        Log.d(TAG, "updateGame(): jumping: " + marioJumping);
        if ( marioJumping ) {
            animationElapsedTime += CLOCK_TICK_MS;
            marioJumping = continueMarioJump();
        }
    }

    private void beginMarioJump() {
        animationElapsedTime = 0;
        marioJumping = true;
        lastEasingValue = 0;
        jumpIncrementedScore = false;
    }

    private boolean continueMarioJump() {
        Log.d(TAG, "continueMarioJump() at time:" + animationElapsedTime);
        boolean stillJumping = true;

        float marioEasingY = Easing.easeOut(
            animationElapsedTime, MARIO_START_Y_REVERSED, JUMP_DISTANCE, ANIMATION_TOTAL_TIME
        );

        Log.d(TAG, "lastEasingValue:: " + lastEasingValue);
        Log.d(TAG, "easing returned: " + marioEasingY);

        if ( marioEasingY < lastEasingValue) {
            if ( ! jumpIncrementedScore ) {
                incrementScore();
            }
        }

        lastEasingValue = marioEasingY;

        marioy = screenSize.y - marioEasingY;
        marioSmall.setY(marioy);

        // easing is returning NaN when complete
        if ( marioEasingY != marioEasingY ) {
            marioSmall.setY(MARIO_START_Y);
            stillJumping = false;
        }

        /*
        if ( marioDirection == "up") {
            boolean didWeHitTopBound = ( marioy - MARIO_INCREMENT ) < MARIO_BOUND_Y;
            if ( ! didWeHitTopBound ) {
                marioy -= MARIO_INCREMENT;
                marioSmall.setY(marioy);
            } else {
                incrementScore();
                marioDirection = "down";
            }
        } else if ( marioDirection == "down" ) {
            boolean didWeHitBottomBound = ( marioy + MARIO_INCREMENT ) >= MARIO_START_Y;
            if ( ! didWeHitBottomBound ) {
                marioy += MARIO_INCREMENT;
                marioSmall.setY(marioy);
            } else {
                marioDirection = "up";
                marioy = MARIO_START_Y;
                marioSmall.setY(marioy);
                stillJumping = false;
            }
        }
        */

        /*
        Log.d(TAG, "marioy: " + marioy);
        Log.d(TAG, "MARIO_BOUND_Y: " + MARIO_BOUND_Y);
        Log.d(TAG, "didWeHitTopBound: " + didWeHitTopBound);
        */

        return stillJumping;
    }

    private void incrementScore() {
        jumpIncrementedScore = true;
        score = score + 1;
        scoreText.setText(Integer.toString(score));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
