import java.rmi.*;
import java.rmi.server.*;
import java.util.Random;

public class Srv_Temperature extends UnicastRemoteObject implements Temperature {

    DoTemperature df;
    
    public Srv_Temperature() throws RemoteException {
        this.df = new DoTemperature();
    }
    
    public void nextTemperature() throws RemoteException {
        df = new DoTemperature();
        df.start();
    }
    
    public boolean doneYet() throws RemoteException {
        return df.doneYet();
    }
    
    public int getTemperature() throws RemoteException {
    	return df.getTemperature();
    }

    public int getHumidity() throws RemoteException {
    	return df.getHumidity();
    }

    public static void main(String[] args) {
        try {
            Srv_Temperature server = new Srv_Temperature();
            Naming.rebind("TEMPERATURE", server);
            System.out.println("Server 'Temperature' is running...");
        }
        catch (java.net.MalformedURLException e) {
            System.out.println("Malformed URL for MessageServer name: " + e.toString());
        }
        catch (RemoteException e) {
            System.out.println("Communication error: " + e.toString());
        }
    }
}

class DoTemperature extends Thread {
    
    private final static int MAX_TEMPERATURE = 51;
    private final static int MAX_HUMIDITY = 101;
    private final static int SLEEP_TIMER = 1500; 
    
    private int temperature = 0;
    private int humidity = 0;
    private boolean done = false;
    
    private int getRandomTemperature() {
        Random rand = new Random();
        int t = rand.nextInt(MAX_TEMPERATURE);
        return t;
    }
    
    private int getRandomHumidity() {
        Random rand = new Random();
        int h = rand.nextInt(MAX_HUMIDITY);
        return h;
    }
    
    public void run()  {
        try {
            done = false;
            temperature = getRandomTemperature();
            humidity = getRandomHumidity();
            Thread.sleep(SLEEP_TIMER);
            done = true;
        }
        catch(Exception e) {
            System.out.println("Server: Oh no!!! #1:" + e.toString());
        }
    }
    
    public int getTemperature() {
    	done = false;
    	return temperature;
    }
    
    public int getHumidity() {
    	done = false;
    	return humidity;
    }
    
    public boolean doneYet() {
    	return done;
    }
}