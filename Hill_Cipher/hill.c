#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MATRIX_SIZE 2

// Function to calculate the determinant of a 2x2 matrix mod 26
int determinant_mod26(int matrix[MATRIX_SIZE][MATRIX_SIZE]) {
    int det = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
    return (det < 0) ? det + 26 : det;  // Ensure positive determinant
}

// Function to find the modular multiplicative inverse of a number mod 26
int mod_inverse(int a) {
    for (int x = 1; x < 26; x++)
        if ((a * x) % 26 == 1) return x;  // Return the modular inverse if found
    return -1;
}

// Function to calculate the inverse of a 2x2 matrix mod 26
void inverse_matrix(int matrix[MATRIX_SIZE][MATRIX_SIZE], int inv[MATRIX_SIZE][MATRIX_SIZE]) {
    int det = mod_inverse(determinant_mod26(matrix));  // Calculate determinant inverse
    inv[0][0] =  matrix[1][1] * det % 26;
    inv[0][1] = -matrix[0][1] * det % 26;
    inv[1][0] = -matrix[1][0] * det % 26;
    inv[1][1] =  matrix[0][0] * det % 26;
    for (int i = 0; i < MATRIX_SIZE; i++)
        for (int j = 0; j < MATRIX_SIZE; j++)
            inv[i][j] = (inv[i][j] < 0) ? inv[i][j] + 26 : inv[i][j];  // Ensure positive values
}

// Function to multiply a matrix with a vector mod 26
void multiply_matrix_vector(int matrix[MATRIX_SIZE][MATRIX_SIZE], int vec[MATRIX_SIZE], int res[MATRIX_SIZE]) {
    for (int i = 0; i < MATRIX_SIZE; i++) {
        res[i] = 0;
        for (int j = 0; j < MATRIX_SIZE; j++)
            res[i] = (res[i] + matrix[i][j] * vec[j]) % 26;  // Matrix-vector multiplication mod 26
    }
}

// Function to process (encrypt/decrypt) a sentence using the matrix key
void process_sentence(int key[MATRIX_SIZE][MATRIX_SIZE], const char* input, char* output) {
    int len = strlen(input), vec[MATRIX_SIZE], res[MATRIX_SIZE];
    for (int i = 0; i < len; i += MATRIX_SIZE) {
        for (int j = 0; j < MATRIX_SIZE; j++)
            vec[j] = (i + j < len && isalpha(input[i + j])) ? toupper(input[i + j]) - 'A' : 'X' - 'A';
        multiply_matrix_vector(key, vec, res);
        for (int j = 0; j < MATRIX_SIZE; j++)
            output[i + j] = res[j] + 'A';
    }
    output[len] = '\0';
}

int main() {
    int key[MATRIX_SIZE][MATRIX_SIZE], inv_key[MATRIX_SIZE][MATRIX_SIZE];
    char plaintext[1024], ciphertext[1024];

    // Input the 2x2 key matrix from the user
    printf("Enter 2x2 key matrix (4 integers):\n");
    for (int i = 0; i < MATRIX_SIZE * MATRIX_SIZE; i++)
        scanf("%d", &key[i / MATRIX_SIZE][i % MATRIX_SIZE]);

    // Validate the key matrix (it must be invertible mod 26)
    if (determinant_mod26(key) == 0 || mod_inverse(determinant_mod26(key)) == -1) {
        printf("Invalid key matrix. The determinant must be non-zero and invertible mod 26.\n");
        return 1;
    }

    // Calculate the inverse key matrix
    inverse_matrix(key, inv_key);

    // Display the key matrix
    printf("\nKey matrix:\n");
    for (int i = 0; i < MATRIX_SIZE; i++) {
        for (int j = 0; j < MATRIX_SIZE; j++) {
            printf("%2d ", key[i][j]);
        }
        printf("\n");
    }

    // Display the inverse key matrix
    printf("\nInverse key matrix:\n");
    for (int i = 0; i < MATRIX_SIZE; i++) {
        for (int j = 0; j < MATRIX_SIZE; j++) {
            printf("%2d ", inv_key[i][j]);
        }
        printf("\n");
    }

    // Input the plaintext from the user
    printf("\nEnter the plaintext: ");
    getchar();  // Clear the newline character
    fgets(plaintext, sizeof(plaintext), stdin);
    plaintext[strcspn(plaintext, "\n")] = '\0';  // Remove the newline character

    // Encrypt the plaintext
    process_sentence(key, plaintext, ciphertext);
    printf("Ciphertext: %s\n", ciphertext);

    // Decrypt the ciphertext
    process_sentence(inv_key, ciphertext, plaintext);
    printf("Decrypted text: %s\n", plaintext);

    return 0;
}

