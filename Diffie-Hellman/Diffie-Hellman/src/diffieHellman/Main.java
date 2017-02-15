package diffieHellman;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {
	
	private static BigInteger modExp(BigInteger x, BigInteger y, BigInteger n) {
		
		if(y.compareTo(BigInteger.ZERO) == 0)
			return BigInteger.ONE;
		BigInteger z = modExp(x,y.divide(new BigInteger("2")), n);
		if (y.mod(new BigInteger("2")) == BigInteger.ZERO)
			return z.multiply(z).mod(n);
		return x.multiply(z.pow(2)).mod(n);
		
	}
	
	public static void main(String[] args)
	{
		/*
		This portion of code was run beforehand to generate 3 512 bit safe prime numbers 
		//Generate p,a,b
		
		for(int i = 0;i < 3; i++) {
			PrimeGenerator pg = new PrimeGenerator(512, 512, new SecureRandom());
			System.out.println(pg.getSafePrime());
			//System.out.println(pg.getStrongPrime());
		}
		//p = 41021727491361549413898328329602841133333183478889798596018853960608303111560790542181900326404448953857207520256434071084136572941773514372749902218898017
		//a = 50274074977466649616850556148945463652532770515303953081943762300361916203015564351129193315202641884993566564770648199204798500382629411147391736413033839
		//b = 52299925207932546125090569402679760474554302979221508396985876781037999046804697476481290999823180427188346165554918406369425859426321290733896971091410521
		 * 
		We are given gbp and b to calculate (g^b%p)^a%p
		*/
		
		BigInteger p = new BigInteger("41021727491361549413898328329602841133333183478889798596018853960608303111560790542181900326404448953857207520256434071084136572941773514372749902218898017");
		BigInteger a = new BigInteger("50274074977466649616850556148945463652532770515303953081943762300361916203015564351129193315202641884993566564770648199204798500382629411147391736413033839");
		BigInteger b = new BigInteger("1998286638065473057944506344030256054916203227381748916180906390214373930105605405985818224246280726328877245115163209963634633681313092395058312190549");
		BigInteger g = new BigInteger("5");
		
		System.out.println("p: " + p.toString()); 
		System.out.println("a: " + a.toString());
		System.out.println("b: " + b.toString());
		
		BigInteger gap = modExp(g, a, p);
		BigInteger gbp = /*modExp  (g, b, p);*/new BigInteger("39823273309167835319637266013034375881097691154162484454233880266109930461711279616434442535415162143299958479548220902800003795460223232070956476212162818");
		BigInteger gbpap = modExp(gbp, a, p);
		
		System.out.println("g^a%p: " + gap);
		System.out.println("g^b%p: " + gbp);
		System.out.println("(g^b%p)^a%p: " + gbpap.toString());

	}

}
