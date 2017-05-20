package com.example.kevin.createaccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 2/1/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "soen341project.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, int age, String degree, String password, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username",username);
        params.put("age",age + "");
        params.put("degree",degree);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }


}
