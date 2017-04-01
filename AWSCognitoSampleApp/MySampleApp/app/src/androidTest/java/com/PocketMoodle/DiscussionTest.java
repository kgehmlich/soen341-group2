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
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DiscussionTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void discussionTest() {
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(15000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        

        
        ViewInteraction editText2 = onView(
allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText2.perform(replaceText("mewtrandell"), closeSoftKeyboard());
        
        ViewInteraction editText3 = onView(
allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText3.perform(replaceText("mewtrandell"), closeSoftKeyboard());
        
        ViewInteraction imageButton = onView(
allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());

        try {
            Thread.sleep(15000);
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
allOf(withId(R.id.design_menu_item_text), withText("Home Page"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());
        
        ViewInteraction appCompatTextView = onView(
allOf(withId(android.R.id.text1), withText("COEN 341"),
childAtPosition(
withId(R.id.TAclassList),
0),
isDisplayed()));
        appCompatTextView.perform(click());
        
        ViewInteraction appCompatButton2 = onView(
allOf(withId(R.id.openDiscussionBoard), withText("Open Discussion Board"), isDisplayed()));
        appCompatButton2.perform(click());
        
        ViewInteraction appCompatButton3 = onView(
allOf(withId(R.id.add_discussion_group), withText("+"), isDisplayed()));
        appCompatButton3.perform(click());
        
        ViewInteraction editText4 = onView(
allOf(withClassName(is("android.widget.EditText")),
withParent(allOf(withId(R.id.custom),
withParent(withId(R.id.customPanel)))),
isDisplayed()));
        editText4.perform(replaceText("DiscTest"), closeSoftKeyboard());
        
        ViewInteraction appCompatButton4 = onView(
allOf(withId(android.R.id.button1), withText("Add")));
        appCompatButton4.perform(scrollTo(), click());
        
        ViewInteraction appCompatTextView2 = onView(
allOf(withId(android.R.id.text1), withText("DiscTest"),
childAtPosition(
withId(R.id.discussion_group_list),
0),
isDisplayed()));
        appCompatTextView2.perform(click());
        
        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.message_to_send), isDisplayed()));
        appCompatEditText.perform(replaceText("hello"), closeSoftKeyboard());
        
        ViewInteraction appCompatButton5 = onView(
allOf(withId(R.id.send_message), withText("Send"), isDisplayed()));
        appCompatButton5.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(android.R.id.text1), withText(endsWith("hello")),
childAtPosition(
allOf(withId(R.id.message_list),
childAtPosition(
IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
0)),
0),
isDisplayed()));
        textView.check(matches(withText(endsWith("hello"))));
        
        pressBack();
        
        ViewInteraction appCompatButton6 = onView(
allOf(withId(R.id.remove_discussion_group), withText("-"), isDisplayed()));
        appCompatButton6.perform(click());
        
        ViewInteraction appCompatButton7 = onView(
allOf(withId(android.R.id.button1), withText("Remove")));
        appCompatButton7.perform(scrollTo(), click());
        
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
