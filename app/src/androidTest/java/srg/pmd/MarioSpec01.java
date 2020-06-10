package srg.pmd;


import android.support.test.espresso.ViewInteraction;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MarioSpec01 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void marioSpec01() {
        ViewInteraction imageView = onView(
            allOf(withContentDescription("mushroom_main"),
                childAtPosition(
                    allOf(withId(R.id.main_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    2),
                isDisplayed()));
        imageView.perform(click());

        ViewInteraction imageView2 = onView(
            allOf(withContentDescription("mario_small"),
                childAtPosition(
                    allOf(withId(R.id.base_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    3),
                isDisplayed()));
        imageView2.perform(click());

        ViewInteraction imageView3 = onView(
            allOf(withContentDescription("mario_small"),
                childAtPosition(
                    allOf(withId(R.id.base_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    3),
                isDisplayed()));
        imageView3.perform(click());

        ViewInteraction imageView4 = onView(
            allOf(withContentDescription("mario_small"),
                childAtPosition(
                    allOf(withId(R.id.base_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    3),
                isDisplayed()));
        imageView4.perform(click());

        ViewInteraction imageView5 = onView(
            allOf(withContentDescription("mario_small"),
                childAtPosition(
                    allOf(withId(R.id.base_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    3),
                isDisplayed()));
        imageView5.perform(click());
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
