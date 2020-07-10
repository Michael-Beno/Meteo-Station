import java.rmi.*;

public interface Temperature extends Remote {

    public void nextTemperature() throws RemoteException;
    public boolean doneYet() throws RemoteException;
    public int getTemperature() throws RemoteException;
    public int getHumidity() throws RemoteException;
}
