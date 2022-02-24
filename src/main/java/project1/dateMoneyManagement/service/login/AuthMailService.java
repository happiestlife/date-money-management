package project1.dateMoneyManagement.service.login;

public interface AuthMailService {
    public static final String SERVER_ADDRESS = "datemoneymanagement@gmail.com";

    public static final String TITLE = "데이트 비용 관리 프로그램 인증 코드입니다.";
    public static final String MESSAGE =
            "안녕하세요. 데이트 비용 관리 프로그램입니다.\n" +
                    "코드 6자리는 다음과 같습니다." +
                    "\n%s" +
                    "\n감사합니다.";

    public String sendMail(AuthMailDTO authMailDTO, String id);
}
