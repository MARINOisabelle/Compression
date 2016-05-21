import java.io.File;
import java.util.*;
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
	int tab[]=new int[256];
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
    public void addTri(ArrayList<Poids> a,Poids p){
	if(a.size()!=0){
	    if(a.get(a.size()-1).poids<=p.poids)
		a.add(p);
	    else{
		Poids p2 = a.remove(a.size()-1);
		ArrayList<Poids> a2 = new ArrayList<Poids>();
		while(p2.poids>p.poids && a.size()!=0){
		    a2.add(p2);
		    p2=a.remove(a.size()-1);
		}
		if(p2.poids<=p.poids){
		    a.add(p2);
		    a.add(p);
		}
		else{
		    a.add(p);
		    a.add(p2);
		}
		while(a2.size()>0){
		    a.add(a2.remove(a2.size()-1));		    
		}
	    }
	}
	else
	    a.add(p);
    }
     
  public void ecrireArbre(Arbre a,EcrireBit fw)throws IOException{
        ArrayList<Integer> list = new ArrayList<Integer>();
	a.racine.ListArbre(list,0,false);
	for(int i=0;i<list.size();i++){
	    fw.write(list.get(i)/256);
	    fw.write(list.get(i));
	    
	}
    }
    public void ecrireLettre(CodeLettre[] cl,EcrireBit fw,int lettre){
	try{
	    for(int i=0;i<cl[lettre].code.size();i++){
		    fw.writeBit(cl[lettre].code.get(i));
		}
	}
	catch(Exception e){
	    e.printStackTrace();
	    System.out.println("can't write");
	}
	    
    }
    public ArrayList<Poids> listeFeuille(LireBit fr){
	int tab[]=compteIter(fr);
	ArrayList<Poids> a = new ArrayList<Poids>();
	for(int i=0;i<tab.length;i++){
	    if(tab[i]!=0){
		addTri(a,new Feuille(tab[i],i));
	    }
	}
	return a;
    }
    public Arbre arbreCompr(LireBit fr){
	ArrayList<Poids> a = listeFeuille(fr);
	int taille = a.size();
	Arbre arbre = new Arbre(new Node(0)) ;
	while(taille>0){
	     Poids p1 = a.remove(0);
	    if(taille==1){
		Node racine = new Node(p1.poids);
		racine.left=p1;
		arbre.racine =racine;
		break;
	    }
	    
	    else{
		taille--;
		Poids p2=a.remove(0);
		taille--;
		Node racine = new Node(p1.poids+p2.poids);
		if(p1.poids>p2.poids){
		    racine.right =p2;
		    racine.left = p1;
		}
		else{
		    racine.right =p1;
		    racine.left = p2;
		}
		if(taille==0){
		    arbre.racine=racine;
		    break;
		}
		else{   
		    addTri(a,racine);
		    taille++;
		}
	    }
	}
	return arbre;
	
    }
	
	


    /*fonction qui récupère l'arbre de compression*/
    public ArrayList<Integer> listDec(LireBit l) throws IOException{
	int taille =l.read();
	while(taille%255==0){
	    taille=taille+l.read();
	}
	ArrayList<Integer> list = new ArrayList<Integer>();
	int tab[]=new int[3];
	for(int i=0;i<taille*3;i++){
	    int mult = l.read();
	   int j =l.read()+256*mult;
	   list.add(j);
	   
	}
	return list;
    }
    
	/*  fonction appeler dans les main de compresion et decompression */
    public  void compression(String fr, String fw)throws Exception{
	LireBit fl = new LireBit(new FileInputStream(new File(fr)));
	Arbre a = arbreCompr(fl);//premiere lecture du fichier pour construire l'arbre de compression
	fl.close();
	fl =  new LireBit(new FileInputStream(new File(fr)));
	EcrireBit fe = new EcrireBit(new FileOutputStream(new File(fw)));
	fe.write(1);//permet de préciser que le fichier est compresser avec Huffman
	int taille=a.taille();//recupere la taille de l'arbre
	while(taille>=255){//écrit la taille de l'arbre dans le fichier
	    fe.write(255);
	    taille=taille-255;
	}
	fe.write(taille);
	ecrireArbre(a,fe);//écrit l'arbre dans le fichier
	CodeLettre[] c = new CodeLettre[256];
	ArrayList<Integer> ar = new ArrayList<Integer>();
	a.racine.dfs(ar,c);//récupèration des codes bit à bit de chaque lettre
	int l;
	while((l=fl.read())!=-1 ){
	    ecrireLettre(c,fe,l);
	    }
	    fe.writeLastBit();
	fe.close();
	fl.close();
    }
    
    public  void decompression(LireBit fr, EcrireBit fw,long le)throws IOException{
	ArrayList<Integer> list = listDec(fr);
	int placement = 0;
	int taille =  list.size()/(255*3);
	for(long i=0;i<le-list.size()*2-2-taille;i++){
	    int b=0;
	    fr.lire();
	    for(int j=0;j<8;j++){
		if(list.get(placement+1)!=255 || list.get(placement+2)!=255){			
		    placement=list.get(placement+1+fr.octet[j]);
		}
		else{
		    fw.write(list.get(placement));
			j=j-1;
			placement = 0;
		}
	    }
	}
	    fr.lire();
	    int d;
	    for(d=0;d<8;d++){
		if(fr.octet[7-d]==1)
		    break;
	    }
	    for(int j=0;j<8-d;j++){
		if(list.get(placement)==255)
		    placement=list.get(placement+1+fr.octet[j]);
		else{
		    fw.write(list.get(placement));
		    placement=0;
		    j=j-1;
		}
	    }
    }
}



