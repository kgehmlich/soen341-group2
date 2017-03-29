package com.PocketMoodle;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.PocketMoodle.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationHeaderTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void navigationHeaderTest() {
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3596403);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction editText = onView(
allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText.perform(replaceText("mewt"), closeSoftKeyboard());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(5000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction editText2 = onView(
allOf(withId(R.id.signIn_editText_email), withText("mewt"), isDisplayed()));
        editText2.perform(replaceText("mewtrandell"), closeSoftKeyboard());
        
        ViewInteraction editText3 = onView(
allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText3.perform(replaceText("mewtrandell"), closeSoftKeyboard());
        
        ViewInteraction imageButton = onView(
allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(3515862);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction appCompatImageButton = onView(
allOf(withContentDescription("Open"),
withParent(withId(R.id.nav_action)),
isDisplayed()));
        appCompatImageButton.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(R.id.navigation_email), withText("momo_lebest@hotmail.fr"),
childAtPosition(
childAtPosition(
withId(R.id.navigation_header_container),
0),
2),
isDisplayed()));
        textView.check(matches(withText("momo_lebest@hotmail.fr")));
        
        ViewInteraction textView2 = onView(
allOf(withId(R.id.navigation_username), withText("mewtrandell"),
childAtPosition(
childAtPosition(
withId(R.id.navigation_header_container),
0),
1),
isDisplayed()));
        textView2.check(matches(withText("mewtrandell")));
        
        ViewInteraction imageButton2 = onView(
allOf(withId(R.id.imageButton1),
childAtPosition(
childAtPosition(
withId(R.id.navigation_header_container),
0),
0),
isDisplayed()));
        imageButton2.check(matches(isDisplayed()));
        
        ViewInteraction imageButton3 = onView(
allOf(withId(R.id.imageButton1),
childAtPosition(
childAtPosition(
withId(R.id.navigation_header_container),
0),
0),
isDisplayed()));
        imageButton3.check(matches(isDisplayed()));
        
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
