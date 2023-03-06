package com.example.LexeicalAndSyntax.Lexical;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class SyntaxAnalyze {
    public String error;
    public String msg;
    public String errormsg = "";
    public int line;
    public int pos;

    public String valid;
    public SyntaxAnalyze(){
    }
    public SyntaxAnalyze(String valid) {
        this.valid = valid;
    }
    public List<String> Valid1 = new ArrayList<>();
    String last,next;
    List<String> Stack = new ArrayList<>();
    List<String> Stack1 = new ArrayList<>();


    boolean match(String str, List<String> analyze){
        for (int i = 0; i < analyze.size(); i++){
            if(str.contains(analyze.get(i))){return true;}
            else {return false;}
        }
        return false;
    }

    String set(List<String> Valid,String Text){
        Valid1 = Valid;
        System.out.println(Valid1.get(0) + " Valid check " );
int i=0;
        for(String str : Text.split(" ")){

            if(str.contains("(")){ Stack1.add("("); }
            else {}

            if(str.contains(")")){ Stack.add("("); }
            else if(match(str,Valid1)){}
            else if(str.contains("+")){}
            else if(str.contains("*")){}
            else{
                System.out.println(Valid1.contains(str));
                errormsg+= "invalid character \t"+ str;
            }
            i++;
        }
        System.out.println(i);
        System.out.println(errormsg);
        if(Stack.equals(Stack1) && error==""){
            msg = "sucessful Compiled";
            System.out.println(msg+ errormsg);
        }
        else{msg = " Compilation error at value : "+errormsg;}

        return errormsg;

    }

}
