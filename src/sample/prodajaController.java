package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class prodajaController {

    public HashMap<Integer, Integer> artikalID = new HashMap<>();
    public HashMap<Integer, Integer> narudzbeID = new HashMap<>();

    public ListView listaProizvoda;
    public ImageView slika;

    public KupovinaController kupovinaKontroler;


    @FXML
    private void initialize(){

    }
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

    public void kompjuteriKliknuto(MouseEvent mouseEvent) {
        listaProizvoda.getItems().clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        artikalID.clear();
        //slika.setImage(new Image("slike/monitor.png"));
        try{
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            rs = s.executeQuery("select * from artikalTehnika_info where vrstaTehnike = 'KOMPJUTER' and naStanju>0");
            int i = 0;
            while(rs.next()){
                listaProizvoda.getItems().add(rs.getString(1) + ", " + rs.getString(2) + ", " +
                        rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) +
                        ", " + rs.getString(6) + " KM");
                artikalID.put(i++,rs.getInt(1));
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
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
    }

    public void laptopiKliknuto(MouseEvent mouseEvent) {
        listaProizvoda.getItems().clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        artikalID.clear();
        try{
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            rs = s.executeQuery("select * from artikalTehnika_info where vrstaTehnike = 'LAPTOP' and naStanju>0");
            int i = 0;
            while(rs.next()){
                listaProizvoda.getItems().add(rs.getString(1) + ", " + rs.getString(2) + ", " +
                        rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) +
                        ", " + rs.getString(6) + " KM");
                artikalID.put(i++,rs.getInt(1));
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
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
    }

    public void telefoniKliknuto(MouseEvent mouseEvent) {
        listaProizvoda.getItems().clear();
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        artikalID.clear();
        //
        try{
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            rs = s.executeQuery("select * from artikalTehnika_info where vrstaTehnike = 'TELEFON' and naStanju>0");
            int i = 0;
            while(rs.next()){
                listaProizvoda.getItems().add(rs.getString(1) + ", " + rs.getString(2) + ", " +
                        rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5) +
                        ", " + rs.getString(6) + " KM");
                artikalID.put(i++,rs.getInt(1));
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
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
    }

    public void odaberiKliknuto(MouseEvent mouseEvent) {
        if(listaProizvoda.getSelectionModel().getSelectedItem() != null) {
            Stage stage = null;
            try {
                stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/kupovina.fxml"));
                Parent root = loader.load();
                kupovinaKontroler = loader.getController();
                stage.setTitle("Kupovina proizvoda");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                //stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String odabraniArtikal = (String) listaProizvoda.getSelectionModel().getSelectedItem();
            String parametri[] = odabraniArtikal.split(", ");
            Connection c = null;
            CallableStatement cs = null;
            Statement s = null;
            ResultSet rs = null;
            try{
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                rs = s.executeQuery("select * from artikalSpecifikacije_info where idArtikla = " + parametri[0] + "");
                while(rs.next()){
                    kupovinaKontroler.idArtikla = Integer.parseInt(parametri[0]);
                    kupovinaKontroler.specifikacijeLista.getItems().add(parametri[1]);
                    kupovinaKontroler.specifikacijeLista.getItems().add("Proizvodjac: " + parametri[2]);
                    kupovinaKontroler.specifikacijeLista.getItems().add("Model: " + parametri[3]);
                    kupovinaKontroler.specifikacijeLista.getItems().add("Boja: " + parametri[4]);
                    kupovinaKontroler.specifikacijeLista.getItems().add("RAM memorija: " + rs.getString(7) + "GB");
                    kupovinaKontroler.specifikacijeLista.getItems().add("Interna memorija: " + rs.getString(8) + "GB");
                    kupovinaKontroler.specifikacijeLista.getItems().add("Dimenzije: " + rs.getString(9));
                    kupovinaKontroler.specifikacijeLista.getItems().add("Kamera: " + rs.getString(10) + "MP");
                    kupovinaKontroler.specifikacijeLista.getItems().add("Procesor: " + rs.getString(11));
                    kupovinaKontroler.specifikacijeLista.getItems().add("Graficka kartica: " + rs.getString(12));
                    kupovinaKontroler.specifikacijeLista.getItems().add("Godina proizvodnje: " + rs.getString(13));
                    kupovinaKontroler.specifikacijeLista.getItems().add("CIJENA: " + rs.getString(6));
                    kupovinaKontroler.nacinPlacanjaMeni.getItems().add("GOTOVINA");
                    kupovinaKontroler.nacinPlacanjaMeni.getItems().add("KARTICA");
                    cs = c.prepareCall("{call nabavi_adrese_korisnika(?)}");
                    cs.setInt(1,loginController.ulogovaniKorisnik.idKorisnik);
                    rs = cs.executeQuery();
                    while(rs.next()){
                        kupovinaKontroler.adresaMeni.getItems().add(rs.getString(1) + " "
                                + rs.getString(2) + ", " + rs.getString(3));
                    }
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
                if (s != null)
                    try {
                        s.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                ConnectionPool.getInstance().checkIn(c);

            }
            stage.show();
        }
    }
}
