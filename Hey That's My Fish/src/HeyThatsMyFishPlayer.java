/*
Ismat Syah Imran
Mr. Tully
Period 4
 */

public class HeyThatsMyFishPlayer {

    private int fish;
    private HeyThatsMyFishPenguin[] penguin;
    public static final int NUM_PENGUINS = 4;
    private int selected;
    private boolean verified;
    boolean removed;

    public HeyThatsMyFishPlayer() {
        fish = 0;
        penguin = new HeyThatsMyFishPenguin[4];
        for(int i = 0; i < NUM_PENGUINS; i++) {
            penguin[i] = new HeyThatsMyFishPenguin();
        }
        selected = 0;
        verified = false;
        removed = false;
    }

    public int getFish() {
        return fish;
    }

    public HeyThatsMyFishPenguin getPenguin(int i) {
        return penguin[i];
    }

    public int getSelected() {
        return selected;
    }

    public boolean getVerified() {
        return verified;
    }

    public boolean getRemoved() {
        return removed;
    }

    public void setFish(int fish) {
        this.fish = fish;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}

