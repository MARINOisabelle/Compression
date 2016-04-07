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
	return tab;
    }
    
     public static int[][] arbreMin(int tab[]){
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
	int tab2 [][]= new int[2][2];
	tab2[0][0]=i1;
	tab2[0][1]=min1;
	tab2[1][0]=i2;
	tab2[1][1]=min2;
	return tab2;
       
    }
    public static Arbre arbreComp(LireBit fr){
	int tab[]=compteIter(fr);
	int tab2[][]=arbreMin(tab);
	Arbre principal = null;
	while(tab2[1][0]!=-1){
	    Feuille g = new Feuille(tab2[0][1],new Character((char)tab2[0][0]));
	    Feuille d = new Feuille(tab2[0][1],new Character((char)tab2[0][0]));
	    Arbre min = new Arbre(g,d);
	    principal = min.fusionne(principal);
	}
	if(tab2[0][0]!=-1){
	    principal.rajouteFeuille(new Feuille(tab2[0][1],new Character((char)tab2[0][0])));
	}
	
	return principal;
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

