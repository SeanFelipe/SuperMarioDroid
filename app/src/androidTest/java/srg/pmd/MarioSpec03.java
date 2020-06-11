package srg.pmd;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.PositionAssertions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MarioSpec03 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkPosition() {
        Matcher welcomeTextMatcher = allOf(withId(R.id.welcome));
        ViewInteraction welcomeText = onView(welcomeTextMatcher);
        ViewInteraction mushroom = onView(allOf(withContentDescription("mushroom_main")));
        mushroom.check(PositionAssertions.isBelow(welcomeTextMatcher));
    }

    @Test
    public void checkPositionFail() {
        Matcher welcomeTextMatcher = allOf(withId(R.id.welcome));
        ViewInteraction welcomeText = onView(welcomeTextMatcher);
        ViewInteraction mushroom = onView(allOf(withContentDescription("mushroom_main")));
        mushroom.check(PositionAssertions.isAbove(welcomeTextMatcher));
    }

    private static Matcher<View> childAtPosition(
        final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                    && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
