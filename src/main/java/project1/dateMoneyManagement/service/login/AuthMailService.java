package project1.dateMoneyManagement.service.login;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
public class AuthMailService {

    private JavaMailSenderImpl mailSender;

    private static final String SERVER_ADDRESS = "chickenman10@naver.com";

    public static final String TITLE = "데이트 비용 관리 프로그램 인증 코드입니다.";
    public static final String MESSAGE =
            "안녕하세요. 데이트 비용 관리 프로그램입니다.\n" +
                    "코드 6자리는 다음과 같습니다." +
                    "\n%s" +
                    "\n감사합니다.";

    public AuthMailService() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtps.ssl.trust", "*");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtps.ssl.checkserveridentity", true);
        properties.put("mail.debug", true);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("datemoneymanagement@gmail.com");
        mailSender.setPassword("datemoney0130");
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setJavaMailProperties(properties);
    }

    public String sendMail(AuthMailDTO authMailDTO, String id) {
        SimpleMailMessage message = new SimpleMailMessage();
        String newCode = makeAuthCode();

        message.setTo(authMailDTO.getAddr());
        message.setFrom(SERVER_ADDRESS);
        message.setSubject(authMailDTO.getTitle());
        message.setText(String.format(MESSAGE, newCode));

        mailSender.send(message);

        return newCode;
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