import datalayer.DataGateway;
import interfacelayer.AuthFrame;

public class Main {

    public static void main(String[] args) {
        DataGateway.initdb();
        DataGateway.connect();

        AuthFrame af = new AuthFrame();
        af.setVisible(true);
    }
}
