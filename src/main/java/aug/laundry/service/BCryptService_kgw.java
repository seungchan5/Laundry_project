package aug.laundry.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptService_kgw {

    public String encodeBCrypt(String password){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder(10);
        String encodedPassword = bc.encode(password);
        return encodedPassword;

    }

    public boolean matchBCrypt(String password, String encodedPassword){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder(10);
        boolean res = bc.matches(password, encodedPassword);
        return res;
    }
}
