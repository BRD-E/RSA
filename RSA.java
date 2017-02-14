import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

class RSA {
    private static final int PRIME_SIZE = 64;
	private static BigInteger prime1;
	private static BigInteger prime2;
	private static BigInteger modulus;
	private static BigInteger totient;
	private static BigInteger publicKey;
	private static BigInteger privateKey;
	private static Random rnd = new Random();
	private static String message;


    //*************************************************************************
    // Main
    public static void main(String[] args) throws IOException {
        System.out.println("c: "+c);
        BigInteger ec = BigInteger.valueOf(c).modPow(e, n);
        System.out.println("encryted: "+ec);
        BigInteger m = ec.modPow(d, n);
        System.out.println("decrypted: "+m.intValue());
    }
	public void generateKeys()
	{
		generatePrime12();
		generateModulus();
		generateTotient();
		generatePublicKey();
		generatePrivateKey();
		printKeys();
	}
	static void generatePrime12()
	{
		prime1 = BigInteger.probablePrime(PRIME_SIZE,rnd);
		prime2 = BigInteger.probablePrime(PRIME_SIZE,rnd);
	}
	static void generateModulus()
	{
		modulus = prime1.multiply(prime2);
	}
	static void generateTotient()
	{
		totient = prime1.subtract(BigInteger.valueOf(1)).multiply(prime2.subtract(BigInteger.valueOf(1)));
	}
	static void generatePublicKey()
	{
        while(true)
		{
            publicKey = BigInteger.probablePrime(16,rnd).mod(totient);
            if (totient.mod(publicKey) != BigInteger.valueOf(0))
			{
				break;
			}
        }
	}
	static void generatePrivateKey()
	{
		privateKey = publicKey.modInverse(totient);
	}
	static void printKeys()
	{

	}
	static BigInteger encryptMessage(String message)
	{
		byte[] byteMessage = message.getBytes();
		BigInteger bigMessage = new BigInteger(byteMessage);
		BigInteger encrypted = BigInteger.valueOf(bigMessage).modPow(privateKey, modulus);
		return encrypted;
	}
	static void decryptSequence()
	{

	}
}
