
import bean.Person;
import bean.PersonDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Client extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      System.out.print("请输入用户名：");
      Scanner scan = new Scanner(System.in);
      String read = scan.nextLine();
      System.out.print("请输入密码：");
      scan = new Scanner(System.in);
      String read1 = scan.nextLine();
      PersonDAO dao = new PersonDAO();
      Person person = dao.check(read, read1);
      if (person != null) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../fxmlAndImg/tic-tac-toe.fxml"));
        loader.setController(new MatchFxml(primaryStage, "127.0.0.1", 8087, read));
        Pane rootPane = loader.load();
        Scene scene = new Scene(rootPane, rootPane.getPrefWidth(), rootPane.getPrefHeight());
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setResizable(false);
        primaryStage.show();
      } else {
        System.out.println("密码错误");
      }


    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
