package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class loginController {
    public PasswordField lozinkaPolje;
    public TextField korisnickoImePolje;
    public Button ulogujSeDugme;
    public Label InfoPolje;
    public static Korisnik ulogovaniKorisnik;

    public void ulogujSeDugmeKliknuto(MouseEvent mouseEvent) {
        String korisnickoIme = korisnickoImePolje.getText();
        String lozinka = lozinkaPolje.getText();

        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try{
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            rs = s.executeQuery("select * from korisnik");

            while(rs.next()){
                if(rs.getString(2).equals(korisnickoIme) && rs.getString(3).equals(lozinka)){
                    InfoPolje.setText("Uspjesno logovanje.");
                    ulogovaniKorisnik = new Korisnik(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                            rs.getString(5), rs.getString(6),rs.getString(7));
                    try{
                        Parent root = FXMLLoader.load(getClass().getResource("fxml/glavnaForma.fxml"));
                        Stage newStage = new Stage();
                        newStage.setTitle("Online prodaja tehnike.");
                        newStage.setScene(new Scene(root));
                        newStage.show();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                    return;
                }
            }
            InfoPolje.setText("Neuspjesno logovanje, pokusajte ponovo.");
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
    }

    public void ulogujSeDugmeEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            String korisnickoIme = korisnickoImePolje.getText();
            String lozinka = lozinkaPolje.getText();

            Connection c = null;
            Statement s = null;
            ResultSet rs = null;
            try{
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                rs = s.executeQuery("select * from korisnik");

                while(rs.next()){
                    if(rs.getString(2).equals(korisnickoIme) && rs.getString(3).equals(lozinka)){
                        InfoPolje.setText("Uspjesno logovanje.");
                        ulogovaniKorisnik = new Korisnik(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                                rs.getString(5), rs.getString(6),rs.getString(7));
                        try{
                            Parent root = FXMLLoader.load(getClass().getResource("fxml/glavnaForma.fxml"));
                            Stage newStage = new Stage();
                            newStage.setTitle("Online prodaja tehnike.");
                            newStage.setScene(new Scene(root));
                            newStage.show();
                        }catch(IOException ex){
                            ex.printStackTrace();
                        }
                        return;
                    }
                }
                InfoPolje.setText("Neuspjesno logovanje, pokusajte ponovo.");
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally {
                if (rs != null)
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                if (s != null)
                    try {
                        s.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                ConnectionPool.getInstance().checkIn(c);
            }
        }
    }
}
