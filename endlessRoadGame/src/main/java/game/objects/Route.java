package main.java.game.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import main.java.game.objects.RoadSegment;

public class Route extends GameObject {

    private List<RoadSegment> roadSegments;
    private Random random;
    private double scrollOffset = 0;

    private final double segmentHeight = 30;
    private final double baseRoadWidth = 150;
    private final double gameHeight = 600;
    private double currentCurve = 0;
    private int segmentCounter = 0;

    public Route(String nom) {
        super(nom);
        this.roadSegments = new ArrayList<>();
        this.random = new Random();
        generateInitialRoad();
    }

    private void generateInitialRoad() {
        for (int i = 0; i < 30; i++) {
            addSegment();
        }
    }

    private void addSegment() {
        segmentCounter++;

        double curveChange = 0;
        if (segmentCounter % 20 < 10) {
            curveChange = random.nextDouble() * 2 - 1;
        } else {
            curveChange = -currentCurve * 0.1;
        }

        currentCurve += curveChange;

        if (currentCurve > 50)
            currentCurve = 50;
        if (currentCurve < -50)
            currentCurve = -50;

        double positionY = roadSegments.isEmpty() ? 0
                : roadSegments.get(roadSegments.size() - 1).getPositionY() + segmentHeight;

        RoadSegment segment = new RoadSegment(positionY, currentCurve, baseRoadWidth);
        roadSegments.add(segment);
    }

    @Override
    public void update() {
        scrollOffset += 8;

        roadSegments.removeIf(segment -> segment.getPositionY() - scrollOffset > gameHeight + 100);

        while (roadSegments.isEmpty() ||
                roadSegments.get(roadSegments.size() - 1).getPositionY() - scrollOffset < gameHeight + 100) {
            addSegment();
        }
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }

    public double getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(double offset) {
        this.scrollOffset = offset;
    }

    public double getSegmentHeight() {
        return segmentHeight;
    }

    public double getRoadWidth() {
        return baseRoadWidth;
    }

    public double getRoadCenterX(double depthY, double gameWidth) {
        for (RoadSegment segment : roadSegments) {
            double segmentScreenY = segment.getPositionY() - scrollOffset;
            if (segmentScreenY >= depthY - segmentHeight && segmentScreenY <= depthY) {
                double curveOffset = segment.getCurveAmount() * 0.5;
                return gameWidth / 2 + curveOffset;
            }
        }
        return gameWidth / 2;
    }

    public void reset() {
        roadSegments.clear();
        scrollOffset = 0;
        currentCurve = 0;
        segmentCounter = 0;
        generateInitialRoad();
    }
}
