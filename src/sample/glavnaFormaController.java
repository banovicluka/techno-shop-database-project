package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class glavnaFormaController {
    public Label imeLabela;
    public Label korisnickoImeLabela;
    public Label adreseLabela;
    public Button dodajAdresuDugme;
    public Label telefonLabela;
    public Label prezimeLabela;

    public void mojeNarudzbeKliknuto(MouseEvent mouseEvent) {
        try {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/mojeNarudzbe.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void prodajaKliknuta(MouseEvent mouseEvent) {
        try {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/prodaja.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void mojNalogKliknuto(MouseEvent mouseEvent) {
        try {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/mojNalog.fxml"));
            Scene scene = new Scene(root);
            ((Label)root.getChildrenUnmodifiable().get(1)).setText("Korisnicko ime: " + loginController.ulogovaniKorisnik.korisnickoIme);
            ((Label)root.getChildrenUnmodifiable().get(3)).setText("Telefon korisnika: " + loginController.ulogovaniKorisnik.telefon);
            ((Label)root.getChildrenUnmodifiable().get(2)).setText("Ime korisnika: " + loginController.ulogovaniKorisnik.ime);
            ((Label)root.getChildrenUnmodifiable().get(4)).setText("Prezime korisnika: " + loginController.ulogovaniKorisnik.prezime);
            Connection c = null;
            ResultSet rs = null;
            CallableStatement cs = null;
            try {
                c = ConnectionPool.getInstance().checkOut();
                cs = c.prepareCall("{call nabavi_adrese_korisnika(?)}");
                cs.setInt(1,loginController.ulogovaniKorisnik.idKorisnik);
                rs = cs.executeQuery();
                while(rs.next()){
                    ((Label)root.getChildrenUnmodifiable().get(5)).setText(((Label)root.getChildrenUnmodifiable().get(5)).getText() + "\n" + rs.getString(1) +
                            " " + rs.getString(2) + ", " + rs.getString(3));
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally {
                if (rs != null)
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                if (cs != null)
                    try {
                        cs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                ConnectionPool.getInstance().checkIn(c);
            }
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void dodajAdresuDugmeKliknuto(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/dodajAdresuForma.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Dodaj adresu");
            newStage.initModality(Modality.APPLICATION_MODAL);
            //newStage.setHeight(280);
            //newStage.setWidth(540);
            newStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
