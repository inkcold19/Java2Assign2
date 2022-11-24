

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

  private static List<Socket> socketList = new ArrayList<>();
  ServerSocket server = null;
  Socket socket = null;
  int gamecount = 1;

  public Server(Socket socket) {
    super("客户端 ：" + (socketList.size() + 1));
    this.socket = socket;
    socketList.add(socket);
    System.out.println("监听到对象" + socket);
    System.out.println(getName() + " 进入棋盘室 ");
    if (socketList.size() > 0 && socketList.size() % 2 == 0) {
      System.out.println("够两个人，开始配对-1" + socket);
      sendMessage(socket, "游戏" + gamecount + "开始游戏-1");
      sendMessage(socketList.get(socketList.size() - 2), "游戏" + gamecount + "开始游戏-2");
      gamecount++;
    } else {
      sendMessage(socket, "请等待");
    }
  }


  public void sendMessage(Socket socket1, String message) {
    Data data = new Data();
    data.setMessage(message);
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket1.getOutputStream());
      oos.writeObject(data);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendData(Socket socket1, Data data) {

    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket1.getOutputStream());
      oos.writeObject(data);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        try {
          Data data = (Data) in.readObject();
          System.out.println("服务器端收到信息 x" + data.getX() + "y" + data.getY());
          String win = data.getWinner();

          if (win != null && !"".equals(win)) {
            System.out.println("获胜");
            int i = socketList.indexOf(socket);
            data.setMessage("获胜");
            sendData(socket, data);
            data.setMessage("对手获胜");
            if (i % 2 == 0) {
              sendData(socketList.get(i + 1), data);
            } else {
              sendData(socketList.get(i - 1), data);
            }
            return;
          }
          String mess = data.getMessage();
          if ("平局".equals(mess)) {
            System.out.println("平局");
            int i = socketList.indexOf(socket);
            data.setMessage("平局");
            sendData(socket, data);
            data.setMessage("平局");
            if (i % 2 == 0) {
              sendData(socketList.get(i + 1), data);
            } else {
              sendData(socketList.get(i - 1), data);
            }
            return;
          }
          if ("退出".equals(mess)) {
            System.out.println("退出");
            int i = socketList.indexOf(socket);
            //   sendMessage(socket,"退出");
            if (i % 2 == 0) {
              sendMessage(socketList.get(i + 1), "对手退出");
            } else {
              sendMessage(socketList.get(i - 1), "对手退出");
            }
            return;
          }
          Data data1 = new Data();
          data1.setX(data.getX());
          data1.setY(data.getY());
          data1.setMessage("下棋");
          int i = socketList.indexOf(socket);
          if (i % 2 == 0) {
            sendData(socketList.get(i + 1), data1);
          } else {
            sendData(socketList.get(i - 1), data1);
          }

        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }


      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
