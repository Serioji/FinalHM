package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class gameBoard extends JFrame implements MouseListener {
    public static final int TILE_SIDE_COUNT = 8;
    protected Obsticle[][] obsticle;
    protected Food[][] food;
    protected Snake[][] snake;
    private Snake snake1;
    int randomNumber1;
    int randomNumber2;
    int points;
    int firstRow;
    int firstCol;

    public gameBoard() {
        this.snake = new Snake[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        randomNumber1 = ThreadLocalRandom.current().nextInt(1, 5);
        if (randomNumber1 == 1)
            this.snake[0][0] = new Snake(0, 0, Color.YELLOW);
        if (randomNumber1 == 2)
            this.snake[0][7] = new Snake(0, 7, Color.YELLOW);
        if (randomNumber1 == 3)
            this.snake[7][0] = new Snake(7, 0, Color.YELLOW);
        if (randomNumber1 == 4)
            this.snake[7][7] = new Snake(7, 7, Color.YELLOW);
        this.food = new Food[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        this.food[randomNumber1][randomNumber2] = new Food(randomNumber1, randomNumber2, Color.green);
        this.obsticle = new Obsticle[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        summonObsticle(randomNumber1, randomNumber2);

        this.setVisible(true);
        this.setSize(800, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(this);
    }

    private void summonFood(int randomNumber1, int randomNumber2) {
        for (int blueCount = 1; blueCount > 0; blueCount--) {
            randomNumber1 = ThreadLocalRandom.current().nextInt(0, 8);
            randomNumber2 = ThreadLocalRandom.current().nextInt(0, 8);
            if (this.snake[randomNumber1][randomNumber2] == null) {
                this.food[randomNumber1][randomNumber2] = new Food(randomNumber1, randomNumber2, Color.GREEN);
                blueCount--;
            }
            blueCount++;
        }
    }

    private void summonObsticle(int randomNumber1, int randomNumber2) {
        for (int blueCount = 8; blueCount > 0; blueCount--) {
            randomNumber1 = ThreadLocalRandom.current().nextInt(0, 8);
            randomNumber2 = ThreadLocalRandom.current().nextInt(0, 8);
            if (this.food[randomNumber1][randomNumber2] == null && this.snake[randomNumber1][randomNumber2] == null) {
                this.obsticle[randomNumber1][randomNumber2] = new Obsticle(randomNumber1, randomNumber2, Color.BLACK);
                blueCount--;
            }
            blueCount++;
        }
    }

    @Override
    public void paint(Graphics g) {
        /**
         *
         * @author Vasil
         * @param "визуализиране на игралните пешки върху бойното поле чрез два for цикъла и повикване на точната им позиция чрез row,col"
         */

        super.paint(g);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                this.renderGameTile(g, row, col);

            }
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                this.renderGamePice(g, row, col);

            }
        }
    }

    private Color getTileColor(int row, int col) {

        return Color.WHITE;
    }

    private void renderGameTile(Graphics g, int row, int col) {
        Color tileColor = this.getTileColor(row, col);
        GameTile tile = new GameTile(row, col, tileColor);
        tile.render(g);
    }

    private void renderGamePice(Graphics g, int row, int col) {
        if (this.hasFood(row, col)) {
            Food p = (Food) this.getFood(row, col);
            p.render(g);
        }
        if (this.hasSnake(row, col)) {
            Snake p = (Snake) this.getSnake(row, col);
            p.render(g);
        }
        if (this.hasObsticle(row, col)) {
            Obsticle p = (Obsticle) this.getObsticle(row, col);
            p.render(g);
        }
    }

    private Obsticle getObsticle(int row, int col) {
        return this.obsticle[row][col];
    }

    private boolean hasObsticle(int row, int col) {
        return this.getObsticle(row, col) != null;
    }

    private Food getFood(int row, int col) {
        return this.food[row][col];
    }

    private boolean hasFood(int row, int col) {
        return this.getFood(row, col) != null;
    }

    private Snake getSnake(int row, int col) {
        return this.snake[row][col];
    }

    private boolean hasSnake(int row, int col) {
        return this.getSnake(row, col) != null;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardDimensionBasedOnCoordinates(e.getY());
        int col = this.getBoardDimensionBasedOnCoordinates(e.getX());
        row--;
        if (this.food[row][col] == null) {
            summonFood(randomNumber1, randomNumber2);
        }
        if(this.snake !=null){
            snakeMove(row,col,firstRow,firstCol);
        }
        else
            firstRow=row;
            firstCol=col;

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private int getBoardDimensionBasedOnCoordinates(int coordinates) {
        return coordinates / GameTile.TILE_SIZE;
    }
    private void moveGps(int row, int col, Snake p) {
        int initialRow = p.getRow();
        int initialCol = p.getCol();

        p.move(row, col);

        this.snake[p.getRow()][p.getCol()] = this.snake1;
        this.snake[initialRow][initialCol] = null;

        this.snake = null;
    }
    private void snakeMove(int row , int col,int firstRow,int firstCol){
        Snake p = (Snake) this.snake1;
        if (this.hasSnake(row, col)) {


        } else if (p.isMoveValid(row, col)) {
            if (hasFood(row, col)) {
                points +=15;
            }
            if (points == 300) {
                UI.render(this, "Победа", "Победа");
            }
            if (this.hasObsticle(row, col)) {
                UI.render(this, "Смърт", "Kрай");
                System.exit(2);
            } else if (p.isMoveValid(row, col)) {
                moveGps(row, col, p);

            }
            this.repaint();

            }
        }
    }

