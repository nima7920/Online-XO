package server.Util;

import java.security.SecureRandom;
import java.util.Base64;

public class AuthTokenGenerator {

    private SecureRandom secureRandom;

    public AuthTokenGenerator() {
     secureRandom=new SecureRandom();
    }

    public String getAuthToken(){
        return secureRandom.nextInt()+"";
    }
}
