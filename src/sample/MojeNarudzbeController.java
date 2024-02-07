package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MojeNarudzbeController {

    public ListView mojeNarudzbe;
    public Button detaljiNarudzbeDugme;
    public Button otkaziNarudzbuDugme;
    public Label idNarudzbe;
    public Label idKorisnika;
    public Label vrstaTehnike;
    public Label RAM;
    public Label dimenzije;
    public Label procesor;
    public Label nazivProizvodjaca;
    public Label imeUlice;
    public Label nazivModela;
    public Label grad;
    public Label bojaArtikla;
    public Label godinaProizvodnje;
    public Label nazivDostavljaca;
    public Label internaMemorija;
    public Label kamera;
    public Label grafickaKartica;
    public Label brojUlice;
    public Label datumNarudzbe;
    public Label nazivZaposlenog;
    public Label cijena;
    public Label statusNarudzbe;
    public Label nacinPlacanja;
    public Button zatvoriDugme;

    @FXML
    public void initialize(){
        if(mojeNarudzbe!= null) {
            Connection c = null;
            Statement s = null;
            ResultSet rs = null;
            mojeNarudzbe.getItems().clear();
            try {
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                rs = s.executeQuery("select * from narudzba_kratkiinfo where idkorisnik='" + loginController.ulogovaniKorisnik.idKorisnik + "';");
                while (rs.next()) {
                    mojeNarudzbe.getItems().add(rs.getString(1) + ", " + rs.getString(3) + ", " +
                            rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) +
                            ", " + rs.getString(7) + ", " + rs.getString(8));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
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
    public void mojeNarudzbeKliknuto(MouseEvent mouseEvent) {
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

    public void detaljiNarudzbeKliknuto(MouseEvent mouseEvent) {
            if(mojeNarudzbe.getSelectionModel().getSelectedItem() != null){
             try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/detaljiNarudzbe.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                MojeNarudzbeController detaljiNarudzbeController = (MojeNarudzbeController) loader.getController();
                stage.setTitle("Detalji narudzbe");
                int idNarudzbe = Integer.parseInt(((String) mojeNarudzbe.getSelectionModel().getSelectedItem()).split(", ")[0]);
                Connection c = null;
                ResultSet rs = null;
                CallableStatement cs = null;
                Statement s = null;
                try {
                    c = ConnectionPool.getInstance().checkOut();
                    s = c.createStatement();
                    rs = s.executeQuery("select * from narudzba_sveinfo where idracun='" + idNarudzbe + "';");
                    rs.next();
                    detaljiNarudzbeController.idNarudzbe.setText("ID narudzbe: " + rs.getString(1));
                    detaljiNarudzbeController.idKorisnika.setText("ID korisnika: " + rs.getString(2));
                    detaljiNarudzbeController.vrstaTehnike.setText("Proizvod: " + rs.getString(3));
                    detaljiNarudzbeController.nazivProizvodjaca.setText("Proizvodjac: " + rs.getString(4));
                    detaljiNarudzbeController.nazivModela.setText("Model: " + rs.getString(5));
                    detaljiNarudzbeController.RAM.setText("RAM: " + rs.getString(6) + " GB");
                    detaljiNarudzbeController.internaMemorija.setText("Interna memorija: " + rs.getString(7) + " GB");
                    detaljiNarudzbeController.dimenzije.setText("Dimenzije: " + rs.getString(8));
                    detaljiNarudzbeController.kamera.setText("Kamera: " + rs.getString(9) + " MP");
                    detaljiNarudzbeController.procesor.setText("Procesor: " + rs.getString(10));
                    detaljiNarudzbeController.grafickaKartica.setText("Graficka kartica: " + rs.getString(11));
                    detaljiNarudzbeController.godinaProizvodnje.setText("Godina proizvodnje: " + rs.getString(12));
                    detaljiNarudzbeController.bojaArtikla.setText("Boja: " + rs.getString(13));
                    detaljiNarudzbeController.cijena.setText("Cijena: " + rs.getString(14));
                    detaljiNarudzbeController.datumNarudzbe.setText("Datum narudzbe: " + rs.getString(15));
                    detaljiNarudzbeController.statusNarudzbe.setText("Status narudzbe: " + rs.getString(16));
                    detaljiNarudzbeController.nacinPlacanja.setText("Nacin placanja: " + rs.getString(17));
                    detaljiNarudzbeController.imeUlice.setText("Ulica: " + rs.getString(18));
                    detaljiNarudzbeController.brojUlice.setText("Broj ulice: " + rs.getString(19));
                    detaljiNarudzbeController.grad.setText("Grad: " + rs.getString(20));
                    detaljiNarudzbeController.nazivDostavljaca.setText("Dostavljac: " + rs.getString(21) + " " + rs.getString(22));
                    detaljiNarudzbeController.nazivZaposlenog.setText("Racun izdao: " + rs.getString(23) + " " + rs.getString(24));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
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
                stage.show();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void otkaziNarudzbuKliknuto(MouseEvent mouseEvent) {
        if(mojeNarudzbe.getSelectionModel().getSelectedItem() != null) {
            int idNarudzbe = Integer.parseInt(((String)mojeNarudzbe.getSelectionModel().getSelectedItem()).split(", ")[0]);
            Connection c = null;
            Statement s = null;
            int rs1 = 0;
            ResultSet rs = null;
            try{
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                rs1 = s.executeUpdate("delete from narudzba where racun_idracun='" + idNarudzbe + "';");
                mojeNarudzbe.getItems().clear();
                rs = s.executeQuery("select * from narudzba_kratkiinfo where idkorisnik='" + loginController.ulogovaniKorisnik.idKorisnik + "';");
                while(rs.next()){
                    mojeNarudzbe.getItems().add(rs.getString(1) + ", " + rs.getString(3) + ", " +
                            rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) +
                            ", " + rs.getString(7) + ", " + rs.getString(8));
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally {
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

    public void zatvoriDugmeKliknuto(MouseEvent mouseEvent) {
        Stage stage = (Stage) zatvoriDugme.getScene().getWindow();
        stage.close();
    }
}
