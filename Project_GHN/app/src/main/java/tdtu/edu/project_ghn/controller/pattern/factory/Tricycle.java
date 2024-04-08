package tdtu.edu.project_ghn.controller.pattern.factory;

public class Tricycle extends Transportation {

    @Override
    public double calculateTotalPrice(String service, double lengthRoad) {
        double totalPrice = 0.0;

        if (lengthRoad <= 4) {
            totalPrice = 162000;
        } else if (lengthRoad > 4 && lengthRoad <= 10){
            totalPrice = 162000 + 18600*(lengthRoad-4);
        } else if (lengthRoad > 10 && lengthRoad <= 15) {
            totalPrice = 162000 + 18600*6 + (lengthRoad-10)*14236;
        } else if (lengthRoad > 15 && lengthRoad <= 40) {
            totalPrice = 162000 + 18600*6 + 5*14236 + (lengthRoad-15)*13745;
        } else if (lengthRoad > 40) {
            totalPrice = 162000 + 18600*6 + 5*14236 + 25*13745 + (lengthRoad-40)*7855;
        }
        return totalPrice;
    }
}
