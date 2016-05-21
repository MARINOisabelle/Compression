import java.util.*;
public class CodeLettre{
    ArrayList<Integer> code;
    public CodeLettre(ArrayList<Integer> c){
	code=c;
    }
    public void afficheCode(){
	for(int i=0;i<code.size();i++){
	    System.out.print(code.get(i));
	}
	System.out.println(" ");
    }
}
