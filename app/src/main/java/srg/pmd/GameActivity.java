package srg.pmd;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends Activity {

    RelativeLayout baseLayout;
    ImageView marioSmall;
    ImageView pipe;
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

        baseLayout = findViewById(R.id.base_layout);
        pipe = findViewById(R.id.pipe);
        pipe.setImageDrawable(getDrawable(R.drawable.pipe));
        float pipeHeight = pipe.getDrawable().getIntrinsicHeight();

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
