package hashAttack;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Random;

public class Main {

	private static Random random;
	private static int c1 = 0;
	private static int c2 = 0;
	
	public static void collisionAttack() {
		int n = 0;
		boolean match = false;
		HashMap<String,String> hm = new HashMap<String,String>();
		
		while(!match) {
			try {
				String x = getRandomString();
				String y = sha1(x);
				
				if(hm.containsKey(y))
					match = true;
				else
					hm.put(y,x);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			n++;
		}
		
		c1+=n;
	}
	
	public static void preImageAttack() {
		int n = 0;
		boolean match = false;
		String stringToFind = getRandomString();
		String sha1ToFind = null; //original hash
		try {
			sha1ToFind = sha1(stringToFind);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(!match) {
			
			try {
				String x = getRandomString();
				String y = sha1(x);
				
				if(y.equals(sha1ToFind))
					match = true; //we found a string with a matching hash
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			n++;
		}
		
		c2+=n;
	}
	
	public static String getRandomString() {
		int stringLength = random.nextInt(13) + 8; //length from 8 chars to 20 chars
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<stringLength;i++)
			sb.append(Character.toChars((random.nextInt(94) + 33))); //get an ascii code from ! to ~ and add random character to string
		
		return sb.toString();
	}

	static String sha1(String input) throws Exception {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        //each character is 4 bits so substring * 4 = bits
        //we will be testing bits between 4-20 substrings 1-5
        return sb.substring(0, 6).toString(); //the second digit determines how many bites each hash will represent
    }
	
	public static void main(String[] args)	{
		random = new Random();
		
		for(int i=0;i<10;i++)
			collisionAttack();
		
		for(int i=0;i<10;i++)
			preImageAttack();
		
		c1=c1/10;
		c2=c2/10;
		
		System.out.println(c1);
		System.out.println(c2);
		
		System.out.println("Finished");
		
		
	}

}
