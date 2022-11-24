

import bean.PersonDAO;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class MatchFxml {
    @FXML
    private ImageView im00, im01, im02, im10, im11, im12, im20, im21, im22;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;
    private ImageView im;
    private boolean circleTurn = true, netPlay = false, enterEver = false, firstTime = true;
    // [0][x]记录是否已经有子，[1][x]记录谁的子，1为circle的子,-1为cross的子
    private int[][] step = new int[2][10];
    private int x, y, flag = 0;
    private String winner;
    private Stage stage;
    private Data data;
    private Socket socket;
    private boolean isstart=false;
    private String  username;
    int docount=0;
    boolean nodiaoxian=true;
    public  MatchFxml(Stage stage, String host, int port,String username) throws Exception {
        this.username=username;
        socket = new Socket("127.0.0.1", port);
        this.stage = stage;
        netPlay = true;

        /*  this.socket = new Socket(hosts, port);*/
        this.os = socket.getOutputStream();
        this.is = socket.getInputStream();
        SocketThread socketThread = new SocketThread();
        socketThread.start();
        stage.setOnCloseRequest(event -> {

            data = new Data();

            data.setX(x);
            data.setY(y);
            data.setMessage("退出");
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }


    public MatchFxml(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void gridPaneOnMouseMoved() throws Exception {

    }

    @FXML
    private void on_btnClose_clicked(ActionEvent actionEvent) {

        String aa="1";
        int s22;

    }
    @FXML
    public void exitApplication(ActionEvent event) {
        String aa="1";
        int s22;

        ((Stage)stage.getScene().getWindow()).close();
    }

    @FXML
    public void gridPaneOnMouseClicked(MouseEvent mouseEvent) throws Exception {
        if(isstart==false){
            return;
        }
        isstart=false;
        x = (int) (mouseEvent.getX() / 100);
        y = (int) (mouseEvent.getY() / 100);
        setChess(x, y, circleTurn);
        winner = checkWin();
        if (data == null) {
            data = new Data();
        }
        if(circleTurn==true){
            docount++;
            if(docount==5&&winner.equals("")){
                data.setMessage("平局");

            }
        }


        data.setX(x);
        data.setY(y);
        data.setWinner(winner);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(data);
        oos.flush();
        firstTime = enterEver = false;

        if (!"".equals(winner)) {
            anchorPane.setDisable(true);
            stage.setTitle(winner + " win!");
            data.setWinner(winner);
        }
    }

    private void setChess(int x, int y, boolean circleTurn) {
        im = getImageView(x, y);
        if (circleTurn && step[0][y * 3 + x] == 0) {
            im.setImage(new Image("/fxmlAndImg/111.png"));
            step[1][y * 3 + x] = 1;
        } else if (!circleTurn && step[0][y * 3 + x] == 0) {
            im.setImage(new Image("/fxmlAndImg/222.png"));
            step[1][y * 3 + x] = -1;
        }
        step[0][y * 3 + x] = 1;
    }

    private ImageView getImageView(int x, int y) {
        ImageView im = null;
        if (x == 0) {
            switch (y) {
                case 0:
                    im = im00;
                    break;
                case 1:
                    im = im01;
                    break;
                case 2:
                    im = im02;
                    break;
                default:
                    im = null;
                    break;
            }
        } else if (x == 1) {
            switch (y) {
                case 0:
                    im = im10;
                    break;
                case 1:
                    im = im11;
                    break;
                case 2:
                    im = im12;
                    break;
                default:
                    im = null;
                    break;
            }
        } else if (x == 2) {
            switch (y) {
                case 0:
                    im = im20;
                    break;
                case 1:
                    im = im21;
                    break;
                case 2:
                    im = im22;
                    break;
                default:
                    im = null;
                    break;
            }
        } else
            im = null;
        return im;
    }

    private boolean isFull() {
        for (int i = 0; i < 9; i++)
            if (step[1][i] == 0)
                return false;
        return true;
    }

    private String checkWin() {
        String winner = "";
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                // 横竖、左上右下
                if ((step[1][0] == step[1][1] && step[1][1] == step[1][2])
                    || (step[1][0] == step[1][3] && step[1][3] == step[1][6])
                    || (step[1][0] == step[1][4] && step[1][4] == step[1][8]))
                    if (step[1][i] == 1)
                        winner = "circle";
                    else if (step[1][i] == -1) winner = "cross";
            } else if (i == 1) {
                // 竖
                if (step[1][1] == step[1][4] && step[1][4] == step[1][7])
                    if (step[1][i] == 1)
                        winner = "circle";
                    else if (step[1][i] == -1) winner = "cross";
            } else if (i == 2) {
                // 竖、右上左下
                if ((step[1][2] == step[1][5] && step[1][5] == step[1][8]) || (step[1][2] == step[1][4] && step[1][4] == step[1][6]))
                    if (step[1][i] == 1)
                        winner = "circle";
                    else if (step[1][i] == -1) winner = "cross";
            } else if (i == 3)
                if (step[1][3] == step[1][4] && step[1][4] == step[1][5])
                    if (step[1][i] == 1)
                        winner = "circle";
                    else if (step[1][i] == -1) winner = "cross";
                    else if (i == 6)
                        if (step[1][6] == step[1][7] && step[1][7] == step[1][8])
                            if (step[1][i] == 1)
                                winner = "circle";
                            else if (step[1][i] == -1) winner = "cross";

        }
        return winner;
    }

    private String hosts;
    private int port;
    private OutputStream os = null;
    private InputStream is = null;


    private class SocketThread extends Thread {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            //sendHeartbeat();
            while (true) {
                try {
                    if (socket == null || socket.isClosed()) {
                        try {
                            socket = new Socket("127.0.0.1", 8087); // 连接socket
                            os = socket.getOutputStream();
                        }catch (Exception e){
                            System.out.println("服务端掉线");
                            nodiaoxian=false;
                            return;
                        }

                    }
                    // 从服务端程序接收数据
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    try {
                        Data data = (Data)in.readObject();
                        if(data.getMessage()!=null){
                            String message =data.getMessage();
                            System.out.println("服务器端发送信息: " + message);
                            if (message.indexOf("开始游戏-1")>-1){
                                isstart=true;
                                circleTurn=true;
                            }else if(message.indexOf("开始游戏-2")>-1){
                                circleTurn=false;
                            }

                            if(message.equals("下棋")){
                                setChess(data.getX(),data.getY(),!circleTurn);
                                isstart=true;
                            }
                            if(message.equals("获胜")){
                                PersonDAO userDAO=new PersonDAO();
                                userDAO.win(username);
                            }
                            if(message.equals("对手获胜")){
                                setChess(data.getX(),data.getY(),!circleTurn);
                                PersonDAO userDAO=new PersonDAO();
                                userDAO.loss(username);
                            }
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Thread.sleep(100);
                    is = socket.getInputStream();
                    int size = is.available();
                    if (size <= 0) {
                        if ((System.currentTimeMillis() - startTime) > 3 * 10 * 1000) {
                            System.out.println("断线");
                        }
                        continue;
                    } else {
                        startTime = System.currentTimeMillis();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                        is.close();
                        os.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

}
