
public class MainClient {

    public static void main(String args[]) {

        Front clientUI = new Front();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                clientUI.setVisible(true);
            }
        });

    }
}
