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
import static android.support.test.espresso.action.ViewActions.scrollTo;
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
public class AddDiscussionTest {

    //Start at sign in activity (skip the splash activity)
    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void addDiscussionTest() {

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

        //Adding a class to access discussion board
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
                allOf(withId(R.id.design_menu_item_text), withText("Home Page"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("COEN 341"),
                        isDisplayed()));
        appCompatTextView.perform(click());

        //Adding a new discussion group
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.openDiscussionBoard), withText("Open Discussion Board"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.add_discussion_group), withText("+"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText3.perform(replaceText("Discussion"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Add")));
        appCompatButton4.perform(scrollTo(), click());

        //Check that the discussion group has been added
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Discussion"),
                        isDisplayed()));
        textView.check(matches(withText("Discussion")));

        //Remove the discussion group
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.remove_discussion_group), withText("-"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), isDisplayed()));
                appCompatTextView4.perform(click());

        //This next line is to select the discussion group from the spinner
        onView(withText("Discussion")).inRoot(isPlatformPopup()).perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Remove")));
        appCompatButton6.perform(scrollTo(), click());

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
