package tdtu.edu.project_ghn.controller.pattern.state;

public class DeliveryContext {
    private DeliveryStatus deliveryStatus;

    public void setStatus(DeliveryStatus status) {
        this.deliveryStatus = status;
    }

    public void applyState() {
        this.deliveryStatus.handleRequest();
    }
}
