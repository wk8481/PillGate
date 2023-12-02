//package be.kdg.programming3.oldproj.service;
//
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class PasswordEncryptionServiceImpl implements PasswordEncryptionService {
//
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    public PasswordEncryptionServiceImpl() {
//        this.passwordEncoder = new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public String encrypt(String password) {
//        return passwordEncoder.encode(password);
//    }
//
//    @Override
//    public boolean matches(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
//}
