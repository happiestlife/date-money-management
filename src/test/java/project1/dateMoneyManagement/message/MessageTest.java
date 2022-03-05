package project1.dateMoneyManagement.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@SpringBootTest
public class MessageTest {

    @Autowired
    private ResourceBundleMessageSource ms;
    
    @Test
    public void KO_test() {
        String greet = ms.getMessage("greeting", new String[]{"치킨맨"}, Locale.KOREA);
        String str = "치킨맨님, 반갑습니다.";
        Assertions.assertThat(greet).isEqualTo(str);
    }
}
