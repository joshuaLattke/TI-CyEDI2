package com.integradora;

import com.structures.Edge;
import com.structures.PrimAlgorithm;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Random;
import java.util.ResourceBundle;
import com.integradora.Wall;

public class BomberController implements Initializable{

    @FXML
    private Canvas cuumvas;
    private boolean isRunning;
    private GraphicsContext graphicsContext;
    private Player player=new Player();
    private Image playerimage;

    private int playerLife=3;

    private ArrayList<Enemy> enemies;
    private ArrayList<Rectangle> enemiesCollision;
    private String choice;
    private  Bomb bomb;
    private Rectangle playerCollison=new Rectangle(50,40,50,50);

    private ArrayList<Wall> walls = new ArrayList<>();

    private   ArrayList<DestruuctibleObject> destructibleObjects = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCanvas();
        initializeEnemies();

        ArrayList<Node> nodes = (ArrayList<Node>) createNodesForEnemies(); // Obtener nodos para el algoritmo de Prim
        ArrayList<Edge> edges = (ArrayList<Edge>) createEdgesBetweenEnemies();

        if (nodes != null) {
            PrimAlgorithm prim = new PrimAlgorithm(nodes, edges);
            initializeWalls();
            initializeColisions();
            initializeBomb();
            initEvents();
        } else {
            // Manejo de errores si los nodos o aristas son nulos
            System.out.println("Error: Nodos o aristas nulos para el algoritmo de Prim");
        }
    }




    private void initializeWalls() {
        ArrayList<Wall> walls = new ArrayList<>();
        double x = 62;
        double y = 84;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 5; j++) {
                if (j % 2 == 0) {
                    walls.add(new IndestructibleWall(x, y, 32, 44));
                } else {
                    walls.add(new DestructibleWall(x, y, 32, 44));
                }
                y += 168;
            }
            x += 68;
        }

        // Aquí puedes utilizar las paredes creadas según sea necesario
        for (Wall wall : walls) {
            // Si deseas realizar alguna operación con las paredes, puedes hacerlo aquí
        }
    }


    public BomberController(){
        isRunning = true;
        player=  new Player();
        choice=player.getChoice();
        playerimage=player.getPlayerImage();
    }

    private void initializeColisions() {
        for (Rectangle enemy: enemiesCollision) {
            if(playercolision.intersects(enemy.getLayoutBounds())) {
                //esto es para que al morir envie al gameover
                //creo que hay que hacer que la hitbox vaya siguiendo la posicion de las imagenes(que segun mire no tienen capacidad para ver colisiones)

                System.out.println("-1 hp");
                //Parent fxmlLoader = FXMLLoader.load((getClass().getResource("/fxml/playerSelection-view.fxml")));
                //Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                //Scene scene = new Scene(fxmlLoader);
                //stage.setScene(scene);

                //stage.show();
            }
        }
    }

    private void initializeCanvas() {
        graphicsContext = cuumvas.getGraphicsContext2D();
        graphicsContext.drawImage(player.getPlayerImage(),50,50);

        try {
            player.paint(isRunning);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    private void initEvents() {
        cuumvas.setFocusTraversable(true);
        cuumvas.requestFocus();

        cuumvas.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case W:

                    player.setUpPressed(true);

                    break;
                case S:

                    player.setDownPressed(true);
                    break;
                case A:
                    player.setLeftPressed(true);
                    break;
                case D:
                    player.setRightPressed(true);
                    break;
                case SPACE:
                    player.setBombPressed(true);
                    default:
                    break;
            }
        });

        cuumvas.setOnKeyReleased(event -> {
            System.out.println();
            switch (event.getCode()) {
                case W:
                    player.setUpPressed(false);
                    player.setPlayerImage(new Image(choice +"IdleBack.png"));
                    break;
                case S:
                    player.setDownPressed(false);
                    player.setPlayerImage(new Image(choice +"IdleFront.png"));
                    break;
                case A:
                    player.setLeftPressed(false);
                    player.setPlayerImage(new Image(choice +"IdleLeft.png"));
                    break;
                case D:
                    player.setRightPressed(false);
                    player.setPlayerImage(new Image(choice +"IdleRight.png"));
                    break;
                    case SPACE:
                        player.setBombPressed(false);
                        setBomb(event);
                        checkBomb();
                default:
                    break;
            }
        });
    }


    private void initializeEnemies() {
        enemies = new ArrayList<>();
        enemiesCollision = new ArrayList<>();
        Random random = new Random();
        double enemyCount = (double) (random.nextDouble()) *5;
        System.out.println(enemyCount);
        for (int i = 0; i < enemyCount; i++) {
            float randomX = (float) (random.nextDouble() * cuumvas.getWidth());
            float randomY = (float) (random.nextDouble() * cuumvas.getHeight());
            enemies.add(new Enemy(randomX, randomY));
            enemiesCollision.add(new Rectangle(randomX,randomY,50,50));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                render();
            }
        };
        timer.start();
    }
    private Rectangle playercolision = new Rectangle(player.getPlayerX(),player.getPlayerY(),50,50);

    private void update() throws IOException {
        if (player != null) {
            double playerX = player.getPlayerX();
            double playerY = player.getPlayerY();
            playercolision.setX(playerX);
            playercolision.setY(playerY);

            // Actualizamos la lógica del juego para que los enemigos sigan al jugador
            for (int i=0; i<enemies.size();i++) {
                Enemy enemy= enemies.get(i);
                double enemyX = enemy.getX();
                double enemyY = enemy.getY();

                double dx = playerX - enemyX;
                double dy = playerY - enemyY;
                double angle = Math.atan2(dy, dx);

                double speed = enemy.getSpeed();
                double newX = enemyX + Math.cos(angle) * speed;
                double newY = enemyY + Math.sin(angle) * speed;

                enemy.setX((float) newX);
                enemy.setY((float) newY);
                Rectangle collision = enemiesCollision.get(i);
                collision.setX(newX);
                collision.setY(newY);

                if (playercolision.intersects(collision.getLayoutBounds())) {

                    playerLife--;
                    player.setPlayerX(50);
                    player.setPlayerY(40);
                    if(playerLife==0){
                        gameOver();
                    }
                }

                //if (prim != null) {
                    //ArrayList<Edge> enemyPath = prim.minimumSpanningTree();
                    //moveEnemiesAlongPath(enemyPath);
                //}
            }
        }
    }

    private void gameOver() throws IOException {
        player.setPlayerX(10000);
        player.setPlayerY(10000);
        Parent fxmlLoader = FXMLLoader.load((getClass().getResource("/fxml/gameOver-view.fxml")));

        Scene scenegame = new Scene(fxmlLoader);
        Stage gamestage = new Stage();
        gamestage.setScene(scenegame);
        Image icon = new Image("/Icon.png");
        gamestage.getIcons().add(icon);
        gamestage.setTitle("BomberMan Game");
        gamestage.setResizable(false);
        gamestage.show();

        gamestage.setOnCloseRequest(event ->{
            event.consume();
            gamestage.close();

        });
    }
    private void endlevel() {
        if(player.getPlayerY() == 470 && player.getPlayerX()== 920){
            player.setPlayerX(50);
            player.setPlayerY(50);
            MapGame.newLevel(new ActionEvent());
        }

    }
    private void render() {
        GraphicsContext gc = cuumvas.getGraphicsContext2D();
        gc.clearRect(0, 0, cuumvas.getWidth(), cuumvas.getHeight());

        // Dibuja al jugador primero
        gc.drawImage(player.getPlayerImage(), player.getPlayerX(), player.getPlayerY(), 50, 50);
        // revisa que no se ponga en posiciones invalidas
        if(player.getPlayerX() < 25){
            player.setPlayerX(25);
        }
        if(player.getPlayerY() > cuumvas.getHeight() - 70){
            player.setPlayerY( cuumvas.getHeight() -70 );
        }
        if(player.getPlayerX() > cuumvas.getWidth() - 50){
            player.setPlayerX(cuumvas.getWidth() -50 );
        }
        if(player.getPlayerY() < 30){
            player.setPlayerY(30);
        }

        if (bomb != null && bomb.isActive()) {
            bomb.paint(gc);
        }


        //Esto es para terminar el nivel
        if(player.getPlayerY() == 470 && player.getPlayerX()== 920){
            endlevel();
        }

        // Luego, dibuja a los enemigos
        for (Enemy enemy : enemies) {
            gc.drawImage(enemy.getImagen(), enemy.getX(), enemy.getY());
        }
        for (Wall wall : walls) {
            wall.draw(gc);
        }
    }

    public void setBomb(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && bomb != null && !bomb.isActive()) {
            double bombX = player.getPlayerX(); // ajusta la posición inicial de la bomba según la posición del jugador
            double bombY = player.getPlayerY(); // ajusta la posición inicial de la bomba según la posición del jugador
            bomb.setPositionX(bombX);
            bomb.setPositionY(bombY);

            bomb.setActive(true); // activa la bomba nuevamente
            bomb.getExplosionTimer().playFromStart(); // reinicia el temporizador de la bomba
        }
    }
    public void checkBomb() {
        if (bomb != null) {
            double bombX = bomb.getPositionX();
            double bombY = bomb.getPositionY();

            // Colisión con el jugador
            double playerX = player.getPlayerX();
            double playerY = player.getPlayerY();
            double playerWidth = player.getPlayerImage().getWidth();
            double playerHeight = player.getPlayerImage().getHeight();

            if (bombX >= playerX && bombX <= playerX + playerWidth &&
                    bombY >= playerY && bombY <= playerY + playerHeight) {
                System.out.println("Player hit");
            }

            // Eliminar enemigos si hay colisión con la bomba
            ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
            for (Enemy enemy : enemies) {
                double enemyX = enemy.getX();
                double enemyY = enemy.getY();
                double enemyWidth = enemy.getImagen().getWidth();
                double enemyHeight = enemy.getImagen().getHeight();

                if (bombX >= enemyX && bombX <= enemyX + enemyWidth &&
                        bombY >= enemyY && bombY <= enemyY + enemyHeight) {
                    System.out.println("Enemy hit");
                    enemiesToRemove.add(enemy);
                }
            }

            // Eliminar los enemigos después del bucle
            enemies.removeAll(enemiesToRemove);

            // Añadir manejo para objetos destructibles
            for (DestruuctibleObject object : destructibleObjects) {
                double objectX = object.getX();
                double objectY = object.getY();
                double objectWidth = object.getWidth();
                double objectHeight = object.getHeight();

                if (bombX >= objectX && bombX <= objectX + objectWidth &&
                        bombY >= objectY && bombY <= objectY + objectHeight) {
                    System.out.println("Object hit");
                    object.setDestroyed(true);
                }
            }
        }
    }

    private void initializeBomb() {
        double initialBombX = 10000; // posición inicial de la bomba en X (ajústala según sea necesario)
        double initialBombY = 10000; // posición inicial de la bomba en Y (ajústala según sea necesario)
        Image bombImage = new Image("Icon.png",40,40,false,false); // ruta de la imagen de la bomba

        // crea la bomba con una duración de 3 segundos (ajústala según desees)
        bomb = new Bomb(initialBombX, initialBombY, bombImage, 1);
    }

    private void moveEnemiesAlongPath(ArrayList<Edge> enemyPath) {
        if (enemyPath != null) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                double enemyX = enemy.getX();
                double enemyY = enemy.getY();

                // Obtener el siguiente nodo al que se debe mover el enemigo
                Edge nextNode = enemyPath.get(i);
                double nextNodeX = nextNode.getVertex2();
                double nextNodeY = nextNode.getVertex2();

                // Calcular el ángulo entre el enemigo y el siguiente nodo
                double dx = nextNodeX - enemyX;
                double dy = nextNodeY - enemyY;
                double angle = Math.atan2(dy, dx);

                // Calcular la velocidad del enemigo
                double speed = enemy.getSpeed();
                double newX = enemyX + Math.cos(angle) * speed;
                double newY = enemyY + Math.sin(angle) * speed;

                // Actualizar la posición del enemigo
                enemy.setX((float) newX);
                enemy.setY((float) newY);
            }
        }
    }
    private ArrayList<Node> createNodesForEnemies() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Enemy enemy : enemies) {
            double enemyX = enemy.getX();
            double enemyY = enemy.getY();
            //Node node = new Node(enemyX, enemyY);
            //nodes.add(node);
        }
        return nodes;
    }
    private  ArrayList<Edge> createEdgesBetweenEnemies() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy1 = enemies.get(i);
            double enemy1X = enemy1.getX();
            double enemy1Y = enemy1.getY();

            for (int j = 0; j < enemies.size(); j++) {
                if (i != j) {
                    Enemy enemy2 = enemies.get(j);
                    double enemy2X = enemy2.getX();
                    double enemy2Y = enemy2.getY();

                    double distance = Math.sqrt(Math.pow(enemy2X - enemy1X, 2) + Math.pow(enemy2Y - enemy1Y, 2));
                    //Edge edge = new Edge(new Node(enemy1X, enemy1Y), new Node(enemy2X, enemy2Y), distance);
                    //edges.add(edge);
                }
            }
        }
        return edges;
    }
}



