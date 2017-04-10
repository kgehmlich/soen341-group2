package com.PocketMoodle;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationHeaderTest {

    //Start at sign in activity (skip the splash activity)
    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void navigationHeaderTest() {

        //Add some wait time. Precaution for travis
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Signing in
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText2.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText3.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());

        //Some more wait time so that the message that appears after sign in disappears
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Open navigation bar
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        //Verify that email is displayed properly
        ViewInteraction textView = onView(
                allOf(withId(R.id.navigation_email), withText("momo_lebest@hotmail.fr"),
                        isDisplayed()));
        textView.check(matches(withText("momo_lebest@hotmail.fr")));

        //Verify that the username is displayed properly
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.navigation_username), withText("mewtrandell"),
                        isDisplayed()));
        textView2.check(matches(withText("mewtrandell")));

        //Verify that the image is there
        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.imageButton1),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

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
