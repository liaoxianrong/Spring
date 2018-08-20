package oauth2;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
	public static void main(String[] args) {
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String finalPassword = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
		System.out.println(finalPassword);
	}
}
