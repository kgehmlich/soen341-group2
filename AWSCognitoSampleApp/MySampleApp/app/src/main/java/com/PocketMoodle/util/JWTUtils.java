package com.PocketMoodle.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * Created by Mewt on 2017-02-27.
 */

public class JWTUtils {
    public static String decode(String accessToken) throws Exception{
        String decodedToken= "";
        try{
            String[] encodedToken = accessToken.split("[.]"); // split the token according to the dots
             decodedToken = getJson(encodedToken[1]);
        }catch (UnsupportedEncodingException e){

        }
        return decodedToken;
    }

    //Function to decode the token
    public static String getJson(String splitToken) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(splitToken, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static String getUserEmail(String bodyDecodedToken){
        String userEmail= "";
        //Replacing all quotes in the string by spaces
        bodyDecodedToken = bodyDecodedToken.replaceAll("\""," ");

        //Setting a pattern for email
       String emailPattern = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";

        //Array of strings split according to the spaces
       String [] test = bodyDecodedToken.split(" ");
        for (int i = 0; i<test.length; i++){
            //if pattern matches email pattern save it and return it
            if(Pattern.matches(emailPattern,test[i])) {
                userEmail = test[i];
                break;
            }
        }

        return userEmail;

    }
}
