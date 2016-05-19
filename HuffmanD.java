import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;

public class HuffmanD{
    public void addLettre(char l,Arbre a){
	
    }

    //fonction de compression
    /* public void Compression(FileInputStream fl,EcrireBit fe) throws IOException{
	int l = fl.read();
	Node nul = new Node(0);
	Arbre a =  new Arbre(nul,new Feuille(1,new Character((char)l)));
	write(l);
	CodeLettre c[256];
	while(l=fl.read()){
	    if(c[l]!=null){
	    }
	    else{
		c[l]=a.newLettre(l);
		
	    }
		
	}
	
	    
    }
    */
}
