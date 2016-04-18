import java.io.*;
public class Test{
    public static void main(String[]args) throws Exception{
	try{
	    
	    try{
		Huffman huffman = new Huffman();
		huffman.compression("mon.txt","compression");
		
		
		
	    }
	    catch(IOException e){
		System.out.println("faux");
		e.printStackTrace();
	    }
	}
	catch(FileNotFoundException e){
	    e.printStackTrace();
	    System.out.println("ne marche a");
	}
    }
    public static void afficheTab(int tab[]){
	for(int i=97;i<=127;i++){
	    System.out.println(tab[i]);
	}
    }
}
