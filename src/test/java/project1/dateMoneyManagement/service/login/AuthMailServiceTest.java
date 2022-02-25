package project1.dateMoneyManagement.service.login;

import java.util.Random;

public class AuthMailServiceTest implements AuthMailService{

    public String sendMail(AuthMailDTO authMailDTO, String id) {
        return makeAuthCode();
    }

    private String makeAuthCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            sb.append(Integer.toString(random.nextInt(10)));
        }

        return sb.toString();
    }
}
