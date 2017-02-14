import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

class RSAEncoder {
    public static final int PRIME_SIZE = 64;


    //*************************************************************************
    // Main
    public static void main(String[] args) throws IOException {
        Random rnd = new Random();
        BigInteger p = BigInteger.probablePrime(PRIME_SIZE,rnd);
        BigInteger q = BigInteger.probablePrime(PRIME_SIZE,rnd);
        //BigInteger p = BigInteger.valueOf(61);
        //BigInteger q = BigInteger.valueOf(53);

        System.out.println("p: "+p);
        System.out.println("q: "+q);
        BigInteger n = p.multiply(q);
        System.out.println("n: "+n);
        BigInteger totient = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        System.out.println("t: "+totient);
        BigInteger e;
        while(true){
            e = BigInteger.probablePrime(16,rnd).mod(totient);
            if(totient.mod(e) != BigInteger.valueOf(0)) break;
        }
        e = BigInteger.valueOf(17);
        System.out.println("e: "+e);
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
}
