import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;


class LempelZiv{

	Arbre a;
	int nextPoids;
	int nbBit;
	int puissance;

	public LempelZiv(){
		this.a =new Arbre(new Node(0));
		this.nextPoids=1;
		this.nbBit=1;
		this.puissance=1;
	}


					/* Fonctions pour la compression */ 

	public int[] int2tab(int o){
		int mask=0x80;
		if(o == -1)
			return null;
		int octet [] = new int[8];
		for(int i=0;i<8;i++){
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
			this.puissance++;
			this.nbBit=this.nbBit*2;
		}
		if(8-puissance>0){
			for(int i=8-this.puissance; i<8; i++){
				e.writeBit(octet[i]);
			}
		}
		if(bit==1 || bit==0){
			e.writeBit(bit);
		}
	
	}

	public  Node analyseOctetComp(LireBit l,EcrireBit e, Node n, int i)throws IOException{
		if(i<8){
			if(l.octet[i]==0 && n.left==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);
				//System.out.println("ajout 0");
				this.ecrireComp(e,n.poids, 0);	
				this.nextPoids ++;
				return analyseOctetComp(l,e, (Node)this.a.racine, i+1);

			}
			else if(l.octet[i]==0 && n.left!=null){
					return analyseOctetComp(l,e, (Node)n.left, i+1);

			}
			else if(l.octet[i]==1 && n.right==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);	
				this.ecrireComp(e,n.poids, 1);
				//System.out.println("ajout 1");	
				this.nextPoids ++;
				return analyseOctetComp(l,e, (Node)this.a.racine, i+1);
			}
			else if(l.octet[i]==1 && n.right!=null){
				return analyseOctetComp(l,e, (Node)n.right, i+1);
			}
		}
		return n;

	}

	public  void lireFichierComp(LireBit l, EcrireBit eb)throws IOException{
			Node n = (Node)this.a.racine;
			
			try{
				while(l.lire()!=false){	
					n=analyseOctetComp(l,eb, n, 0);
					ecrireComp(eb,n.poids, 2);
					/*for(int i=0;i<8;i++){
						System.out.println(l.octet[i]);
					}*/
					

				}
				eb.writeBit(1);
				for(int i=0;i<7;i++){
					eb.writeBit(0);
				}
				l.close();
			}
			catch(IOException e){
			System.out.println("faux");
			e.printStackTrace();
			}
   	}


	public static void compression(LireBit fr, EcrireBit fw)throws IOException{
		LempelZiv lz= new LempelZiv();
		lz.lireFichierComp(fr, fw);
	}

	/*Fonctions pour la décompression*/
	public  Node analyseOctetDecomp(LireBit l,EcrireBit e, Node n, int i)throws IOException{
		if(i<8){
			if(l.octet[i]==0 && n.left==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);
				//System.out.println("ajout 0");
				this.ecrireComp(e,n.poids, 0);	
				this.nextPoids ++;
				return analyseOctetComp(l,e, (Node)this.a.racine, i+1);

			}
			else if(l.octet[i]==0 && n.left!=null){
					return analyseOctetComp(l,e, (Node)n.left, i+1);

			}
			else if(l.octet[i]==1 && n.right==null){
				this.a.addNode(l.octet[i], this.nextPoids, n);	
				this.ecrireComp(e,n.poids, 1);
				//System.out.println("ajout 1");	
				this.nextPoids ++;
				return analyseOctetComp(l,e, (Node)this.a.racine, i+1);
			}
			else if(l.octet[i]==1 && n.right!=null){
				return analyseOctetComp(l,e, (Node)n.right, i+1);
			}
		}
		return n;

	}
	public  void lireFichierDecomp(LireBit l, EcrireBit eb)throws IOException{
			Node n = (Node)this.a.racine;
			
			try{
				while(l.lire()!=false){	
					n=analyseOctetDecomp(l,eb, n, 0);
					//ecrireDecomp(eb,n.poids, 2);
					for(int i=0;i<8;i++){
						System.out.println(l.octet[i]);
					}
					

				}
				l.close();
			}
			catch(IOException e){
			System.out.println("faux");
			e.printStackTrace();
			}
   	}



	public static void decompression(LireBit fr, EcrireBit fw)throws IOException{
		LempelZiv lz= new LempelZiv();
		lz.lireFichierDecomp(fr, fw);
		System.out.println("Decompression : Ziv");
	}

}
