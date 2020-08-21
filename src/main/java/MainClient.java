import java.rmi.*;
import static java.lang.Thread.sleep;

public class MainClient
{
    public static void main(String args[])
    {
        try
        {
            Interface obj = (Interface) Naming.lookup("//127.0.0.1/ObjetoFecha"); //Localiza el objeto distribuido
            //Llamamos al objeto de la interfaz
            sleep(1000); //Para que dé tiempo a leer la respuesta antes de que se cierre la ventana
        }
        catch (Exception e)
        {
            System.out.println("Excepción : " + e.getMessage());
            e.printStackTrace();
        }
    }
}