import java.io.*;

/**
* <b>LrreBit est une classe qui permet de lire bit a bit dans un fichier, extends FilterIntputStream.</b>
* @author Lucas LABADENS Isabelle MARINO
*/
public class LireBit extends FilterInputStream {

/**
* octet representer dans un tableau 
*/ 
	int octet[] = new int[8];
/**
* curseur dans la tableau octet
*/ 
	int cur;

/**
* Constructeur LireBit.
*
* @param in
* InputStream du fichier à lire
*/ 
	public LireBit(InputStream in) {
		super(in);
		cur = 0;
	}


	/**
         *Lire un nouvel octet 
         * @return un boolean (true si la lecture à bien eut lieu).
         */
	public boolean lire() throws IOException {
		int mask = 0x80;
		int o = this.read();
		// System.out.println(o+"lire");
		if (o == -1)
			return false;
		for (int i = 0; i < 8; i++) {
			if ((o & mask) == 0) {
				this.octet[i] = 0;
				mask = mask >> 1;
			} else {
				this.octet[i] = 1;
				mask = mask >> 1;
			}
		}
		return true;
	}
	/**
         *Lire un nouveau bit
         * @return int (le bit lu)
         */
	public int lireBit() throws IOException {
		if (cur < 8) {
			cur++;
			return octet[cur - 1];

		}else {
			if (lire() != false) {
				cur = 1;
				return octet[0];
			} else {
				return -1;
			}
		}

	}

}
