package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Online prodaja tehnike");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        /*try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "lukaluka1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from adresa");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("imeUlice"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }*/
    }
}
