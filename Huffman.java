import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class Huffman{
    public int[] compteIter(FileInputStream fr){
	int tab[]=new int[127];
	int oct;
	try{
	    while((oct=fr.read())>=0){
		tab[oct]++;
	    }
	}
	catch(Exception e){
	    System.out.println("can't read file");
	    e.printStackTrace();
	}
	return tab;
    }
    public Arbre arbreMin(int tab[]){
	int id1=tab[1];
	int id2=tab[1];
	int i1=0;
	int i2=0;
	for(int i=0;i<128;i++){
	    if(tab[i]>0){
		if(tab[i]<id1){
		    id2=id1;
		    id1=tab[i];
		    i2=i1;
		    i1=i;
		}
		else if(tab[i]<id2){
		    id2=tab[i];
		    i2=i;
		}
	    }
	}
	Arbre a = new Arbre(new Feuille(id1,new Character((char)i1)),new Feuille(id2,new Character((char)i2)));
	return a;
    }

}
