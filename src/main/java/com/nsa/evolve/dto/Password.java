package com.nsa.evolve.dto;

import java.security.SecureRandom;

public class Password {

    private static SecureRandom random = new SecureRandom();

    //    https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
    public static String generatePassword(Integer length) {
        String available = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder sb = new StringBuilder(length);
        for( int i = 0; i < length; i++ )
            sb.append( available.charAt( random.nextInt(available.length()) ) );
        return sb.toString();
    }
}
