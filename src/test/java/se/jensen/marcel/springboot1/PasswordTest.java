package se.jensen.marcel.springboot1;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    void visaHurHashingFungerar() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String mittLösen = "Katt123!";

        // Här skapas hashen
        String hash = encoder.encode(mittLösen);

        System.out.println("----------------------------------------------");
        System.out.println("MITT LÖSENORD: " + mittLösen);
        System.out.println("HASHAD VERSION: " + hash);
        System.out.println("----------------------------------------------");

        // Kolla om Spring kan känna igen lösenordet mot hashen
        assertTrue(encoder.matches(mittLösen, hash));

        // Kolla att det INTE sparats i klartext
        assertNotEquals(mittLösen, hash);
    }
}