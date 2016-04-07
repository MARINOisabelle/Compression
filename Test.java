import java.io.*;
public class Test{
    public static void main(String[]args){
	try{
	    LireBit l = new LireBit(new FileInputStream(new File("mon.txt")));
	    try{
		l.lire();
		for(int i=0;i<8;i++){
		    System.out.println(l.octet[i]);
		}
		Huffman.arbreComp(l);
		l.close();
	    }
	    catch(IOException e){
		System.out.println("faux");
		e.printStackTrace();
	    }
	}
	catch(FileNotFoundException e){
	    e.printStackTrace();
	}
    }
}
