package com.example.weatherforecast.weatherforecast;

import java.util.Base64;

public class Test {


    public static void main (String srgs[])
    {


        String originalInput = "d2929e9483efc82c82c32ee7e02d563e";

        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        System.out.println("encodedString----"+encodedString);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        System.out.println("decodedString----"+decodedString);

    }

}
