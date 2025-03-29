import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class Rsa 
{
	private BigInteger p,q,n,phi,e,d;
	private SecureRandom random;
	
	
	public Rsa(int bitLength)
	{
		random=new SecureRandom();
		generateKeys(bitLength);
		
	}
	
	private void generateKeys(int bitLength)
	{
		p = BigInteger.probablePrime(bitLength/2, random);
		q = BigInteger.probablePrime(bitLength/2, random);
		n = p.multiply(q);		
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("65537");
		while (!e.gcd(phi).equals(BigInteger.ONE))
		{
			e = e.add(BigInteger.TWO);
			
		}
		
		d=e.modInverse(phi);
		
	}
	
	public BigInteger encrypt(BigInteger message)
	{
		return message.modPow(e,n);
		
	}
	
	public BigInteger decrypt(BigInteger ciphertext)
	{
		return ciphertext.modPow(d,n);
		
	}
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter text to Encrypt :");
		String plaintext = scanner.nextLine().trim();
		scanner.close();
		
		int bitLength = 2048;
		Rsa rsa = new Rsa(bitLength);
		
		BigInteger message = new BigInteger(plaintext.getBytes());
		
		BigInteger ciphertext = rsa.encrypt(message);
		System.out.println("Ciphertext: "+ ciphertext);
		
		BigInteger decryptedMessage = rsa.decrypt(ciphertext);
		
		String decryptedText = new String(decryptedMessage.toByteArray());
		System.out.println("Decrypted Text :"+ decryptedText);
		
	}
	
	
}
