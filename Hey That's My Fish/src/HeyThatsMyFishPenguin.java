/*
Ismat Syah Imran
Mr. Tully
Period 4
 */

import java.util.Random;

public class HeyThatsMyFishPenguin {

    private int rowPosition;
    private int colPosition;
    private boolean orange;
    private boolean yellow;
    private boolean placed;

    public HeyThatsMyFishPenguin() {
        rowPosition = -1000;
        colPosition = -1000;

        placed = false;
        orange = false;
        yellow = false;
    }

    public boolean getOrange() {
        return orange;
    }

    public boolean getYellow() {
        return yellow;
    }

    public int getColPosition() {
        return colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public boolean getPlaced() {
        return placed;
    }

    public void setOrange(boolean orange) {
        this.orange = orange;
    }

    public void setYellow(boolean yellow) {
        this.yellow = yellow;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
