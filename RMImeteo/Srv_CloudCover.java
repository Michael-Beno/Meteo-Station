import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

public class Srv_CloudCover extends UnicastRemoteObject implements CloudCover {

    DoCloudCover dcc;
    
    public Srv_CloudCover() throws RemoteException {
        this.dcc = new DoCloudCover();
    }
    
    public void nextCloudCover() throws RemoteException {
        dcc = new DoCloudCover();
        dcc.start();
    }
    
    public boolean doneYet() throws RemoteException {
        return dcc.doneYet();
    }
    
    public int getCloudCover() throws RemoteException {
    	return dcc.getCloudCover();
    }

    public static void main(String[] args) {
        try {
            Srv_CloudCover server = new Srv_CloudCover();
            Naming.rebind("CLOUD_COVER", server);
            System.out.println("Server 'Cloud Cover' is running...");
        }
        catch (java.net.MalformedURLException e) {
            System.out.println("Malformed URL for MessageServer name: " + e.toString());
        }
        catch (RemoteException e) {
            System.out.println("Communication error: " + e.toString());
        }
    }
}

class DoCloudCover extends Thread {
    
    private final static int MAX_CLOUDCOVER = 101;
    private final static int SLEEP_TIMER = 3000;
    
    private int cloud_cover = 0;
    private boolean done = false;
    
    private int getRandomCloudCover() {
        Random rand = new Random();
        int cc = rand.nextInt(MAX_CLOUDCOVER);
        return cc;
    }
    
    public void run()  {
        try {
            done = false;
            cloud_cover = getRandomCloudCover();
            Thread.sleep(SLEEP_TIMER);
            done = true;
        }
        catch(Exception e) {
            System.out.println("Server: Oh no!!! #1:" + e.toString());
        }
    }
    
    public int getCloudCover() {
    	done = false;
    	return cloud_cover;
    }
    
    public boolean doneYet() {
    	return done;
    }
}