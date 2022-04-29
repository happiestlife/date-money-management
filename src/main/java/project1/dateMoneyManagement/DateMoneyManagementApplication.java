package project1.dateMoneyManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DateMoneyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateMoneyManagementApplication.class, args);
	}

}
