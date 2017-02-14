import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

class RSA{
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
        generateKeys();
    }
	static void generateKeys()
	{
		generatePrime12();
		generateModulus();
		generateTotient();
		generatePublicKey();
		generatePrivateKey();
		printKeys();
		decryptMessage(encryptMessage(BigInteger.valueOf(2048)));
	}
	static void generatePrime12()
	{
		//prime1 = BigInteger.probablePrime(PRIME_SIZE,rnd);
		//prime2 = BigInteger.probablePrime(PRIME_SIZE,rnd);
		prime1 = BigInteger.valueOf(61);
		prime2 = BigInteger.valueOf(53);
	}
	static void generateModulus()
	{
		modulus = prime1.multiply(prime2);
	}
	static void generateTotient()
	{
		// |(p-1) * (q-1) | / gcd((p-1), (q-1))
		BigInteger prime1Minus1 = prime1.subtract(BigInteger.ONE);
		BigInteger prime2Minus1 = prime2.subtract(BigInteger.ONE);
		BigInteger multiplied = prime1Minus1.multiply(prime2Minus1);
		BigInteger gcd = prime1Minus1.gcd(prime2Minus1);
		totient = multiplied.divide(gcd);
	}
	static void generatePublicKey()
	{
		publicKey = BigInteger.valueOf(16);
        while(true)
		{
            publicKey = publicKey.add(BigInteger.ONE);
			if (publicKey.isProbablePrime(100))
			{
				if (totient.mod(publicKey) != BigInteger.valueOf(0))
				{
					break;
				}
			}
        }
	}
	static void generatePrivateKey()
	{
		privateKey = publicKey.modInverse(totient);
	}
	static void printKeys()
	{
		System.out.println("Modulus: " + modulus.toString());
		System.out.println("publicKey: " + publicKey.toString());
		System.out.println("privateKey: " + privateKey.toString());
		System.out.println("totient: " + totient.toString());
	}
	static BigInteger encryptMessage(BigInteger message)
	{
		BigInteger encrypted = message.modPow(publicKey, modulus);
		System.out.println("encrypted: " + encrypted.toString());
		return encrypted;
	}
	static void decryptMessage(BigInteger message)
	{
		BigInteger decrypted = message.modPow(privateKey, modulus);
		System.out.println("decrypted: " + decrypted.toString());
	}
}
