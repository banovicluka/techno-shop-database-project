package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dodajAdresuController {
    public TextField nazivUliceTextField;
    public TextField brojUliceTextField;
    public TextField nazivGradaTextField;
    public TextField postanskiBrojTextField;
    public Button dodajButton;

    public void dodajButtonClicked(MouseEvent mouseEvent) {
        String nazivAdrese = nazivUliceTextField.getText();
        String nazivGrada = nazivGradaTextField.getText();
        String postansiBroj = postanskiBrojTextField.getText();
        String brojAdrese = brojUliceTextField.getText();
        if(nazivAdrese != null && nazivGrada != null && postansiBroj != null && brojAdrese != null) {
            Connection c = null;
            Statement s = null;
            Statement s1 = null;
            Statement s2 = null;
            int rs;
            int rs1;
            int rs3;
            ResultSet rs2 = null;
            ResultSet rs4 = null;
            try {
                c = ConnectionPool.getInstance().checkOut();
                s = c.createStatement();
                s1 = c.createStatement();
                s2 = c.createStatement();
                try {
                    rs = s.executeUpdate("insert into grad values (null,'" + nazivGrada + "','" + postansiBroj + "');");
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
                rs2 = s2.executeQuery("select idgrad from grad where nazivGrada='" + nazivGrada + "' and postanskiBroj='" + postansiBroj + "';");
                rs2.next();
                int idGrada = rs2.getInt(1);

                rs1 = s.executeUpdate("insert into adresa values (null,'" + nazivAdrese + "','" + brojAdrese +
                        "','" + idGrada + "');");

                rs4 = s1.executeQuery("select last_insert_id();");
                rs4.next();
                int idAdrese = rs4.getInt(1);

                rs3 = s.executeUpdate("insert into korisnik_has_adresa values ('" + loginController.ulogovaniKorisnik.idKorisnik
                    + "','" + idAdrese +"');");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs4 != null)
                    try {
                        rs4.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                if (rs2 != null)
                    try {
                        rs2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                if (s1 != null)
                    try {
                        s.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                if (s2 != null)
                    try {
                        s.close();
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

            Stage stage = (Stage) dodajButton.getScene().getWindow();
            stage.close();
        }
    }
}
