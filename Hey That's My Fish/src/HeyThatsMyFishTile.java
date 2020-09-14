/*
Ismat Syah Imran
Mr. Tully
Period 4
 */

public class HeyThatsMyFishTile {

    private boolean oneFish;
    private boolean twoFish;
    private boolean threeFish;
    private boolean empty;
    private boolean penguin;

    public HeyThatsMyFishTile() {
        oneFish = false;
        twoFish = false;
        threeFish = false;
        empty = false;
        penguin = false;
    }

    public boolean getOneFish() {
        return oneFish;
    }

    public boolean getTwoFish() {
        return twoFish;
    }

    public boolean getThreeFish() {
        return threeFish;
    }

    public boolean getEmpty() {
        return empty;
    }

    public boolean getPenguin() {
        return penguin;
    }

    public void setOneFish(boolean oneFish) {
        this.oneFish = oneFish;
    }

    public void setTwoFish(boolean twoFish) {
        this.twoFish = twoFish;
    }

    public void setThreeFish(boolean threeFish) {
        this.threeFish = threeFish;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setPenguin(boolean penguin) {
        this.penguin = penguin;
    }
}
