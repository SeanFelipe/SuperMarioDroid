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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MarioSpec02 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void functional() {
        ViewInteraction textView = onView(
            allOf(withId(R.id.welcome), withText("Welcome to Super Mario Droid"), withContentDescription("welcome"),
                childAtPosition(
                    allOf(withId(R.id.main_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    0),
                isDisplayed()));
        textView.check(matches(withText("Welcome to Super Mario Droid")));

        ViewInteraction textView2 = onView(
            allOf(withText("Press mushroom to begin"), withContentDescription("start_text"),
                childAtPosition(
                    allOf(withId(R.id.main_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0)),
                    2),
                isDisplayed()));
        textView2.check(matches(withText("Press mushroom to begin")));
    }


    @Test
    public void simpler() {
        ViewInteraction textView = onView(allOf(withId(R.id.welcome)));
        textView.check(matches(withText("Welcome to Super Mario Droid")));
    }

    @Test
    public void simplerFail() {
        ViewInteraction textView = onView(allOf(withId(R.id.welcome)));
        textView.check(matches(withText("the horror")));
    }

    @Test
    public void verifyIntent() {
        ViewInteraction textView = onView(allOf(withId(R.id.welcome)));
        textView.check(matches(withText("Welcome to Super Mario Droid")));
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
