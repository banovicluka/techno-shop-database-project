package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KupovinaController {
    public Button kupiDugme;
    public ChoiceBox nacinPlacanjaMeni;
    public ListView specifikacijeLista;
    public ChoiceBox adresaMeni;
    public static int idArtikla;
    public Label infoLabel;

    @FXML
    public void initialize(){

    }

    public void kupiKliknuto(MouseEvent mouseEvent) {
        if(nacinPlacanjaMeni.getValue() != null && adresaMeni.getValue() != null){
            String adresa = adresaMeni.getValue().toString().split(", ")[0];
            String grad = adresaMeni.getValue().toString().split(", ")[1];
            String nacinPlacanja = (String) nacinPlacanjaMeni.getValue();
            int idStatusNarudzbe;
            int idNacinPlacanja;
            int idAdresa;
            int idKorisnik;
            int idDostavljac;
            int idRacun;

            int idZaposleni;
            int total;

            Connection c = null;
            Statement s = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            try{
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                //LocalDateTime now = LocalDateTime.now();
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                rs = s.executeQuery("select naStanju from artikalTehnika where idartikalTehnika='" + idArtikla + "';");
                rs.next();
                if(rs.getInt(1)>0){
                    cs = c.prepareCall("{call dobavi_random_zaposlenog()}");
                    rs = cs.executeQuery();
                    rs.next();
                    idZaposleni= rs.getInt(1);

                    rs = s.executeQuery("select cijena from artikaltehnika_info where idArtikla='" + idArtikla + "'");
                    rs.next();
                    total = rs.getInt(1);

                    int i;
                    s = c.createStatement();
                    i = s.executeUpdate("insert into racun values (null,'/',now(),'" + total + "','" + idZaposleni + "');");
                    rs = s.executeQuery("select last_insert_id()");
                    rs.next();
                    idRacun = rs.getInt(1);

                    cs = c.prepareCall("{call dobavi_random_dostavljaca()}");
                    rs = cs.executeQuery();
                    rs.next();
                    idDostavljac= rs.getInt(1);

                    rs =s.executeQuery("select idnacinPlacanja from nacinplacanja where nacinPlacanja = '" + nacinPlacanja + "'");
                    rs.next();
                    idNacinPlacanja = rs.getInt(1);

                    rs = s.executeQuery("select idstatusNarudzbe from statusnarudzbe where statusNarudzbe = 'Predato dostavljacu'");
                    rs.next();
                    idStatusNarudzbe = rs.getInt(1);
                    System.out.println(adresa);
                    rs = s.executeQuery("select idadresa from adresa where concat(imeUlice,' ',brojUlice) = '" + adresa + "';");
                    rs.next();
                    idAdresa = rs.getInt(1);

                    s = c.createStatement();
                    i = s.executeUpdate("insert into narudzba values(now(),'" + idStatusNarudzbe + "', '" +
                           idNacinPlacanja + "', '" + idAdresa + "', '" + loginController.ulogovaniKorisnik.idKorisnik +
                            "', '" + idDostavljac + "', '" + idRacun + "', '" + idArtikla + "');");
                    infoLabel.setText("Uspjesna narudzba.");
                    Stage stage = (Stage) kupiDugme.getScene().getWindow();
                    stage.close();
                }else{
                    infoLabel.setText("Neuspjesna narudzba, artikla nema na stanju.");
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
    }

}
