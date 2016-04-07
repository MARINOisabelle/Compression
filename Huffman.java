import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class Huffman{
    
    public Huffman(){
	
    }
    public static int[] compteIter(LireBit fr){
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
	System.out.println("fin iter");
	for(int i =0;i<tab.length;i++)
	    System.out.println(tab[i]);
	return tab;
    }
    
     public static Arbre arbreMin(int tab[]){
	int min1=-1;
	int min2=-1;;
	int i1=-1;
	int i2=-1;
	for(int i=0;i<127;i++){
	    if(tab[i]!=0){
		if(min1!=-1){
		    if(tab[i]<min1){
			min2=min1;
			i2=i1;
			min1=tab[i];
			i1=i;
		    }
		    else{
			if(min2!=-1){
			    if(tab[i]<min2){
				min2=tab[i];
				i2=i;
			    }
			}
			else{
			    min2=tab[i];
			    i2=i;
			}
		    }
		}
		else{
		    min1=tab[i];
		    i1=i;
		}
	    }
	}
	if(min1 !=-1 && min2 !=-1){
	    Arbre a = new Arbre(new Feuille(min1,new Character((char)i1)),new Feuille(min2,new Character((char)i2)));
		tab[i1]=0;
		tab[i2]=0;
		return a;
	}
	else{
	    if(min1 != -1){
		Arbre a = new Arbre(new Feuille(min1,new Character((char)i1)));
		tab[i1]=0;
		return a;
	    }
	    else{
		return null;
	    }
	}
	
	
    }
    public static Arbre arbreComp(LireBit fr){
	int tab[]=compteIter(fr);
	Arbre a = arbreMin(tab);
	Arbre c;
	while((c=arbreMin(tab))!=null){
	    a.fusionne(c);
	}
	return a;
    }

	/*  fonction appeler dans les main de compresion et decompression */
	public static FileOutputStream compression(FileInputStream fr, FileOutputStream fw)throws IOException{
		
		System.out.println(" Compression : Huffman");
	
		return fw;
	}

	public static FileOutputStream decompression(FileInputStream fr, FileOutputStream fw)throws IOException{
		
		System.out.println("Decompression : Huffman");
	
		return fw;
	}

}

