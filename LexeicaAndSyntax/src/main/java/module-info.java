module com.example.lexeicaandsyntax {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.example.LexeicalAndSyntax to javafx.fxml;
    opens com.example.LexeicalAndSyntax.Lexical to javafx.fxml;
    exports com.example.LexeicalAndSyntax;
    exports com.example.LexeicalAndSyntax.Lexical;
}