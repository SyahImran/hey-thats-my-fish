/*
Ismat Syah Imran
Mr. Tully
Period 4
 */

import java.util.Random;

public class HeyThatsMyFishMap {

    public static final int NUM_ROWS = 6;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_ONES = 30;
    public static final int NUM_TWOS = 20;
    public static final int NUM_THREES = 10;

    private HeyThatsMyFishTile[][] grid;

    public HeyThatsMyFishMap() {
        createGrid();
    }

    public void createGrid() {

        //initializes map tiles
        grid = new HeyThatsMyFishTile[NUM_ROWS][NUM_COLUMNS];
        for(int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[0].length; c++) {
                grid[r][c] = new HeyThatsMyFishTile();
                grid[r][c].setEmpty(true);
            }
        }

        //creates random row and column
        Random random = new Random();
        int r;
        int c;

        //places one-fish tiles
        for(int i = 0; i < NUM_ONES; i++) {
            r = random.nextInt(grid.length);
            c = random.nextInt(grid[0].length);
            if(grid[r][c].getEmpty()) {
                grid[r][c].setEmpty(false);
                grid[r][c].setOneFish(true);
            }
            else
                i--;
        }

        //places two-fish tiles
        for(int i = 0; i < NUM_TWOS; i++) {
            r = random.nextInt(grid.length);
            c = random.nextInt(grid[0].length);
            if(grid[r][c].getEmpty()) {
                grid[r][c].setEmpty(false);
                grid[r][c].setTwoFish(true);
            }
            else
                i--;
        }

        //places three-fish tiles
        for(int i = 0; i < NUM_THREES; i++) {
            r = random.nextInt(grid.length);
            c = random.nextInt(grid[0].length);
            if(grid[r][c].getEmpty()) {
                grid[r][c].setEmpty(false);
                grid[r][c].setThreeFish(true);
            }
            else
                i--;
        }
    }

    public HeyThatsMyFishTile getTile(int r, int c) {
        return grid[r][c];
    }

}
