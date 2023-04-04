import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String homuhomu = encoder.encode("homuhomu");
        System.out.println(homuhomu);
    }
}
