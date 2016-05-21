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
    public static void main(String[]args){
	try{
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Quel suite voulez vous créez");
	    String suite = sc.nextLine();
	    System.out.println("longueur de la suite :");
	    int longueur = Integer.parseInt(sc.nextLine());
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File("constante")));
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

	
}
