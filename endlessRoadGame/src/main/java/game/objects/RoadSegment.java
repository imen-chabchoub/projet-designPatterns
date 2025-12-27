package main.java.game.objects;

public class RoadSegment {
    private double positionY;
    private double curveAmount;
    private double roadWidth;

    public RoadSegment(double positionY, double curveAmount, double roadWidth) {
        this.positionY = positionY;
        this.curveAmount = curveAmount;
        this.roadWidth = roadWidth;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getCurveAmount() {
        return curveAmount;
    }

    public void setCurveAmount(double curveAmount) {
        this.curveAmount = curveAmount;
    }

    public double getRoadWidth() {
        return roadWidth;
    }

    public void setRoadWidth(double roadWidth) {
        this.roadWidth = roadWidth;
    }
}
