import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;

public class Experience {

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
		//EcrireBit fe = new EcrireBit(new FileOutputStream(new File("constante")));
		constante(fe, longueur);
		fe.close();
		break;
	    case "alterner":
		//EcrireBit fe = new EcrireBit(new FileOutputStream(new File("alterner")));
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
    public static void constante(EcrireBit fe,int b) throws IOException{
	//	EcrireBit fe = new EcrireBit(new FileOutputStream(new File("constante")));
	for(int i=0;i<b;i++){
		if(i%10000000==1){
			System.out.println(i);
		}
	    fe.write(0);
	}
	//	fe.close();
    }
    public static void alterner(EcrireBit fe ,int b) throws IOException{
	//EcrireBit fe = new EcrireBit(new FileOutputStream(new File("alterner")));
	System.out.println(b*8);
	for(int i=0;i<b;i++){
		for(int j=0;j<8; j++){
	   	 if(j%2==0)
			fe.writeBit(0);
		    else
			fe.writeBit(1);
		}
	}
	//fe.close();
    }


    public static void aleatoire(EcrireBit fe, int b) throws IOException{
	fe = new EcrireBit(new FileOutputStream(new File("Aleatoire")));
	for(int i=0;i<b;i++){
		for(int j=0;j<8; j++){
	    	int alea = (int)(Math.random()*2);
	    	fe.writeBit(alea);
		}

	    
	}
	//fe.close();
    }
    public static void champ(EcrireBit fe,int b) throws IOException{
	/*ArrayList<Integer> a = new ArrayList<Integer>();
	a.add(0);
	a.add(1);
	ArrayList <Integer>a2 = new ArrayList<Integer>();
	int compte =0;
	int alt=0;
	fe.writeBit(0);
	fe.writeBit(1);
	for(int i=1;i<8*b;i++){
	    int taille = a.size();
	    for(int j=0;j<taille;j++){
		if(compte<i){
		    a.add(a.remove(0));
		   // System.out.println(" sa ecrit fils");
		    fe.writeBit(a.get(a.size()-1));
		    compte++;	
		
		}
		else{
		    
		    System.out.println(alt+"alt valeur");
		    fe.writeBit(alt);
		    alt=(alt+1)%2;
		    a2.add(alt);
		    compte=0;
		    j--;
		}
	    }
	}
	fe.writeLastBit();
	*/
	
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
