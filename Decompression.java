import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

class Decompression {
	public static void main(String[] args) throws IOException {
		String in = "";
		String out = "";
		if (args.length == 0) {
			System.out
					.println("Quel est le fichier que vous voulez décompresser ?");
			Scanner sc = new Scanner(System.in);
			in = sc.nextLine();

		} else {
			in = args[0];
		}

		out = in + ".decomp";
		LireBit fr = null;
		EcrireBit fw = null;
		try {
			File i = new File(in);
			long tailleIn = i.length() - 1;
			System.out.println(tailleIn);
			fr = new LireBit(new FileInputStream(i));
			fw = new EcrireBit(new FileOutputStream(new File(out)));
			int methode = fr.read();
			System.out.println(methode);
			if (methode == 1) {
				System.out.println("Huffman");
				Huffman h = new Huffman();
				h.decompression(fr, fw, tailleIn);

			}
			if (methode == 2) {
				long debut = System.currentTimeMillis();
				LempelZiv.decompression(fr, fw);
				System.out.println(System.currentTimeMillis()-debut + " time" ); 
			} else {
				System.out.println("Ce fichier n'a pas été compresser");

			}

		} catch (StackOverflowError e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// Cette exception est levée si l'objet FileInputStream ne trouve
			// aucun fichier
			e.printStackTrace();
		} finally {
			// On ferme nos flux de données dans un bloc finally pour s'assurer
			// que ces instructions seront exécutées dans tous les cas même si
			// une exception est levée !
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
