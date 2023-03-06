package com.example.LexeicalAndSyntax;

//import com.example.LexeicalAndSyntax.Lexical.Lexical;
import com.example.LexeicalAndSyntax.Lexical.Lexical;
import com.example.LexeicalAndSyntax.Lexical.ListLexeme;
import com.example.LexeicalAndSyntax.Lexical.SyntaxAnalyze;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    public MenuBar menubar;
    public TableView tokensTable, Table2;
    public JFXTextArea codeTextArea;
    public JFXButton btnAnalyze;
    public JFXButton btnClear;
    public TableColumn SymLine;
    public TableColumn SymToken;
    public TableColumn SymType;
    public TableColumn SymAttVal;
    public TableColumn lineCol;
    public TableColumn attributeCol;
    public TableColumn attributeValeCol;
    public TableColumn tokenCol;
    public TextArea syntax;
    @FXML
    private Label welcomeText;
    @FXML
    public static void infoDialog(String select_a_part) {
        try{
        infoDialog(select_a_part);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lineCol.setCellValueFactory(new PropertyValueFactory<ListLexeme, Integer>("line"));
        tokenCol.setCellValueFactory(new PropertyValueFactory<ListLexeme,String>("tokentype"));
        attributeCol.setCellValueFactory(new PropertyValueFactory<ListLexeme, String>("value"));
        attributeValeCol.setCellValueFactory(new PropertyValueFactory<ListLexeme, String>("att"));

        SymLine.setCellValueFactory(new PropertyValueFactory<ListLexeme, Integer>("line"));
        SymToken.setCellValueFactory(new PropertyValueFactory<ListLexeme, String>("tokentype"));
        SymType.setCellValueFactory(new PropertyValueFactory<ListLexeme, String>("att"));
        SymAttVal.setCellValueFactory(new PropertyValueFactory<ListLexeme, String>("type"));

        btnAnalyze.disableProperty().bind(codeTextArea.textProperty().isEmpty());
        btnClear.disableProperty().bind(codeTextArea.textProperty().isEmpty());
    }

    @FXML
    private void clearCodeArea(){
        codeTextArea.setText("");
        tokensTable.getItems().clear();
        Table2.getItems().clear();
    }
    @FXML
    private void Syntax(){
        String Text = codeTextArea.getText();

        String [] lines = codeTextArea.getText().split("\n");
        Map<Integer, String> code = new HashMap<>();
        boolean lock = true; // To ignore multiple lines comments

        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            if(!line.strip().startsWith("//") && lock && !line.strip().startsWith("/*") && !line.isBlank())
                code.put(i+1, line);
            if(line.strip().startsWith("/*"))
                lock = false;
            if(line.strip().endsWith("*/"))
                lock = true;
        }
        try {
            Lexical l = new Lexical();
            tokensTable.setItems(FXCollections.observableList(l.startLexAnalysis(Text)));
            Table2.setItems(FXCollections.observableList(l.startLexAnalysis(Text)));

        }
        catch (Exception e){
            System.out.println(e);
        }

            Lexical sa = new Lexical();
            syntax.setText(sa.pass(Text));

    }
    @FXML
    private void analyze(){
        String Text = codeTextArea.getText();

        String [] lines = codeTextArea.getText().split("\n");
        Map<Integer, String> code = new HashMap<>();
        boolean lock = true; // To ignore multiple lines comments

        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            if(!line.strip().startsWith("//") && lock && !line.strip().startsWith("/*") && !line.isBlank())
                code.put(i+1, line);
            if(line.strip().startsWith("/*"))
                lock = false;
            if(line.strip().endsWith("*/"))
                lock = true;
        }
        Lexical l = new Lexical();

        try {

            tokensTable.setItems(FXCollections.observableList(l.startLexAnalysis(Text)));
            Table2.setItems(FXCollections.observableList(l.startLexAnalysis(Text)));

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}