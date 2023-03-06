package com.example.LexeicalAndSyntax.Lexical;

import java.util.*;

public class Lexical {
    public int line;
    public int pos;
    public int position;
    public char chr;
    public int attributeValue=0;
    public String s;
    ArrayList<Token> symbolTable = new ArrayList<Token>();

    Map<String, TokenType> keywords = new HashMap<>();
    public List<ListLexeme> listLexeme = new ArrayList<>();
    public List<String> Valid = new ArrayList<>();
    public Lexical() {

    }

    public String getAttributeValue() {
        this.attributeValue++;
        return String.valueOf(this.attributeValue);
    }

    //Token class is basically used to store the data of the token that is read
    //It stores the Tokens Token Type, Value, Line Number, Position in the Line
    static class Token {
        public TokenType tokentype;
        public String lex;
        public int line;
        public int pos;
        public String att;

        public String val;
        Token(TokenType token, String lex, int line, int pos, String att,String val) {
            this.tokentype = token; this.lex = lex; this.line = line; this.pos = pos; this.att = att;this.val = val;
        }
        @Override
        public String toString() {
            String result = String.format("%5d  %5d %-15s", this.line, this.pos, this.tokentype);
            switch (this.tokentype) {
                case Integer:
                    result += String.format("  %4s", lex);
                    break;
                case ID:
                    result += String.format(" %s", lex);
                    break;
                case String:
                    result += String.format(" \"%s\"", lex);
                    break;
            }
            return result;
        }
    }

    static enum TokenType {
        End_of_input,  Op_divide, Op_less,OOP,AOP,ROP,
        Op_greater,  Op_equal,  Op_assign, IF,ELSE,DO,WHILE,INT,STRING,
        ID, Integer, String,  error
    }

     Token error(int line, int pos, String msg) {
        if (line > 0 && pos > 0) {
            //MainController.infoDialog(msg+" in line "+ line + "pos"+ pos);
            System.out.printf("%s in line %d, pos %d\n", msg, line, pos);
        } else {
            System.out.println(msg);
        }
         getNextChar();
         return new Token(TokenType.error, "msg", line, pos,"error","-");
        //System.exit(1);
    }

    public Lexical(String source) {
        this.line = 1;
        this.pos = 0;
        this.position = 0;
        this.s = source;
        this.chr = this.s.charAt(0);

        this.keywords.put("if", TokenType.IF);
        this.keywords.put("else", TokenType.ELSE);
        this.keywords.put("do", TokenType.DO);
        this.keywords.put("while", TokenType.WHILE);
        this.keywords.put("int", TokenType.INT);
        this.keywords.put("string", TokenType.STRING);
    }

    Token char_lit(int line, int pos) {
        char c = getNextChar(); // skip opening quote
        int n = (int)c;
        if (c == '\'') {
            error(line, pos, "empty character constant");
        } else if (c == '\\') {
            c = getNextChar();
            if (c == 'n') {
                n = 10;
            } else if (c == '\\') {
                n = '\\';
            } else {
                error(line, pos, String.format("unknown escape sequence \\%c", c));
            }
        }
        if (getNextChar() != '\'') {
            error(line, pos, "multi-character constant");
        }
        getNextChar();

        return new Token(TokenType.Integer, "int" + n, line, pos, String.format("%5s",getAttributeValue()),"-");
    }


    Token string_literal(char start, int line, int pos) {
        String result = "";
        while (getNextChar() != start) {
            if (this.chr == '\u0000') {
                error(line, pos, "EOF while scanning string literal");
            }
            if (this.chr == '\n') {
                error(line, pos, "EOL while scanning string literal");
            }
            result += this.chr;
        }
        getNextChar();
        return new Token(TokenType.String, result, line, pos,getAttributeValue(),"-");

    }
    Token div_or_comment(int line, int pos) {

        //Improved & Tested Code
        switch (getNextChar()){
            case '/':
                while(true){
                    if(this.chr == '\n') {
                        //System.out.println("End of line");
                        getNextChar();
                        return getToken(); }

                    else if (this.chr == '\u0000') {
                        error(line, pos, "EOF in comment"); }
                    else {
                        getNextChar();  }
                }
            case '*':
                while (true) {
                    if (this.chr == '\u0000') {
                        error(line, pos, "EOF in comment");
                    }
                    else if (this.chr == '*') {
                        if (getNextChar() == '/') {
                            getNextChar();
                            return getToken();
                        }
                    }
                    else {
                        getNextChar();
                    }
                }

            default:
                return new Token(TokenType.Op_divide, "/", line, pos,getAttributeValue(),"-");
        }
    }

