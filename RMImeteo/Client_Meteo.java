import java.rmi.*;

public class Client_Meteo {
    
    static float calc;
    static float c_temperature;
    static float c_humidity;
    static float c_cloud_cover;
    
    public Client_Meteo() {
        Client_Meteo.calc = 0;
        Client_Meteo.c_temperature = 0;
        Client_Meteo.c_humidity = 0;
        Client_Meteo.c_cloud_cover = 0;
    }
    
    public static void main(String args[]) {
        
        System.out.println("Client Meteo");
        
        Temperature temperature = null;
        TemperatureChecker testTemperature;
        try {
            Remote remoteObject = Naming.lookup("TEMPERATURE");
            temperature = (Temperature)remoteObject;
        }
        catch(Exception e){
            System.out.println("Oh no!!! Temperature#1");
        }
        testTemperature = new TemperatureChecker(temperature);
        testTemperature.start();
        
        CloudCover cloud_cover = null;
        CloudCoverChecker testCloudCover;
        try {
            Remote remoteObject = Naming.lookup("CLOUD_COVER");
            cloud_cover = (CloudCover)remoteObject;
        }
        catch(Exception e){
            System.out.println("Oh no!!! CloudCover#1");
        }
        testCloudCover = new CloudCoverChecker(cloud_cover);
        testCloudCover.start();
        
        while (true) {
            try {
                Thread.sleep(10000);
                temperature.nextTemperature();
                cloud_cover.nextCloudCover();
                c_temperature = temperature.getTemperature();
                c_humidity = temperature.getHumidity();
                c_cloud_cover = cloud_cover.getCloudCover();
                calc = c_temperature * (1 - c_humidity * 0.01f) * (1 - c_cloud_cover * 0.01f);
                //System.out.println("X: " + calc);
                
				if(calc > 20) System.out.println("Checking... risk detected (" + calc + ")");
				else System.out.println("Checking... no risk");
            }
            catch(Exception e){
                System.out.println("Oh no!!!#2");
            }
        }
    }
}

class TemperatureChecker extends Thread {

    private Temperature remoteTemperature;
    private int temperature = 0;
    private int humidity = 0;

    public TemperatureChecker(Temperature t) {
    	remoteTemperature = t;
    }
    
    public int getTemperature() {
        return temperature;
    }
    
    public int getHumidity() {
        return humidity;
    }
    
    public void run() {
    	while (true) {
            try {
                Thread.sleep(100) ;
                if (remoteTemperature.doneYet()) {
                    temperature = remoteTemperature.getTemperature();
                    humidity = remoteTemperature.getHumidity();
                    //System.out.println("T: " + temperature);
                    //System.out.println("H: " + humidity);
                }
            }
            catch(Exception e){
                System.out.println("TemperatureChecker: Oh no!!!");
                e.printStackTrace();
            }
    	}
    }
}

class CloudCoverChecker extends Thread {

    private CloudCover remoteCloudCover;
    private int cloud_cover = 0;

    public CloudCoverChecker(CloudCover cc) {
    	remoteCloudCover = cc;
    }
    
    public int getCloudCover() {
        return cloud_cover;
    }
    
    public void run() {
    	while (true) {
            try {
                Thread.sleep(100) ;
                if (remoteCloudCover.doneYet()) {
                    cloud_cover = remoteCloudCover.getCloudCover();
                    //System.out.println("CC: " + cloud_cover);
                }
            }
            catch(Exception e){
                System.out.println("CloudCoverChecker: Oh no!!!");
                e.printStackTrace();
            }
    	}
    }
}
