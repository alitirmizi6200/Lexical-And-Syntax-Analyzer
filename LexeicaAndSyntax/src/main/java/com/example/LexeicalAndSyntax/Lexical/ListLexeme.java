package com.example.LexeicalAndSyntax.Lexical;


public class ListLexeme {
    public String tokentype;
    public String value;
    public int line;
    public int pos;
    public String att;

    public String type;

    public String error;

    public ListLexeme(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public ListLexeme(String tokentype, String lex, int line, int pos, String att, String type) {
        setTokentype(tokentype);
        setAtt(att);
        setValue(lex);
        setPos(pos);
        setLine(line);
        setType(type);

    }
    public ListLexeme( int line, int pos, String error) {
        setPos(pos);
        setLine(line);
        setType(error);

    }
    public void setValue(String value) {

        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTokentype() {
        return tokentype;
    }

    public void setTokentype(String tokentype) {
        this.tokentype = tokentype;
    }

    public String getLex() {
        return value;
    }



    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }
}
