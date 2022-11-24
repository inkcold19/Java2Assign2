

import bean.Person;
import bean.PersonDAO;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class register extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    System.out.print("请输入用户名：");
    Scanner scan = new Scanner(System.in);
    String read = scan.nextLine();
    System.out.print("请输入密码：");
    scan = new Scanner(System.in);
    String read1 = scan.nextLine();
    PersonDAO dao = new PersonDAO();
    Person person = dao.reg(read, read1);
    System.out.print("注册成功");

  }
}
