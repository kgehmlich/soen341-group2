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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
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
public class SwitchClassTest {

    //Ship the splash screen
    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void switchClassTest() {

        //Sleep a bit as a precaution for travis
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Sign In
        ViewInteraction editText = onView(
                allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText2.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());

        //Sleep a bit while the sign in confirmation message dissapears
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        //Add a couple of classes (two as TA and two as student. Precondition)
        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Add Class"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radioButton), withText("I am a TA"), isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button), withText("Submit"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.add_class_spinner), isDisplayed()));
        appCompatSpinner.perform(click());


        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 346"),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button), withText("Submit"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.add_class_spinner), isDisplayed()));
        appCompatSpinner2.perform(click());



        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 348"),
                        isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.radioButton2), withText("I am a Student"), isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button), withText("Submit"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.add_class_spinner), isDisplayed()));
        appCompatSpinner3.perform(click());


        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 349"),
                        isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button), withText("Submit"), isDisplayed()));
        appCompatButton4.perform(click());

        //Return to home page
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView5 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home Page"), isDisplayed()));
        appCompatCheckedTextView5.perform(click());

        //Click on one of the classes
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("COEN 341"),
                        isDisplayed()));
        appCompatTextView.perform(click());

        //Check that the proper page is displayed
        ViewInteraction textView = onView(
                allOf(withId(R.id.setclassTitle), withText("COEN 341"),
                        childAtPosition(
                                allOf(withId(R.id.headerSection),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("COEN 341")));

        //Switch between all classes that have been added and check that the switch actually happens
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.change_class_spinner),
                        withParent(withId(R.id.headerSection)),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatCheckedTextView6 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 349"), isDisplayed()));
        appCompatCheckedTextView6.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.setclassTitle), withText("COMP 349"),
                        childAtPosition(
                                allOf(withId(R.id.headerSection),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        //Assert that we are on comp 349 page
        textView2.check(matches(withText("COMP 349")));

        //switch
        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.change_class_spinner),
                        withParent(withId(R.id.headerSection)),
                        isDisplayed()));
        appCompatSpinner5.perform(click());

        ViewInteraction appCompatCheckedTextView7 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 346"), isDisplayed()));
        appCompatCheckedTextView7.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.setclassTitle), withText("COMP 346"),
                        childAtPosition(
                                allOf(withId(R.id.headerSection),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        //Assert that we are on comp 346 page
        textView3.check(matches(withText("COMP 346")));

        //switch
        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.change_class_spinner),
                        withParent(withId(R.id.headerSection)),
                        isDisplayed()));
        appCompatSpinner6.perform(click());

        ViewInteraction appCompatCheckedTextView8 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 348"), isDisplayed()));
        appCompatCheckedTextView8.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.setclassTitle), withText("COMP 348"),
                        childAtPosition(
                                allOf(withId(R.id.headerSection),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        //Assert that we are on comp 348 page
        textView4.check(matches(withText("COMP 348")));

        //Remove all classes that have been added
        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatCheckedTextView9 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Remove Class"), isDisplayed()));
        appCompatCheckedTextView9.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.remove_class_button), withText("Remove Class"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatSpinner7 = onView(
                allOf(withId(R.id.remove_class_spinner), isDisplayed()));
        appCompatSpinner7.perform(click());

        ViewInteraction appCompatCheckedTextView10 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 349"), isDisplayed()));
        appCompatCheckedTextView10.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.remove_class_button), withText("Remove Class"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatSpinner8 = onView(
                allOf(withId(R.id.remove_class_spinner), isDisplayed()));
        appCompatSpinner8.perform(click());

        ViewInteraction appCompatCheckedTextView11 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 346"), isDisplayed()));
        appCompatCheckedTextView11.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.remove_class_button), withText("Remove Class"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatSpinner9 = onView(
                allOf(withId(R.id.remove_class_spinner), isDisplayed()));
        appCompatSpinner9.perform(click());

        ViewInteraction appCompatCheckedTextView12 = onView(
                allOf(withId(android.R.id.text1), withText("COMP 348"), isDisplayed()));
        appCompatCheckedTextView12.perform(click());

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
