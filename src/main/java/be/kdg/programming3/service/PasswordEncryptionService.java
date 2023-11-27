package be.kdg.programming3.service;


public interface PasswordEncryptionService {

    String encrypt(String password);

    boolean matches(String rawPassword, String encodedPassword);

}