    Token identifier_or_integer(int line, int pos) {
        boolean is_number = true;
        String text = "";

        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '_') {
            text += this.chr;
            if (!Character.isDigit(this.chr)) {
                is_number = false;
            }
            getNextChar();
        }
        if (text.equals("")) {
            error(line, pos, String.format("identifier_or_integer unrecognized character: (%d) %c", (int)this.chr, this.chr));
        }
         if (Character.isDigit(text.charAt(0))) {
            if (!is_number) {
                error(line, pos, String.format("invalid number: %s", text));
            }
            return new Token(TokenType.Integer, text, line, pos,getAttributeValue(),"-");
        }
        if (this.keywords.containsKey(text)) {
        }
        Valid.add(text);
        return new Token(TokenType.ID, text, line, pos,getAttributeValue(),text);
    }
    Token getToken() {
        int line, pos;
        while (Character.isWhitespace(this.chr)) {
            getNextChar();
        }
        line = this.line;
        pos = this.pos;

        switch (this.chr) {
            case '\u0000': return new Token(TokenType.End_of_input, "NULL", this.line, this.pos,getAttributeValue(),"-");
            case '/': return div_or_comment(line, pos);
            case '\'': return char_lit(line, pos);
            case '<':
                if (getNextChar() == '=') {
                    getNextChar();
                    return new Token(TokenType.ROP, "<=", line, pos,"LE","-");
                }
                if(getNextChar() == '>'){
                    getNextChar();
                    return new Token(TokenType.ROP, "<>", line,pos,"NE","-");
                }
                if (TokenType.Op_less == TokenType.End_of_input) {
                    error(line, pos, String.format("follow: unrecognized character: (%d) '%c'", (int)this.chr, this.chr));
                }
                return new Token(TokenType.ROP, "<", line, pos,"LT","-");

            case '>':
                if (getNextChar() == '=') {
                    getNextChar();
                    return new Token(TokenType.ROP, ">=", line, pos,"GE","-");
                }
                if (TokenType.Op_greater == TokenType.End_of_input) {
                    error(line, pos, String.format("follow: unrecognized character: (%d) '%c'", (int)this.chr, this.chr));
                }
                return new Token(TokenType.ROP, ">", line, pos,"GT","-");
            case '=':
                if (getNextChar() == '=') {
                    getNextChar();
                    return new Token(TokenType.Op_equal, "==", line, pos,"EQ","-");
                }
                if (TokenType.Op_assign == TokenType.End_of_input) {
                    error(line, pos, String.format("follow: unrecognized character: (%d) '%c'", (int)this.chr, this.chr));
                }
                return new Token(TokenType.ROP, "=", line, pos,"AS","-");
            case '"': return string_literal(this.chr, line, pos);
            case '{': getNextChar(); return new Token(TokenType.OOP, "{", line, pos,"OB","-");
            case '}': getNextChar(); return new Token(TokenType.OOP, "}", line, pos,"CB","-");
            case '(': getNextChar(); return new Token(TokenType.OOP, "(", line, pos,"OP","-");
            case ')': getNextChar(); return new Token(TokenType.OOP, ")", line, pos,"CP","-");
            case '+': getNextChar(); return new Token(TokenType.AOP, "+", line, pos,"AD","-");
            case '-': getNextChar(); return new Token(TokenType.AOP, "-", line, pos,"SB","-");
            case '*': getNextChar(); return new Token(TokenType.AOP, "*", line, pos,"ML","-");
            case ';': getNextChar(); return new Token(TokenType.OOP, ";", line, pos,"TR","-");

            default: return identifier_or_integer(line, pos);
        }
    }

    //Handles Null Characters and Newlines
    //Also increments position and line where needed
    char getNextChar() {
        this.pos++;
        this.position++;
        if (this.position >= this.s.length()) {
            this.chr = '\u0000';
            return this.chr;
        }
        this.chr = this.s.charAt(this.position);
        if (this.chr == '\n') {
            this.line++;
            this.pos = 0;
        }
        return this.chr;
    }

    void printTokens() {
        Token t;
        while ((t = getToken()).tokentype != TokenType.End_of_input) {

            try {
                if (Integer.parseInt(t.att) > 0 && Integer.parseInt(t.att) < 50)
                    symbolTable.add(t);
            }
            catch (Exception e){}
            listLexeme.add(new ListLexeme(t.tokentype.toString(), t.lex, t.line,t.pos,t.att,t.val));

        }
    }

    public void printSymbolTable() {

        System.out.println("Attribute \t Token Name \t Type \t\t Value");

        for (int i = 0; i < symbolTable.size(); i++){
            System.out.println(listLexeme.get(i).getValue());
        }


    }
int i =0;
    public String pass(String Text){
        Lexical l = new Lexical(Text);
        l.printTokens();
        System.out.println("\n\n\n-SYMBOL TABLE-");
        l.printSymbolTable();

        System.out.println("--------------------------------------------------------");
        SyntaxAnalyze SA = new SyntaxAnalyze();
        String msg = SA.set(l.Valid, Text);

        if(Objects.equals(msg, "")){
            return "compiled Successfully" + Text;
        }
        return Text+" Unsuccessful compilation: "+msg;}

     public  List<ListLexeme> startLexAnalysis(String Text) {
            Lexical l = new Lexical(Text);
            l.printTokens();
            System.out.println("\n\n\n-SYMBOL TABLE-");
            l.printSymbolTable();
            return l.listLexeme;
    }
}
