import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;

/**
* <b>Experience est une classe qui permet de generer des Tests pour les algorithmes.</b>
* @author Lucas LABADENS Isabelle MARINO
*/
public class Experience {
/**
	*traducion d'un int en tableau de 0/1
	*@param o
	*@return int[]
	*/
	public static int[] int2tab(int o) { // sur 32 byte
		if (o == -1)
			return null;
		long mask = (1073741824) * 2L;
		int oct[] = new int[32];

		for (int i = 0; i < 32; i++) {
			if ((o & mask) == 0) {
				oct[i] = 0;
				mask = mask >> 1;
			} else {
				oct[i] = 1;
				mask = mask >> 1;
			}
		}
		return oct;
	}

/**
	*fonction main
	*/
    public static void main(String[]args){
	try{
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Quel suite voulez vous créez");
	    String suite = sc.nextLine();
	    System.out.println("longueur de la suite :");
	    int longueur = Integer.parseInt(sc.nextLine());
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(suite)));
	    switch(suite){
	    case "constante":
		constante(fe, longueur);
		fe.close();
		break;
	    case "alterner":
		alterner(fe, longueur);
		fe.close();
		break;
	    case "aleatoire":
		aleatoire(fe,longueur);
		fe.close();
		break;
	    case "champ":
		champ(fe,longueur);
		fe.close();
		break;
	    default :
		System.out.println("cette suite n'existe pas");
	    }
	    
	}
	catch(Exception e){
	}
    }
	/**
	*fonction qui cree une suite constante de b octet 
	*@param fe
	*@param b
	*/
    public static void constante(EcrireBit fe,int b) throws IOException{
	for(int i=0;i<b;i++){
		if(i%10000000==1){
			System.out.println(i);
		}
	    fe.write(0);
	}
    }
	/**
	*fonction qui cree une suite alternee de b octet 
	*@param fe
	*@param b
	*/
    public static void alterner(EcrireBit fe ,int b) throws IOException{
	System.out.println(b*8);
	for(int i=0;i<b;i++){
		for(int j=0;j<8; j++){
	   	 if(j%2==0)
			fe.writeBit(0);
		    else
			fe.writeBit(1);
		}
	}
    }

	/**
	*fonction qui cree une suite aleatoire de b octet 
	*@param fe
	*@param b
	*/
    public static void aleatoire(EcrireBit fe, int b) throws IOException{
	fe = new EcrireBit(new FileOutputStream(new File("Aleatoire")));
	for(int i=0;i<b;i++){
		for(int j=0;j<8; j++){
	    	int alea = (int)(Math.random()*2);
	    	fe.writeBit(alea);
		}

	    
	}
    }
	/**
	*fonction qui cree une suite de champarnone de b octet 
	*@param fe
	*@param b
	*/
    public static void champ(EcrireBit fe,int b) throws IOException{
	int j=1;
	int k=1;
	int bit=1;
	int puissance =0;
	int []octet=null;
	while(k<b*8){	
		if (j >=bit) {
			puissance++;
			bit = bit * 2;
			j=0;
		}
		octet = int2tab( j);	
		if (32 - puissance >= 0) {
			for (int i = 32 - puissance; i < 32; i++) {
				if(k<=b*8){
					fe.writeBit(octet[i]);

					k++;
				}
			}
		}
		j++;
	}

    }
	
}
