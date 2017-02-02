package macAttack;

public class Sha1 {

    //Bitwise rotate a 32-bit number to the left
    private static int rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    //Take a string and return the base64 representation of its SHA-1.
    public static String encode(String str) {

        // Convert a string to a sequence of 16-word blocks, stored as an array.
        // Append padding bits and the length, as described in the SHA1 standard

        byte[] x = str.getBytes();
        int[] blks = new int[(((x.length + 8) >> 6) + 1) * 16];
        int i;

        for(i = 0; i < x.length; i++) {
            blks[i >> 2] |= x[i] << (24 - (i % 4) * 8);
        }

        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blks[blks.length - 1] = 1024 + (x.length * 8); //add 1024 to represent the bits of the key + message + padding

        // calculate 160 bit SHA1 hash of the sequence of blocks

        int[] w = new int[80];

        //overwriting registers with the HMAC of the intercepted message
        int a = 0xf4b645e8;
    	int b = 0x9faaec2f;
    	int c = 0xf8e443c5;
    	int d = 0x95009c16;
    	int e = 0xdbdfba4b;

        for(i = 0; i < blks.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            int olde = e;

            for(int j = 0; j < 80; j++) {
                w[j] = (j < 16) ? blks[i + j] :
                       ( rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1) );

                int t = rol(a, 5) + e + w[j] +
                   ( (j < 20) ?  1518500249 + ((b & c) | ((~b) & d))
                   : (j < 40) ?  1859775393 + (b ^ c ^ d)
                   : (j < 60) ? -1894007588 + ((b & c) | (b & d) | (c & d))
                   : -899497514 + (b ^ c ^ d) );
                e = d;
                d = c;
                c = rol(b, 30);
                b = a;
                a = t;
              }

              a = a + olda;
              b = b + oldb;
              c = c + oldc;
              d = d + oldd;
              e = e + olde;
          }

          //convert each word in the hash to bytes
		  byte[] result = new byte[20];
		  toBytes(result,a,0);
		  toBytes(result,b,4);
		  toBytes(result,c,8);
		  toBytes(result,d,12);
		  toBytes(result,e,16);
		  
		  //turn array of bytes into a hex string, then return it
          return byteArrayToHex(result);
    }
    
    private static void toBytes(byte[] result,int i,int starting)
    {
		result[starting+0] = (byte) (i >> 24);
		result[starting+1] = (byte) (i >> 16);
		result[starting+2] = (byte) (i >> 8);
		result[starting+3] = (byte) (i /*>> 0*/);
    }

    private static String byteArrayToHex(byte[] a) 
    {
    	   StringBuilder sb = new StringBuilder(a.length * 2);
    	   for(byte b: a)
    	      sb.append(String.format("%02x", b & 0xff));
    	   return sb.toString();
	}
}
