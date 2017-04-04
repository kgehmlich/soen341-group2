//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.14
//
package com.PocketMoodle;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.PocketMoodle.util.JWTUtils;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /** Class name for log messages. */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** Bundle key for saving/restoring the toolbar title. */
    private static final String BUNDLE_KEY_TOOLBAR_TITLE = "title";

    /** The identity manager used to keep track of the current user account. */
    private IdentityManager identityManager;

    /** The helper class used to toggle the left navigation drawer open and closed. */
    private ActionBarDrawerToggle drawerToggle;

    /** Data to be passed between fragments. */
    private Bundle fragmentBundle;

    private Button   signOutButton;

    // for navigation bar
    private DrawerLayout drawerLayout;

    // for navigation bar
    private ActionBarDrawerToggle toggle;

    // for navigation bar
    private Toolbar toolbar;

    private FragmentTransaction fragmentTransaction;

    private NavigationView navigationView;

    ImageView imageView;
    Button button;

    private static final int IMAGE_UPLOAD_REQUEST=42;
    Uri imageUri;
    private ImageButton imgButton;
    String path;
    public final static String APP_PATH_SD_CARD = "/DesiredSubfolderName/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";



    /**
     * Initializes the Toolbar for use with the activity.
     */
    /*
    private void setupToolbar(final Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set up the activity to use this toolbar. As a side effect this sets the Toolbar's title
        // to the activity's title.
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            // Some IDEs such as Android Studio complain about possible NPE without this check.
            assert getSupportActionBar() != null;

            // Restore the Toolbar's title.
            getSupportActionBar().setTitle(
                savedInstanceState.getCharSequence(BUNDLE_KEY_TOOLBAR_TITLE));
        }
    }
*/

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the mobile client. It is created in the Application class.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();

        setContentView(R.layout.activity_main);

        //setupToolbar(savedInstanceState);

        //setupNavigationMenu(savedInstanceState);
        toolbar  =(Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new HomeFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);

        //Initializing a view for the header of the navigation bar
        View navigationHeaderView = navigationView.getHeaderView(0);


        //Initializing a text used to modify the textview created in navigation_header.xml with
        //id navigation_username
        TextView username = (TextView) navigationHeaderView.findViewById(R.id.navigation_username);

        //Setting the textview to display the user's username
        username.setText(awsMobileClient.getIdentityManager().getUserName());

        //Textview for user email
        TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navigation_email);
        try {
            //Displaying the user's email
            email.setText(JWTUtils.getUserEmail(JWTUtils.decode(awsMobileClient.getIdentityManager().getCurrentIdentityProvider().getToken())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            public boolean onNavigationItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.home:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Home");
                        item.setChecked(false);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.add_class:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AddClassFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Class");
                        item.setChecked(false);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.remove_class:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new RemoveClassFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Remove Class");
                        item.setChecked(false);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.sign_out:

                        getIdentityManager().signOut();
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        finish();
                }
                return false;
            }
        });

        imgButton = (ImageButton) navigationHeaderView.findViewById(R.id.imageButton1);
                imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_UPLOAD_REQUEST);

            }
         });

        //******** load pic from internal memory and format it into a circle
        if(fileExistance("desiredFilename.png")) {
            // Make the image into a circle
            RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), getThumbnail("desiredFilename.png"));
            roundedBitmapDrawable.setCircular(true);
            imgButton.setImageDrawable(roundedBitmapDrawable);
        }
            //*********

    }





    public IdentityManager getIdentityManager()
    {
        return identityManager;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AWSMobileClient.defaultMobileClient().getIdentityManager().isUserSignedIn()) {
            // In the case that the activity is restarted by the OS after the application
            // is killed we must redirect to the splash activity to handle the sign-in flow.
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }


    }


    //************
    //Save picture to internal memory
    //************
    public boolean saveImageToInternalStorage(Bitmap image) {

        try {
        // Use the compress method on the Bitmap object to write image to
        // the OutputStream
            FileOutputStream fos = openFileOutput("desiredFilename.png", Context.MODE_PRIVATE);

        // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }
    //************
    //end of save picture to internal memory
    //************


    //************
    //Load picture from internal memory
    //************
    public boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
        // We can read and write the media
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        // We can only read the media
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {
        // all we need to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
    }

    public Bitmap getThumbnail(String filename) {

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        Bitmap thumbnail = null;


        // If no file on external storage, look in internal storage
        if (thumbnail == null) {
            try {
                File filePath = getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                Log.e(LOG_TAG + "getThumbnail() failed", ex.getMessage());
            }
        }
        return thumbnail;
    }
    //******
    // end of load picture from internal memory
    //******

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here excluding the home button.

        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        // Save the title so it will be restored properly to match the view loaded when rotation
        // was changed or in case the activity was destroyed.
        if (toolbar != null) {
            bundle.putCharSequence(BUNDLE_KEY_TOOLBAR_TITLE, toolbar.getTitle());
        }
    }

    @Override
    public void onClick(final View view) {
        if (view == signOutButton) {
            // The user is currently signed in with a provider. Sign out of that provider.
            identityManager.signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        // ... add any other button handling code here ...

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

                     if(resultCode == RESULT_CANCELED) return;

                        if (requestCode == IMAGE_UPLOAD_REQUEST) {
                        ParcelFileDescriptor fd;
                        try {
                                fd = getContentResolver().openFileDescriptor(data.getData(), "r");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                return;
                            }

                            // Get the image file location
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor());

                            //save to internal storage
                            saveImageToInternalStorage(bmp);

                            // Make the image into a circle
                            RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(), bmp);
                            roundedBitmapDrawable.setCircular(true);
                            imgButton.setImageDrawable(roundedBitmapDrawable);
                        }
            }




    public void setActionBarTitle(String title) {

        try {
            getSupportActionBar().setTitle(title);
        }
        catch(Exception e)
        {
            Log.d("MainActivity","Error changing title of action bar");

        }

    }

    //******
    //check if the picture exists in the internal memory before trying to load the picture to prevent crash
    //******
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
    //end of file existance code

}
