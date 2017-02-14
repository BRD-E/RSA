import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

class RSA {
    private static final int PRIME_SIZE = 64;
	private static BigInteger prime1;
	private static BigInteger prime2;
	private static BigInteger totient;
	private static BigInteger publicKey;
	private static BigInteger privateKey;
	private static Random rnd = new Random();



    //*************************************************************************
    // Main
    public static void main(String[] args) throws IOException {
        BigInteger d = e.modInverse(totient);
        System.out.println("d: "+d);
        System.out.println();
        //n = new BigInteger("3217685205209121519351740253776369787387967512724035581690489949286693931765120992537584931899783756819701528805256046408628175175581616761513285402095463");
        //e = BigInteger.valueOf(55807);
        //d = new BigInteger("2200839714875147875986440361010228307459388749228397193698061029158033832125037067805443949857585924543789309726019044594351746181364535604703468726308807");
        int c = 1966733;
        System.out.println("c: "+c);
        BigInteger ec = BigInteger.valueOf(c).modPow(e, n);
        System.out.println("encryted: "+ec);
        BigInteger m = ec.modPow(d, n);
        System.out.println("decrypted: "+m.intValue());
    }
	public void generateKeys()
	{
		generatePrime12();
		generateTotient();
		generatePublicKey();
		generatePrivateKey();
	}
	static void generatePrime12()
	{
		prime1 = BigInteger.probablePrime(PRIME_SIZE,rnd);
		prime2 = BigInteger.probablePrime(PRIME_SIZE,rnd);
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
}
