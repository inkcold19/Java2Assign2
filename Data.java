

    import java.io.Serializable;

    public class Data implements Serializable {
        private int x;
        private int y;
        private boolean circleTurn;
        private String winner;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        private String Message;
        public Data(){}

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean isCircleTurn() {
            return circleTurn;
        }

        public void setCircleTurn(boolean circleTurn) {
            this.circleTurn = circleTurn;
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }
    }
