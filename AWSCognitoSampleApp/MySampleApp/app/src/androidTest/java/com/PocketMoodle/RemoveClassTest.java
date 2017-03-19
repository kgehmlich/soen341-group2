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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RemoveClassTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void removeClassTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText2.perform(replaceText("mewtrandell"), closeSoftKeyboard());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Add Class"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radioButton), withText("I am a TA"), isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button), withText("Submit"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Remove Class"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.remove_class_button), withText("Remove Class"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.nav_action)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home Page"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction listView = onView(
                allOf(withId(R.id.TAclassList),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        0),
                                4),
                        isDisplayed()));
        listView.check(doesNotExist());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("COEN 341"),
                        childAtPosition(
                                withId(R.id.TAclassList),
                                0),
                        isDisplayed()));
        appCompatTextView4.check(doesNotExist());



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
