import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RC4 {

    // Key Scheduling Algorithm (KSA)
    private static byte[] ksa(byte[] key) {
        byte[] S = new byte[256];
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + key[i % key.length]) & 0xFF;
            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
        return S;
    }

    // Pseudo-Random Generation Algorithm (PRGA)
    private static byte[] prga(byte[] S, byte[] data) {
        byte[] keystream = new byte[data.length];
        int i = 0, j = 0;
        for (int k = 0; k < data.length; k++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;
            byte keystreamByte = S[(S[i] + S[j]) & 0xFF];
            keystream[k] = (byte) (data[k] ^ keystreamByte);
        }
        return keystream;
    }

    // RC4 encryption/decryption method
    public static byte[] rc4(byte[] key, byte[] data) {
        byte[] S = ksa(key);
        return prga(S, data);
    }

    // Convert byte array to a hex string with spaces
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim(); // Trim to remove the trailing space
    }

    // Convert byte array to a string using UTF-8 encoding
    private static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // Convert a hex string to a byte array
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display options
            System.out.println("Choose an option:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");

            // Read user choice
            System.out.print("Enter your choice (1, 2, or 3): ");
            String choice = scanner.nextLine();
            
            if (choice.equals("3")) {
                break; // Exit the loop and terminate the program
            }
            
            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                continue; // Re-prompt the user
            }

            // Prompt user for key
            System.out.print("Enter the key: ");
            String keyString = scanner.nextLine();
            byte[] key = keyString.getBytes(StandardCharsets.UTF_8);
            
            byte[] inputData;
            
            if (choice.equals("1")) {
                // Encrypt Plaintext
                System.out.print("Enter the plaintext: ");
                String plaintext = scanner.nextLine();
                inputData = plaintext.getBytes(StandardCharsets.UTF_8);

                // Encrypt
                byte[] ciphertext = rc4(key, inputData);
                // Print encrypted data as hex with spaces
                System.out.println("Ciphertext (Hex): " + bytesToHex(ciphertext));

            } else if (choice.equals("2")) {
                // Decrypt Hexadecimal Ciphertext
                System.out.print("Enter the ciphertext (hex): ");
                String hexInput = scanner.nextLine();

                // Convert hex string to byte array
                inputData = hexStringToByteArray(hexInput.replace(" ", ""));

                // Decrypt
                byte[] plaintext = rc4(key, inputData);
                // Print decrypted data as string
                System.out.println("Decrypted text: " + bytesToString(plaintext));
            }
        }
        scanner.close(); // Close the scanner when exiting
    }
}