package SnakeGame;

import java.awt.*;

public class Tail extends Tile{
    protected final Color tail = new Color(255,255,153);
    public Tail(int row, int col, Color color) {

        this.row = row;
        this.col = col;
        this.color = color;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public boolean isMoveValid(int moveRow, int moveCol) {

        int rowCoeficient = Math.abs(moveRow - this.row);
        int colCoeficient = moveCol - this.col;
        if(rowCoeficient<=1&&colCoeficient==0||colCoeficient<=1 && rowCoeficient==0)
            return  true;
        return false;
    }

    public void render(Graphics g) {
        int x = this.col * GameTile.TILE_SIZE;
        int y = this.row * GameTile.TILE_SIZE;
        g.setColor(tail);
        g.fillRect(x + 10, y + 33, 48, 48);
    }
    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }
}