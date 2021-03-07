package SnakeGame;

import java.awt.*;

public class Food extends Tile{
        public Food(int row, int col, Color color) {

            this.row = row;
            this.col = col;
            this.color = color;
        }

        public void render(Graphics g) {
            int x = this.col * GameTile.TILE_SIZE;
            int y = this.row * GameTile.TILE_SIZE;
            g.setColor(Color.GREEN);
            g.fillRect(x + 10, y + 33, 48, 48);
        }
    }

