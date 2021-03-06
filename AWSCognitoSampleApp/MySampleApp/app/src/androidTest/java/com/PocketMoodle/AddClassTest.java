package com.PocketMoodle;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddClassTest {

    //Start at sign in activity (skip the splash activity)
    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void addClassTest(){
        //Add some wait time. Precaution for travis
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Signing in
        ViewInteraction editText = onView(
                allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText2.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());

        //Some more wait time so that the message that appears after sign in disappears
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Open the navigation bar
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        //Click on "Add Class" (to get to the add class fragment)
        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Add Class"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        //Add the default class (COEN 341) as a TA
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radioButton), withText("I am a TA"), isDisplayed()));
        appCompatRadioButton.perform(click());

        //Enter password (moodle)
        ViewInteraction editText4 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(android.R.id.custom),
                                withParent(withClassName(is("android.widget.FrameLayout"))))),
                        isDisplayed()));
        editText4.perform(replaceText("moodle"), closeSoftKeyboard());

        //Click OK
        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton.perform(click());

        appCompatButton = onView(
                allOf(withId(R.id.button), withText("Submit"), isDisplayed()));
        appCompatButton.perform(click());

        //Go back to the home page
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home Page"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        //Check that the class has been added
        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("COEN 341"),
                        isDisplayed()));
        appCompatTextView2.check(matches(isDisplayed()));
        appCompatTextView2.check(matches(withText("COEN 341")));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        //Drop the class
        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Remove Class"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.remove_class_button), withText("Remove Class"), isDisplayed()));
        appCompatButton2.perform(click());

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
