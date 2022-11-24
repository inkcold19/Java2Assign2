import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDAO {

  private Connection conn = null;
  private Statement st = null;

  public PersonDAO() {
    conn = DBConnection.getConnection();
    try {
      st = conn.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 检查用户名密码
   *
   * @param username
   * @param password
   * @return
   */
  public Person check(String username, String password) {
    String sql =
        "select * from user where username='" + username + "' and password='" + password + "'";
    ResultSet rs;
    Person person = null;
    try {
      rs = st.executeQuery(sql);
      if (rs.next()) {
        person = new Person();
        person.setId(rs.getInt("id"));
        person.setUsername(rs.getString("username"));
        person.setPassword(rs.getString("password"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return person;
  }

  public Person reg(String username, String password) {
    String sql =
        "INSERT INTO user(username, password,win,loss) VALUES ( '" + username + "', '" + password
            + "','0','0');";
    Person person = null;
    try {
      st.executeUpdate(sql);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return person;
  }


  public void win(String username) {
    String sql = "update user set win=win+1 where username='" + username + "'";
    try {
      st.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void loss(String username) {
    String sql = "update user set loss=win+1 where username='" + username + "'";
    try {
      st.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
