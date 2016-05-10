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
	
	    switch(suite){
	    case "constante":
		EcrireBit fe = new EcrireBit(new FileOutputStream(new File("constante")));
		constante(fe, longueur);
		fe.close();
		break;
	    case "alterner":
		alterner(longueur);
		break;
	    case "aleatoire":
		aleatoire(longueur);
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
	    fe.write(0);
	}
	//	fe.close();
    }
    public static void alterner(int b) throws IOException{
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File("alterner")));
	for(int i=0;i<b*8;i++){
	    if(i%2==0)
		fe.writeBit(0);
	    else
		fe.writeBit(1);
	}
	fe.close();
    }

    public static void aleatoire(int b) throws IOException{
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File("Aleatoire")));
	for(int i=0;i<8*b;i++){
	    int alea = (int)(Math.random()*2);
	    fe.writeBit(alea);
	    
	}
	fe.close();
    }

	
}
