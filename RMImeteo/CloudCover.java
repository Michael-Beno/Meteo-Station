import java.rmi.*;

public interface CloudCover extends Remote {

    public void nextCloudCover() throws RemoteException;
    public boolean doneYet() throws RemoteException;
    public int getCloudCover() throws RemoteException;
}
