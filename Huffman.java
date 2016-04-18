import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.BufferedInputStream;
import java.nio.channels.*;
import java.io.IOException;

class Huffman{
    
    public Huffman(){
	
    }
    public  int[] compteIter(LireBit fr) {
	int tab[]=new int[255];
	int oct;
	
	try{
	    oct = fr.read();
	    while(oct>=0){
		tab[oct]++;
		oct = fr.read();
	    }
	 
	  
	}
	catch(Exception e){
		System.out.println("can't read file");
		e.printStackTrace();
	}
	
	
	return tab;
    }
    
     public  Feuille feuilleMin(int tab[]){
	int min1=-1;
	int i1=-1;
	for(int i=0;i<255;i++){
	    if(tab[i]!=0){
		if(min1!=-1){
		    if(tab[i]<=min1){
			min1=tab[i];
			i1=i;
		    }
		}
		else{
		    min1=tab[i];
		    i1=i;
		}
	    }
	}
	if(min1 != -1){
	    Feuille f = new Feuille(min1,new Character((char)i1));
	    tab[i1]=0;
	    return f;
	}
	else{
	    return null;
	}
	
       
    }
    
    public Arbre arbreCompr(LireBit fr) throws Exception{
	int tab[]=compteIter(fr);
	Arbre principal = new Arbre(null);
	Node sup = new Node(0);
	Feuille min = feuilleMin(tab);
	if(min != null){
	    Feuille min2 = feuilleMin(tab);
	    if(min2 != null){
		sup.addPoids(min,min2);
		principal.racine = sup;
		System.out.println("to1");
		while((min=feuilleMin(tab))!=null){
		    sup = new Node(0);
		    System.out.println("to");
		    sup.addPoids(principal.racine,min);
		    principal.racine = sup;
		}
		return principal;
	    }
	    else{
		sup.poids = min.poids;
		sup.left=min;
		principal.racine = sup;
		return principal;
	    }
	}
	else{
	    return null;
	}
	    
    }
    public void ecrireLettre(EcrireBit fw,Node a,char lettre){
	try{
	    char c;
	    if(( c = a.right.getId()) !='\0'){
		if(c==lettre){
		    fw.writeBit(1);
		}
		else{
		    fw.writeBit(0);
		    ecrireLettre(fw,a.left.getNode(),lettre);
		}
	    }
	    else{
		if(c==lettre){
		    fw.writeBit(0);
		}
		else{
		    fw.writeBit(1);
		    ecrireLettre(fw,a.right.getNode(),lettre);
		}
	    }
	}
	catch(Exception e){
	    e.printStackTrace();
	    System.out.println(e);
	}
    }
	/*  fonction appeler dans les main de compresion et decompression */
    public  void compression(String fr, String fw)throws Exception{
	LireBit fl = new LireBit(new FileInputStream(new File(fr)));
	
	Arbre a = arbreCompr(fl);
	fl.close();
	fl =  new LireBit(new FileInputStream(new File(fr)));
	int l ;
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(fw)));
	System.out.println("pas gentil");
	while((l=fl.read())!=-1){
	    ecrireLettre(fe,a.racine,new Character((char)l));
	    System.out.println("ecrit");
	}
	fe.close();
    }
   
	public static void decompression(FileInputStream fr, FileOutputStream fw)throws IOException{
		
		System.out.println("Decompression : Huffman");
	
		
	}

}

