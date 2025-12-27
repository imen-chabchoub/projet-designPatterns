package main.java.game.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.java.game.manager.GameManager;
import main.java.game.manager.GameManagerSingleton;
import main.java.game.objects.Voiture;
import main.java.game.objects.RoadSegment;
import main.java.game.objects.Route;
import main.java.game.objects.VoitureJoueur;
import main.java.game.objects.obstacles.Block;
import main.java.game.objects.obstacles.Obstacle;
import main.java.game.objects.obstacles.ObstacleFactory;
import main.java.game.objects.obstacles.PowerUp;
import main.java.game.objects.obstacles.RoadHole;
import main.java.game.decorators.ShieldDecorator;
import main.java.game.decorators.SpeedBoostDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    @FXML
    private StackPane gameContainer;

    private Canvas gameCanvas;
    private GraphicsContext gc;
    private GameManager gameManager;
    private Route route;
    private Voiture voiture;
    private List<Obstacle> obstacles;
    private ObstacleFactory obstacleFactory;
    private AnimationTimer gameLoop;
    private double obstacleTimer = 0;
    private Random random = new Random();

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    private final double gameWidth = 800;
    private final double gameHeight = 600;

    private boolean gameOver = false;
    private double voitureX = 375;
    private double voitureY = 450;
    private double voitureVerticalVelocity = 0;
    private double gravity = 0.6;
    private double jumpPower = 18;
    private boolean isJumping = false;
    private Image blockImage;
    private Image powerupImage;

    private double replayButtonX = 0;
    private double replayButtonY = 0;
    private double replayButtonWidth = 150;
    private double replayButtonHeight = 50;

    @FXML
    private void initialize() {
        gameManager = GameManagerSingleton.getInstance();
        obstacleFactory = new ObstacleFactory();
        route = new Route("Route du Jeu");
        obstacles = new ArrayList<>();
        gameOver = false;

        gameCanvas = new Canvas(gameWidth, gameHeight);
        gc = gameCanvas.getGraphicsContext2D();
        gameContainer.getChildren().add(gameCanvas);

        voiture = new VoitureJoueur("Ma Voiture");
        voiture.setPositionX(voitureX);
        gameManager.setVoiture(voiture);

        loadObstacleImages();

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPressed);
        gameCanvas.setOnKeyReleased(this::handleKeyReleased);
        gameCanvas.requestFocus();
        gameCanvas.setOnMouseClicked(this::handleMouseClick);

        startGameLoop();
    }

    private void loadObstacleImages() {
        try {
            blockImage = new Image("/main/bloc.png");

            powerupImage = new Image("/main/powerup.png");
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des images: " + e.getMessage());
        }
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    update();
                    render();
                } else {
                    showGameOver();
                    this.stop();
                }
            }
        };
        gameLoop.start();
    }

    private void update() {
        route.update();
        if (leftPressed) {
            voitureX -= 15;
        }
        if (rightPressed) {
            voitureX += 15;
        }
        double minX = 30;
        double maxX = gameWidth - 90;

        if (voitureX < minX)
            voitureX = minX;
        if (voitureX > maxX)
            voitureX = maxX;

        voiture.setPositionX(voitureX);

        if (isJumping) {
            voitureVerticalVelocity += gravity;
            voitureY += voitureVerticalVelocity;

            if (voitureY >= 480) {
                voitureY = 480;
                isJumping = false;
                voitureVerticalVelocity = 0;
            }
        }

        voiture.update();

        obstacleTimer += 0.016;
        if (obstacleTimer >= 1.2) {
            Obstacle obstacle = obstacleFactory.createRandomObstacle();
            double randomX = 100 + random.nextDouble() * (gameWidth - 200);
            obstacle.setPositionX(randomX);
            obstacle.setPositionY(-50);
            obstacles.add(obstacle);
            obstacleTimer = 0;
        }
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update();

            if (obstacle.getPositionY() > gameHeight) {
                obstacles.remove(i);
                gameManager.addScore(10);
            } else if (!isJumping && checkCollision(voitureX, voitureY, obstacle)) {
                handleCollision(obstacle);
                obstacles.remove(i);
            }
        }
    }

    private void render() {

        gc.setFill(Color.web("#87CEEB"));
        gc.fillRect(0, 0, gameWidth, gameHeight);

        drawBackground();

        drawRoad();

        for (Obstacle obstacle : obstacles) {
            drawObstacle(obstacle);
        }

        drawVoiture();

        drawHUD();
    }

    private void drawBackground() {

        gc.setFill(Color.web("#6a9deaff"));
        gc.fillRect(0, 0, gameWidth, gameHeight * 0.4);

        gc.setFill(Color.WHITE);
        gc.fillOval(100, 50, 80, 40);
        gc.fillOval(130, 60, 60, 30);
        gc.fillOval(600, 80, 100, 50);
        gc.fillOval(650, 95, 70, 35);

        gc.setFill(Color.web("#00ab47ff"));
        gc.fillRect(0, gameHeight * 0.4, gameWidth, gameHeight * 0.6);

        drawPalmTree(80, gameHeight * 0.35);

        drawPalmTree(gameWidth - 120, gameHeight * 0.35);

        drawDeadTree(200, gameHeight * 0.4);

        drawDeadTree(gameWidth - 200, gameHeight * 0.4);
    }

    private void drawPalmTree(double x, double y) {

        gc.setFill(Color.web("#8B4513"));
        gc.fillRect(x, y, 20, 100);

        gc.setFill(Color.web("#228B22"));
        gc.fillOval(x - 30, y - 50, 80, 60);
    }

    private void drawDeadTree(double x, double y) {

        gc.setFill(Color.web("#654321"));
        gc.fillRect(x, y, 15, 80);

        gc.setStroke(Color.web("#654321"));
        gc.setLineWidth(3);
        gc.strokeLine(x + 7, y + 20, x - 20, y);
        gc.strokeLine(x + 7, y + 20, x + 30, y);
        gc.strokeLine(x + 7, y + 50, x - 15, y + 30);
        gc.strokeLine(x + 7, y + 50, x + 25, y + 30);
    }

    private void drawRoad() {

        List<RoadSegment> segments = route.getRoadSegments();
        double scrollOffset = route.getScrollOffset();
        double segmentHeight = route.getSegmentHeight();

        for (RoadSegment segment : segments) {
            double screenY = segment.getPositionY() - scrollOffset;
            if (screenY < -segmentHeight || screenY > gameHeight)
                continue;
            double depthRatio = (screenY + gameHeight) / gameHeight;
            if (depthRatio < 0)
                depthRatio = 0;
            if (depthRatio > 1)
                depthRatio = 1;

            double currentRoadWidth = 50 + depthRatio * 600;
            double curveOffset = segment.getCurveAmount() * 0.8;
            double roadX = gameWidth / 2 + curveOffset - currentRoadWidth / 2;

            gc.setFill(Color.web("#999999"));
            gc.fillRect(roadX, screenY, currentRoadWidth, segmentHeight + 2);

            gc.setStroke(Color.web("#FF0000"));
            gc.setLineWidth(4);
            gc.strokeLine(roadX, screenY, roadX, screenY + segmentHeight);
            gc.strokeLine(roadX + currentRoadWidth, screenY, roadX + currentRoadWidth, screenY + segmentHeight);

            if ((int) (segment.getPositionY() / segmentHeight) % 3 == 0) {
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(3);
                gc.strokeLine(gameWidth / 2 + curveOffset, screenY,
                        gameWidth / 2 + curveOffset, screenY + segmentHeight);
            }
        }
    }

    private void drawVoiture() {
        voiture.render(gc);
        double x = voitureX;
        double y = 480;
        double width = 60;
        double height = 90;

        gc.setFill(Color.RED);
        gc.fillRect(x, y, width, height);

        gc.setFill(Color.CYAN);
        gc.fillRect(x + 10, y + 15, width - 20, 25);

        gc.setFill(Color.WHITE);
        gc.fillOval(x + 8, y + 35, 8, 8);
        gc.fillOval(x + width - 16, y + 35, 8, 8);

        gc.setFill(Color.BLACK);
        gc.fillRect(x + 5, y + 30, 10, 15);
        gc.fillRect(x + width - 15, y + 30, 10, 15);
        gc.fillRect(x + 5, y + 65, 10, 15);
        gc.fillRect(x + width - 15, y + 65, 10, 15);

        if (isJumping) {
            gc.setFill(Color.color(0, 0, 0, 0.3));
            gc.fillOval(x + 10, y + height + 8, 40, 8);
        }
    }

    private void drawObstacle(Obstacle obstacle) {
        double x = obstacle.getPositionX();
        double y = obstacle.getPositionY();

        double depthRatio = (y + gameHeight) / gameHeight;
        if (depthRatio < 0)
            depthRatio = 0;
        if (depthRatio > 1)
            depthRatio = 1;

        double obstacleScale = 0.1 + depthRatio * 0.9;
        double scaledWidth = 80 * obstacleScale;
        double scaledHeight = 60 * obstacleScale;
        double scaledX = x - scaledWidth / 2;

        if (obstacle instanceof Block) {

            if (blockImage != null) {
                gc.drawImage(blockImage, scaledX, y, scaledWidth, scaledHeight);
            } else {
                gc.setFill(Color.ORANGE);
                gc.fillRect(scaledX, y, scaledWidth, scaledHeight);
                gc.setStroke(Color.DARKORANGE);
                gc.setLineWidth(2);
                gc.strokeRect(scaledX, y, scaledWidth, scaledHeight);
            }
        } else if (obstacle instanceof RoadHole) {

            drawRoadHole(scaledX, y, scaledWidth, scaledHeight);
        } else if (obstacle instanceof PowerUp) {

            if (powerupImage != null) {
                gc.drawImage(powerupImage, scaledX, y, scaledWidth, scaledHeight);
            } else {
                gc.setFill(Color.GOLD);
                gc.fillRect(scaledX, y, scaledWidth, scaledHeight);
                gc.setFill(Color.YELLOW);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                gc.fillText("⚡", scaledX + scaledWidth / 3, y + scaledHeight / 2);
            }
        }
    }

    private void drawRoadHole(double x, double y, double width, double height) {

        gc.setFill(Color.BLACK);
        gc.fillOval(x, y, width, height);

        gc.setStroke(Color.web("#333333"));
        gc.setLineWidth(3);
        gc.strokeOval(x, y, width, height);

        gc.setStroke(Color.web("#555555"));
        gc.setLineWidth(1);
        double centerX = x + width / 2;
        double centerY = y + height / 2;

        for (int i = 0; i < 5; i++) {
            double offset = i * (width / 5);
            gc.strokeLine(x + offset, centerY, centerX, y + height / 2 + 10);
        }
    }

    private boolean checkCollision(double voitureX, double voitureY, Obstacle obstacle) {
        double voitureWidth = 50;
        double voitureHeight = 80;

        double obstacleX = obstacle.getPositionX();
        double obstacleY = obstacle.getPositionY();
        double obstacleWidth = 50;
        double obstacleHeight = 50;

        return voitureX < obstacleX + obstacleWidth &&
                voitureX + voitureWidth > obstacleX &&
                voitureY < obstacleY + obstacleHeight &&
                voitureY + voitureHeight > obstacleY;
    }

    private void handleCollision(Obstacle obstacle) {
        if (obstacle instanceof Block) {
            System.out.println("💥 Collision avec un bloc !");
            gameOver = true;
        } else if (obstacle instanceof RoadHole) {
            System.out.println("🕳️ La voiture tombe dans un trou !");
            gameOver = true;
        } else if (obstacle instanceof PowerUp) {
            System.out.println("⚡ PowerUp ramassé !");
            gameManager.addScore(50);
            int bonus = random.nextInt(2);
            if (bonus == 0) {
                voiture = new SpeedBoostDecorator(voiture, 10);
                System.out.println("🚀 Boost de vitesse activé !");
            } else {
                voiture = new ShieldDecorator(voiture);
                System.out.println("🛡️ Bouclier activé !");
            }
            gameManager.setVoiture(voiture);
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.LEFT || code == KeyCode.A) {
            leftPressed = true;
            event.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            rightPressed = true;
            event.consume();
        } else if (code == KeyCode.SPACE) {
            if (!isJumping) {
                isJumping = true;
                voitureVerticalVelocity = -jumpPower;
            }
            event.consume();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.LEFT || code == KeyCode.A) {
            leftPressed = false;
            event.consume();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            rightPressed = false;
            event.consume();
        } else if (code == KeyCode.SPACE) {
            event.consume();
        }
    }

    private void handleMouseClick(javafx.scene.input.MouseEvent event) {
        if (gameOver) {
            double mouseX = event.getX();
            double mouseY = event.getY();

            if (mouseX >= replayButtonX && mouseX <= replayButtonX + replayButtonWidth &&
                    mouseY >= replayButtonY && mouseY <= replayButtonY + replayButtonHeight) {
                restartGame();
            }
        }
    }

    private void restartGame() {

        gameOver = false;
        voitureX = 375;
        voitureY = 480;
        voitureVerticalVelocity = 0;
        isJumping = false;
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
        obstacleTimer = 0;

        voiture = new VoitureJoueur("Ma Voiture");
        voiture.setPositionX(voitureX);
        gameManager.resetScore();

        route.reset();

        obstacles.clear();

        startGameLoop();

        gameCanvas.requestFocus();
    }

    private void drawHUD() {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText("Score: " + gameManager.getScore(), 20, 30);
        gc.fillText("Vitesse: " + (int) voiture.getSpeed(), 20, 60);
        gc.setFont(Font.font("Arial", 14));
        gc.fillText("Flèches ou A/D pour se déplacer | ESPACE pour sauter", 20, gameHeight - 20);
    }

    private void showGameOver() {

        gc.setFill(Color.color(0, 0, 0, 0.7));
        gc.fillRect(0, 0, gameWidth, gameHeight);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("GAME OVER", gameWidth / 2 - 150, gameHeight / 2 - 80);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gc.fillText("Score Final: " + gameManager.getScore(), gameWidth / 2 - 120, gameHeight / 2 - 10);

        drawReplayButton();
    }

    private void drawReplayButton() {
        replayButtonX = gameWidth / 2 - 75;
        replayButtonY = gameHeight / 2 + 60;

        gc.setFill(Color.web("#0066FF"));
        gc.fillRect(replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeRect(replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.fillText("Rejouer", replayButtonX + 35, replayButtonY + 35);
    }

}
