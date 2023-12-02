package be.kdg.programming3.oldproj.service;


public interface PasswordEncryptionService {

    String encrypt(String password);

    boolean matches(String rawPassword, String encodedPassword);

}