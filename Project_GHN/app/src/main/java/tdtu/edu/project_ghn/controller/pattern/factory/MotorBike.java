package tdtu.edu.project_ghn.controller.pattern.factory;

public class MotorBike extends Transportation{

    @Override
    public double calculateTotalPrice(String service, double lengthRoad) {
        double totalPrice;
        if (service.equals("fast")) {
            if (lengthRoad <= 2) {
                totalPrice = 16000.0;
            } else {
                totalPrice = 16000.0 + (lengthRoad-2)*5400;
            }
        } else {
            if (lengthRoad <= 4) {
                totalPrice = 19636;
            } else {
                totalPrice = 19636 + (lengthRoad-4)*4320;
            }
        }

        return totalPrice;
    }
}
