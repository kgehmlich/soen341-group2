package com.PocketMoodle;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.PocketMoodle.Services.GetAllClass;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Created by Mewt on 2017-03-31.
 */
@RunWith(AndroidJUnit4.class)
public class GetAllClassTest {

   //Start the sign in activitiy. Required for testing the getAllClassRegisteredIn() test
    //The main reason is to sign in as the fake user who will always be registered in the same classes
   @Rule
   public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);


    @Test
    public void getListOfClass() throws Exception {
        //Create and populate actual list of classes
    List<String> expected = new ArrayList<String>();
        expected.add("COEN 341");
        expected.add("ENGR 301");
        expected.add("MARK 201");
        expected.add("BIOL 261");
        expected.add("COMP 349");
        expected.add("COMP 346");
        expected.add("ENGR 299");
        expected.add("COMP 348");

        //Check it against what the function returns
        GetAllClass listOfClass = new GetAllClass();
        List<String> output = new ArrayList<String>();
        output = listOfClass.GetListOfClass();

        assertEquals(expected,output);

    }

    @Test
    public void getAllClassRegisteredIn() throws Exception {
        //the condition to be asserted
        boolean testStatus = true;

        //Signing in as the fake user
        ViewInteraction editText = onView(
                allOf(withId(R.id.signIn_editText_email), isDisplayed()));
        editText.perform(replaceText("reallyfakeuser"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.signIn_editText_password), isDisplayed()));
        editText2.perform(replaceText("reallyfakeuser"), closeSoftKeyboard());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.signIn_imageButton_login), isDisplayed()));
        imageButton.perform(click());

        //Create and populate list of classes that the fake user will always be registered in
        List<String> expected = new ArrayList<String>();
        expected.add("ENGR 301");
        expected.add("MARK 201");
        expected.add("COMP 346");
        expected.add("COEN 341");
        expected.add("ENGR 299");


        //the output list contains the output of the function
        List<String> output = new ArrayList<String>();
        GetAllClass listOfClass = new GetAllClass();
        output = listOfClass.GetAllClassRegisteredIn();

        //for each that checks whether every element in the expected list is also in the
        // output list
        for (String className : expected) {
            if (!(output.contains(className))){
            testStatus = false;
                break;
            }
        }
        assertTrue(testStatus);
    }

    //This method will not be tested because the expected list will have to constantly change
    //since it is likely that various users might register and drop classes at random times
    /*@Test
    public void getAllUsersInAClass() throws Exception {

    }*/

}