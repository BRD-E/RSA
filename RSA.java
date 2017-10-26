// Author: Joshua Melton
import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

class RSA{
  private static final int PRIME_SIZE = 64;
  private static BigInteger prime1; // Q
  private static BigInteger prime2; // P
  private static BigInteger modulus; // N
  private static BigInteger totient; // Lambda
  private static int fixedPublicKey = 65537;
  private static BigInteger publicKey = BigInteger.valueOf(fixedPublicKey); // E
  private static BigInteger privateKey; // D
  private static Random rand = new Random();
  private static String message;

  public static void main(String[] args) throws IOException {
    generateKeys();
  }

  static void generateKeys()
  {
    // This is the basic outline for RSA encryption. Found on Wiki
    generatePrime12();
    generateModulus();
    generateTotient();
    generatePublicKey();
    generatePrivateKey();
    printKeys();
    System.out.println(toText(decryptMessage(encryptMessage(toBigInteger("test")))));

  }

  static void generatePrime12()
  {
    // generates 2 prime numbers P and Q
    prime1 = BigInteger.probablePrime(PRIME_SIZE,rand);
    prime2 = BigInteger.probablePrime(PRIME_SIZE,rand);
  }

  static void generateModulus()
  {
    // Calculates the N value, aka modulus
    modulus = prime1.multiply(prime2);
  }

  static void generateTotient()
  {
    // |(p-1) * (q-1) | / gcd((p-1), (q-1)) or leastCommonMultiple(p-1, q-1)
    BigInteger prime1Minus1 = prime1.subtract(BigInteger.ONE);
    BigInteger prime2Minus1 = prime2.subtract(BigInteger.ONE);
    BigInteger numerator = prime1Minus1.multiply(prime2Minus1);
    BigInteger greatestCommonDenominator = prime1Minus1.gcd(prime2Minus1);
    totient = numerator.divide(greatestCommonDenominator);
  }

  static void generatePublicKey()
  {
    // Subtract one so it will use the fixedPublicKey as default, assuming it meets the requirements
    publicKey = publicKey.subtract(BigInteger.ONE);
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
    System.out.println("modulus: " + modulus.toString());
    System.out.println("publicKey: " + publicKey.toString());
    System.out.println("privateKey: " + privateKey.toString());
    System.out.println("totient: " + totient.toString());
  }

  static BigInteger encryptMessage(BigInteger message)
  {
    BigInteger encryptedMessage = message.modPow(publicKey, modulus);
    return encryptedMessage;
  }

  static BigInteger decryptMessage(BigInteger message)
  {
    BigInteger decryptedMessage = message.modPow(privateKey, modulus);
    return decryptedMessage;
  }

  // The methods below are just for encrypting / decrypting text, not needed for lab.
  static BigInteger toBigInteger(String message)
  {
    byte[] byteString = message.getBytes();
    return new BigInteger(byteString);
  }

  static String toText(BigInteger text)
  {
    byte[] byteArray = text.toByteArray();
    return new String(byteArray);
  }
}
