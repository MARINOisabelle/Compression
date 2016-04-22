import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;


class LempelZiv{

	Arbre a;
	int nextPoids; // poids pour le prochain Node a ajouter
	int nbBit; //nb de Node dans l'arbre possible pour la puissance 
	int puissance; // puissance de 2 utile por coder les poids des node
	ArrayList<Integer[]> list;
	int power;
	long taille;


	public LempelZiv(long taille){
		this.a =new Arbre(new Node(0));
		this.nextPoids=1;
		this.nbBit=1;
		this.puissance=0;
		this.power=32;
		this.taille=taille-1;
		this.list = new ArrayList<Integer[]>();
		Integer[] tab = {-1,-1};
		list.add(0,tab);
	}
	public LempelZiv(){
		this.a =new Arbre(new Node(0));
		this.nextPoids=1;
		this.nbBit=1;
		this.puissance=0;
		this.power=32;
		this.list = new ArrayList<Integer[]>();
		Integer[] tab = {-1,-1};
		list.add(0,tab);
	}


					/* Fonctions pour la compression */ 

	public int[] int2tab(int o){ // sur 32 byte 
		if(o == -1)
			return null;
		long mask=(1073741824)*2L;
		int octet[]= new int[32];
		
		for(int i=0;i<power;i++){
			if((o & mask)==0){
			octet[i]=0;
			mask = mask>>1;
			}
			else{
			octet[i]=1;
			mask = 	mask>>1;
			}
		}
		return octet;
    }

	public void ecrireComp(EcrireBit e, int poids, int bit) throws IOException{
		int[] octet= int2tab(poids);
		if(this.nextPoids>this.nbBit){
			this.puissance++; 				System.out.println( "puissance " +puissance);
			this.nbBit=this.nbBit*2;
		}
		if(power-puissance>=0){
			for(int i=power-this.puissance; i<power; i++){
				e.writeBit(octet[i] );
			}
		}
		if(bit==1 || bit==0){
			e.writeBit(bit); 
		}
	
	}

	public  Node analyseOctetComp(LireBit l,EcrireBit e, Node n, int i)throws IOException{
		if(i<8){				//System.out.println(nextPoids + "next");
			if(l.octet[i]==0 && n.left==null){
				n.addNode(l.octet[i], this.nextPoids);
				//System.out.println("ajout 0 " + (n.poids));
				this.ecrireComp(e,n.poids, 0);	
				this.nextPoids ++;
				return analyseOctetComp(l,e, this.a.racine, i+1);

			}
			else if(l.octet[i]==0 && n.left!=null){
					return analyseOctetComp(l,e, (Node)n.left, i+1);

			}
			else if(l.octet[i]==1 && n.right==null){
				n.addNode(l.octet[i], this.nextPoids);	
				//System.out.println("ajout 1 " + (n.poids) );	
				this.ecrireComp(e,n.poids, 1);
				this.nextPoids ++;
				return analyseOctetComp(l,e, this.a.racine, i+1);
			}
			else if(l.octet[i]==1 && n.right!=null){
				return analyseOctetComp(l,e, (Node)n.right, i+1);
			}
		}
		return n;

	}

	public  void lireFichierComp(LireBit l, EcrireBit eb)throws IOException{
			Node n = (Node)this.a.racine;
			this.puissance=0;
			try{
				while(l.lire()!=false){	
					n=analyseOctetComp(l,eb, n, 0);
					/*for(int i=0;i<8;i++){
						System.out.println(l.octet[i]);
					}*/
					

				}
				//System.out.println(nextPoids + "next");
				this.ecrireComp(eb,n.poids, l.octet[7]);
				eb.writeLastBit();
				l.close();
			}
			catch(IOException e){
			System.out.println("faux");
			e.printStackTrace();
			}
   	}


	public static void compression(LireBit fr, EcrireBit fw) throws IOException{
		LempelZiv lz= new LempelZiv();
		lz.lireFichierComp(fr, fw);
		System.out.println("Compression : Ziv");
	}

	/*Fonctions pour la décompression*/

	public static void ecrireList(ArrayList<Integer> a,EcrireBit e)throws IOException{
		for(int i=a.size(); i>0; i--){
			e.writeBit(a.get(i-1)); 
		}

	}


	public int tab2int(int []tab){
		int res=0;
		for(int i=0; i<power; i++){
			if(tab[i]==1){		
				int j=i+1;
				while(j<power){
					tab[i]=tab[i]*2;
					j++;
				}
				res=res+tab[i];	
			}
		}
		return res;
	}

	public  ArrayList<Integer[]> ecrireDecomp(int pere, int bit,EcrireBit e)throws IOException{
			//System.out.println("dg " + bit + " pere " + pere + " " +list.size() );
			if(pere>=0 && (bit==0 || bit==1)){
				Integer t[]= new Integer[2];
				t[0]=pere; 
				t[1]=bit; 
				list.add(list.size(),t);
				Integer i=pere;
				ArrayList<Integer> ar = new ArrayList<Integer>();
				while(list.get(i)[0]!=-1){
					ar.add(this.list.get(i)[1]); 
					//System.out.println(" i " + i);
					i= list.get(i)[0];
					

				}
				ecrireList(ar, e);
				e.writeBit(bit);

			}
			
		return this.list;
	}


	public  int[] analyseOctetDecomp(LireBit l,EcrireBit e, int cur_tab , int cur_l, int[] tab)throws IOException{
			if(this.nextPoids>this.nbBit){
				this.puissance++;
				System.out.println( "puissance " +puissance);
				
				this.nbBit=this.nbBit*2;
			}
			if(taille==0){ System.out.println("null");
				//return null;
			}
			 if(this.puissance<=power){
				int k=0;
				for(k=cur_tab; k<puissance; k++){
						if(k+cur_l>=8){ break;}
						else{
							tab[k +power-this.puissance]=l.octet[cur_l+k];
							
						}
						
				} 
				cur_tab=k;
				cur_l=cur_l+k;			
				if(k==puissance) {//System.out.println("tab");
					/*for(int j=power-cur_tab; j<power; j++){
						System.out.println(tab[j]);

					}*/						
					int node = tab2int(tab);
					if(cur_l<8){// System.out.println("curl " +cur_l);
						this.ecrireDecomp(node, l.octet[cur_l], e);	
					}
					else{
						if(l.lire()!=false && taille>1){
							cur_l=0;
							taille--;
							this.ecrireDecomp(node, l.octet[0], e);		
						}
					}
					tab= new int[power];
					this.nextPoids++;
					cur_l++; 
				}
				if(cur_tab<puissance && cur_l>=7){ //System.out.println("tab " +cur_tab);
					if(l.lire()!=false){	
						taille--;
						return analyseOctetDecomp(l, e, cur_tab,-cur_tab, tab);	
					}
					//return null;
				}
				else if(cur_l<8 && cur_tab>=puissance){
					return analyseOctetDecomp(l, e, 0, cur_l, tab);	
				}			
			}	
		return tab;
	}

	public  void lireFichierDecomp(LireBit l, EcrireBit eb) throws IOException{
			int tab[]=new int[power];
			this.puissance=0;
			try{
				while(l.lire()!=false){	
					taille --;
					this.analyseOctetDecomp(l,eb, 0,0, tab);
				}
			}
			catch(IOException e){
				System.out.println("faux");
				e.printStackTrace();
			}
   	}



	public static void decompression(LireBit fr, EcrireBit fw, long taille)throws IOException{
		LempelZiv lz= new LempelZiv(taille);
		lz.lireFichierDecomp(fr, fw);
		System.out.println("Decompression : Ziv");
	}

}
