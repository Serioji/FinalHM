package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class gameBoard extends JFrame implements MouseListener {
    public static final int TILE_SIDE_COUNT = 10;
    protected Tail[][] tail;
    protected Obsticle[][] obsticle;
    protected Food[][] food;
    protected Snake[][] snake;
    private Snake selectedSnake;
    int randomNumber1;
    int randomNumber2;
    int points;
    int firstRow;
    int firstCol;
    protected final Color head = new Color(204,204,0);
    int tailEndRow,tailEndCol;
    int beforeEndRow,beforeEndCol;

    public gameBoard() {
        this.snake = new Snake[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        randomNumber1 = ThreadLocalRandom.current().nextInt(1, 5);
        if (randomNumber1 == 1)
            this.snake[1][1] = new Snake(1, 1,head);
        if (randomNumber1 == 2)
            this.snake[1][8] = new Snake(1, 8,head);
        if (randomNumber1 == 3)
            this.snake[8][1] = new Snake(8, 1,head);
        if (randomNumber1 == 4)
            this.snake[8][8] = new Snake(8, 8,head);
        this.food = new Food[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        this.food[randomNumber1][randomNumber2] = new Food(randomNumber1, randomNumber2, Color.green);
        this.obsticle = new Obsticle[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        summonObsticle(randomNumber1, randomNumber2);
        this.setVisible(true);
        this.setSize(800, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.setTitle("points = 0");
        this.tail = new Tail[TILE_SIDE_COUNT][TILE_SIDE_COUNT];
        summonFood();
    }

    private void summonFood() {
            randomNumber1 = ThreadLocalRandom.current().nextInt(2, 9);
            randomNumber2 = ThreadLocalRandom.current().nextInt(2, 9);

            if (this.snake[randomNumber1][randomNumber2] == null && this.obsticle[randomNumber1][randomNumber2] == null&&this.tail[randomNumber1][randomNumber2] == null) {
                this.food[randomNumber1][randomNumber2] = new Food(randomNumber1, randomNumber2, Color.GREEN);
            }
            else
                summonFood();
    }

    private void summonObsticle(int randomNumber1, int randomNumber2) {
        for (int blueCount = 5; blueCount > 0; blueCount--) {
            randomNumber1 = ThreadLocalRandom.current().nextInt(1, 9);
            randomNumber2 = ThreadLocalRandom.current().nextInt(1, 9);
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
        for (int row = 0; row <8; row++) {
            for (int col = 0; col <8; col++) {

                this.renderGameTile(g, row, col);

            }
        }
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {

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
        if(this.hasTail(row,col)){
            Tail p = (Tail) this.getTail(row,col);
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

    private Tail getTail (int row, int col) {
        return this.tail[row][col];
    }

    private boolean hasTail(int row, int col) {
        return this.getTail(row, col) != null;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardDimensionBasedOnCoordinates(e.getY());
        int col = this.getBoardDimensionBasedOnCoordinates(e.getX());
        row--;
            if (this.selectedSnake != null) {
                if(hasSnake(row,col))
                    System.out.println("Greshen hod");
                else {
                    snakeMove(row, col, firstRow, firstCol);
                }
            } else {
                firstRow = row;
                firstCol = col;
                if (this.hasSnake(row, col)) {
                    this.selectedSnake = this.getSnake(row, col);
                }
            }



        this.repaint();
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

    private void snakeMove(int row , int col,int firstRow,int firstCol) {
        Snake p = (Snake) this.selectedSnake;
        if (p.isMoveValid(row, col)) {
            if (hasFood(row, col))
            foodColled(row,col);
            else {
                headLead(row, col, firstRow, firstCol);
                this.tail[firstRow][firstCol] = (new Tail(firstRow, firstCol, Color.YELLOW));
                this.tail[tailEndRow][tailEndCol] = null;
                tailEndRow = firstRow;
                tailEndCol = firstCol;
                selectedSnake = null;
            }
    }
            if (points == 300) {
                UI.render(this, "Победа", "You are a winner");
            }
            if (this.hasObsticle(row, col)) {
                UI.render(this, "Смърт", "Game over");
                System.exit(2);
            }

            this.repaint();

            }
            private void headLead(int row , int col,int firstRow, int firstCol){
                if(row<9&& row >0&&col>0&&col<9) {
                    this.snake[row][col] = (new Snake(row, col, head));
                    this.snake[firstRow][firstCol] = null;
                }
                if(row==0){
                    this.snake[firstRow][firstCol] = null;
                    row=8;
                    this.snake[row][col] = (new Snake(row, col, head));

                }
                if(row==9){
                    this.snake[firstRow][firstCol] = null;
                    row=1;
                    this.snake[row][col] = (new Snake(row, col, head));

                }
                if(col==9){
                    this.snake[firstRow][firstCol] = null;
                    col=1;
                    this.snake[row][col] = (new Snake(row, col, head));

                }
                if(col==0){
                    this.snake[firstRow][firstCol] = null;
                    col=8;
                    this.snake[row][col] = (new Snake(row, col, head));

                }
            }
            private void foodColled(int row,int col){
                if (hasFood(row, col)) {
                    points += 15;
                    this.setTitle("points =" + points);
                    this.food[row][col] = null;
                    summonFood();
                    this.snake[row][col] = (new Snake(row, col, head));

                    if (points == 15) {
                        this.snake[row][col] = (new Snake(row, col, head));
                        this.tail[firstRow][firstCol] = (new Tail(firstRow, firstCol, head));
                        beforeEndRow = firstRow;
                        beforeEndCol = firstCol;
                        this.snake[firstRow][firstCol] = null;
                    }
                    if (points > 15) {
                        this.snake[row][col] = (new Snake(row, col, head));
                        this.tail[firstRow][firstCol] = (new Tail(firstRow, firstCol, head));
                    }
                    selectedSnake = null;
                }
            }
        }


