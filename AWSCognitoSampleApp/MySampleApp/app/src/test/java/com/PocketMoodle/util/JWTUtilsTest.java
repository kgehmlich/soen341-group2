package com.PocketMoodle.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mewt on 2017-03-03.
 */
public class JWTUtilsTest {
   /* @Test
   *//* public void decode() throws Exception {
        String input = "eyJraWQiOiJ5cUxEd2ozXC9rRGd6bjRHMndLUFFHOGx0M2phWXdGSmFqQXliK0s5WmhDZz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzZTllZGQxNS1mZjc3LTRkOTctOGYzZC1jNzEzNjhmZDk3OTMiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfSlpkM3doTTBGIiwicGhvbmVfbnVtYmVyX3ZlcmlmaWVkIjpmYWxzZSwiY29nbml0bzp1c2VybmFtZSI6Im1ld3RyYW5kZWxsIiwiZ2l2ZW5fbmFtZSI6Im1ld3RyYW5kZWxsIiwiYXVkIjoiNTN0ZHFzanNuYnVqaGkycWJtOHByNmUwYXAiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTQ4ODE5OTc1MSwicGhvbmVfbnVtYmVyIjoiKzE1MTQ5OTk5OTk5IiwiZXhwIjoxNDg4MjAzMzUxLCJpYXQiOjE0ODgxOTk3NTEsImVtYWlsIjoibW9tb19sZWJlc3RAaG90bWFpbC5mciJ9.y3uhUVgGq3vCZIXbjFZC5JXz1uCG_hRiV3wNQBmSfySHAJgsTzzDvolZq6F32vc1QmC2JODn2Ues5DLmucChAzjDDmWVW8GJYKYVOyFxYkmtHbnTMkzXJJZsteTXSGW77H7pBILVQrYdFws_wW1JeRiP5GYw4LFiavBOL7DQ_e_zsIi3ojR2cHy9T6ORQGQuPsQKYO346EQllofoeBnPpEaQW_ZeWAyDX98NP02c7PF3_McZVT8uAPzGVQS918nxFsaOSy1-ChNFN_kWLCeNfqRgCnH4DK5pzvBZK2GQMdPtw1e0XI9P2o59u2Yng4a4KiU4rsXqdzlaTG9Xa-qJrw";
        String expected = "{\n" +
                "  \"sub\": \"3e9edd15-ff77-4d97-8f3d-c71368fd9793\",\n" +
                "  \"email_verified\": true,\n" +
                "  \"iss\": \"https://cognito-idp.us-east-1.amazonaws.com/us-east-1_JZd3whM0F\",\n" +
                "  \"phone_number_verified\": false,\n" +
                "  \"cognito:username\": \"mewtrandell\",\n" +
                "  \"given_name\": \"mewtrandell\",\n" +
                "  \"aud\": \"53tdqsjsnbujhi2qbm8pr6e0ap\",\n" +
                "  \"token_use\": \"id\",\n" +
                "  \"auth_time\": 1488199751,\n" +
                "  \"phone_number\": \"+15149999999\",\n" +
                "  \"exp\": 1488203351,\n" +
                "  \"iat\": 1488199751,\n" +
                "  \"email\": \"momo_lebest@hotmail.fr\"\n" +
                "}";
        String output;
        output = JWTUtils.decode(input);

        assertEquals(expected,output);
    }*/

    @Test
    public void getUserEmail() throws Exception {
        String input = "{\n" +
                "  \"sub\": \"3e9edd15-ff77-4d97-8f3d-c71368fd9793\",\n" +
                "  \"email_verified\": true,\n" +
                "  \"iss\": \"https://cognito-idp.us-east-1.amazonaws.com/us-east-1_JZd3whM0F\",\n" +
                "  \"phone_number_verified\": false,\n" +
                "  \"cognito:username\": \"mewtrandell\",\n" +
                "  \"given_name\": \"mewtrandell\",\n" +
                "  \"aud\": \"53tdqsjsnbujhi2qbm8pr6e0ap\",\n" +
                "  \"token_use\": \"id\",\n" +
                "  \"auth_time\": 1488199751,\n" +
                "  \"phone_number\": \"+15149999999\",\n" +
                "  \"exp\": 1488203351,\n" +
                "  \"iat\": 1488199751,\n" +
                "  \"email\": \"momo_lebest@hotmail.fr\"\n" +
                "}";
        String expected ="momo_lebest@hotmail.fr";
        String output;
        output = JWTUtils.getUserEmail(input);
        assertEquals(expected,output);
    }

}