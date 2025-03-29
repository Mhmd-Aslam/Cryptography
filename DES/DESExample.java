import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class DESExample {

    // Generate a DES key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56); // DES uses a 56-bit key
        return keyGen.generateKey();
    }

    // Encrypt a message using the provided DES key
    public static String encrypt(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Specify padding scheme
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] messageBytes = message.getBytes("UTF8");
        byte[] encryptedBytes = cipher.doFinal(messageBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes); // Convert the encrypted bytes to Base64 for readability
    }

    // Decrypt an encrypted message using the provided DES key
    public static String decrypt(String encryptedMessage, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Specify padding scheme
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF8");
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            SecretKey key = null;

            while (true) {
                System.out.println("Do you want to (1) Encrypt, (2) Decrypt, or (3) Exit?");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (choice == 1) {
                    // Encrypt a user-input message
                    System.out.println("Enter the message to encrypt:");
                    String plaintext = scanner.nextLine();
                    key = generateKey(); // Generate a DES key
                    String encryptedText = encrypt(plaintext, key);

                    // Display encryption details
                    System.out.println("Encryption Details:");
                    System.out.println("Encrypted Message: " + encryptedText);
                    System.out.println("DES Key (Base64): " + Base64.getEncoder().encodeToString(key.getEncoded()));
                    System.out.println("Note: Save the DES Key (Base64 encoded) to decrypt later.");

                    // Optionally, decrypt with the same key
                    System.out.println("Do you want to decrypt the message with the same key? (yes/no)");
                    String decryptOption = scanner.nextLine();
                    if (decryptOption.equalsIgnoreCase("yes")) {
                        System.out.println("Decrypting with the same key...");
                        String decryptedText = decrypt(encryptedText, key);
                        System.out.println("Decrypted Message: " + decryptedText);
                    }

                } else if (choice == 2) {
                    // Decrypt a user-input encrypted message
                    System.out.println("Enter the Base64-encoded DES key used for encryption:");
                    String base64Key = scanner.nextLine();
                    SecretKey keyForDecryption = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "DES");

                    System.out.println("Enter the Base64-encoded message to decrypt:");
                    String encryptedText = scanner.nextLine();
                    String decryptedText = decrypt(encryptedText, keyForDecryption);

                    // Display decryption result
                    System.out.println("Decryption Result:");
                    System.out.println("Decrypted Message: " + decryptedText);

                } else if (choice == 3) {
                    System.out.println("Exiting...");
                    break;

                } else {
                    System.out.println("Invalid choice! Please enter 1 to Encrypt, 2 to Decrypt, or 3 to Exit.");
                }

            }

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
