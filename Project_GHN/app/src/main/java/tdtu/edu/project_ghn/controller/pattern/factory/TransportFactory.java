package tdtu.edu.project_ghn.controller.pattern.factory;

public class TransportFactory {
    public static Transportation getTransportation(String typeOfTransport) {
        if (typeOfTransport.equals("motorBike")) {
            return new MotorBike();
        } else if (typeOfTransport.equals("tricycle")) {
            return new Tricycle();
        } else {
            return null;
        }
    }
}
