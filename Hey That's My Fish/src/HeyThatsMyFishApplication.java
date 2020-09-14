/*
Ismat Syah Imran
Mr. Tully
Period 4
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class HeyThatsMyFishApplication extends Application {

    public static final int ORANGE_TURN = 1;
    public static final int YELLOW_TURN = 2;
    public static final int ORANGE_WINS = 3;
    public static final int YELLOW_WINS = 4;
    public static final int TIE = 5;

    private int status;
    private GraphicsContext gc;
    private HeyThatsMyFishPlayer orangePlayer;
    private HeyThatsMyFishPlayer yellowPlayer;
    private HeyThatsMyFishPlayer currentPlayer;
    private HeyThatsMyFishMap map;

    private int currentRow1;
    private int currentCol1;

    private Image oneFish;
    private Image twoFish;
    private Image threeFish;
    private Image orangePenguin;
    private Image yellowPenguin;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load images
        oneFish = new Image("File:Images/one_fish.png");
        twoFish = new Image("File:Images/two_fish.png");
        threeFish = new Image("File:Images/three_fish.png");
        orangePenguin = new Image("File:Images/orange.png");
        yellowPenguin = new Image("File:Images/yellow.png");
        reset();
        Group group = new Group();
        Canvas canvas = new Canvas(500, 500);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(event -> {
            if (((int) event.getX() > 390 && (int) event.getX() < 490) && ((int) event.getY() > 452 && (int) event.getY() < 482))
                reset();
            else
                if ((status == ORANGE_TURN || status == YELLOW_TURN) && !(orangePlayer.getRemoved() && yellowPlayer.getRemoved()))
                    controls(event);
            paint(gc);
        });
        paint(gc);
        canvas.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void reset() {
        status = ORANGE_TURN;
        map = new HeyThatsMyFishMap();
        orangePlayer = new HeyThatsMyFishPlayer();
        yellowPlayer = new HeyThatsMyFishPlayer();

        //assigns penguins to players
        for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {
            orangePlayer.getPenguin(i).setOrange(true);
            yellowPlayer.getPenguin(i).setYellow(true);
        }
    }


    public void controls(MouseEvent event) {

        if (status == ORANGE_TURN)
            currentPlayer = orangePlayer;
        else
            currentPlayer = yellowPlayer;

        //checks if all penguins have been placed
        boolean allPenguinsPlaced = true;

        for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {
            if (!orangePlayer.getPenguin(i).getPlaced() || !yellowPlayer.getPenguin(i).getPlaced()) {
                allPenguinsPlaced = false;
            }
        }

        if ((int) event.getX() >= 0 && (int) event.getX() <= 500 && (int) event.getY() >= 0 && (int) event.getY() <= 300) {

            //runs if all penguins have been placed
            if (allPenguinsPlaced) {

                //runs if a penguin has not been chosen
                if (!currentPlayer.getVerified()) {
                    for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {

                        //checks if selected row and column has a penguin
                        if (currentPlayer.getPenguin(i).getColPosition() == (int) (event.getX() / 50) && currentPlayer.getPenguin(i).getRowPosition() == (int) (event.getY() / 50)) {

                            //selects penguin
                            currentPlayer.setSelected(i);

                            //saves row and column of selected penguin
                            currentRow1 =
                                    currentPlayer.getPenguin(currentPlayer.getSelected()).getRowPosition();
                            currentCol1 =
                                    currentPlayer.getPenguin(currentPlayer.getSelected()).getColPosition();

                            //confirms that row and column of penguin have
                            // been saved
                            currentPlayer.setVerified(true);
                        }
                    }
                }

                //runs if a penguin has been chosen
                else {
                    int r = (int) event.getY() / 50;
                    int c = (int) event.getX() / 50;

                    //if the current penguin is pressed again, deselect it
                    if (currentCol1 == c && currentRow1 == r) {
                        currentPlayer.setVerified(false);
                    }

                    //checks if move is diagonal/orthogonal
                    else
                        if (currentRow1 == r || currentCol1 == c || Math.abs(currentCol1 - c) == Math.abs(currentRow1 - r)) {

                            //runs if the move is valid
                            if (valid(r, c, currentRow1, currentCol1)) {
                                System.out.println(valid(r, c, currentRow1,
                                        currentCol1));

                                //adds fish and removes tile the penguin is
                                // currently on
                                if (map.getTile(currentRow1, currentCol1).getOneFish()) {
                                    currentPlayer.setFish(currentPlayer.getFish() + 1);
                                    map.getTile(currentRow1, currentCol1).setOneFish(false);
                                } else
                                    if (map.getTile(currentRow1, currentCol1).getTwoFish()) {
                                        currentPlayer.setFish(currentPlayer.getFish() + 2);
                                        map.getTile(currentRow1, currentCol1).setTwoFish(false);
                                    } else
                                        if (map.getTile(currentRow1,
                                                currentCol1).getThreeFish()) {
                                            currentPlayer.setFish(currentPlayer.getFish() + 3);
                                            map.getTile(currentRow1,
                                                    currentCol1).setThreeFish(false);
                                        }

                                //changes the penguin status of the new and
                                // previous tile
                                map.getTile(currentRow1, currentCol1).setEmpty(true);
                                map.getTile(currentRow1, currentCol1).setPenguin(false);
                                map.getTile(r, c).setPenguin(true);

                                //moves penguin
                                currentPlayer.getPenguin(currentPlayer.getSelected()).setRowPosition(r);
                                currentPlayer.getPenguin(currentPlayer.getSelected()).setColPosition(c);

                                currentPlayer.setVerified(false);
                                status();
                            }
                        }
                }

            }

            //runs if not all penguins have been placed
            else {
                for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {

                    //runs if the penguin has not been placed
                    if (!currentPlayer.getPenguin(i).getPlaced()) {

                        boolean valid = true;

                        //checks if selected tile has one fish
                        if (!map.getTile((int) (event.getY() / 50),
                                (int) (event.getX() / 50)).getOneFish() || map.getTile((int) event.getY() / 50, (int) event.getX() / 50).getPenguin())
                            valid = false;

                        if (valid) {
                            currentPlayer.getPenguin(i).setRowPosition((int) (event.getY() / 50));
                            currentPlayer.getPenguin(i).setColPosition((int) (event.getX() / 50));
                            currentPlayer.getPenguin(i).setPlaced(true);
                            map.getTile((int) event.getY() / 50,
                                    (int) event.getX() / 50).setPenguin(true);
                            if (status == ORANGE_TURN)
                                status = YELLOW_TURN;
                            else
                                status = ORANGE_TURN;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println((int) event.getX() / 50 + " " + (int) event.getY() / 50);
    }

    public boolean valid(int r, int c, int currentRow1, int currentCol1) {
        boolean valid = true;

        //checks if left move is valid
        if (currentRow1 == r && currentCol1 > c) {
            for (int c1 = currentCol1 - 1; c1 >= c; c1--) { //c1 is all the
                // columns to the left
                if (map.getTile(r, c1).getEmpty() || map.getTile(r, c1).getPenguin())
                    valid = false;
            }
        }

        //checks if up move is valid
        else
            if (currentCol1 == c && currentRow1 > r) {
                for (int r1 = currentRow1 - 1; r1 >= r; r1--) { //r1 is all the
                    // rows above
                    if (map.getTile(r1, c).getEmpty() || map.getTile(r1, c).getPenguin())
                        valid = false;
                }
            }

            //checks if right move is valid
            else
                if (currentRow1 == r && currentCol1 < c) {
                    for (int c1 = currentCol1 + 1; c1 <= c; c1++) { //c1 is
                        // all the
                        // columns to the right
                        if (map.getTile(r, c1).getEmpty() || map.getTile(r,
                                c1).getPenguin())
                            valid = false;
                    }
                }

                //checks if down move is valid
                else
                    if (currentCol1 == c && currentRow1 < r) {
                        for (int r1 = currentRow1 + 1; r1 <= r; r1++) { //r1
                            // is all the
                            // rows beneath
                            if (map.getTile(r1, c).getEmpty() || map.getTile(r1, c).getPenguin())
                                valid = false;
                        }
                    }

                    //checks if up left move is valid
                    else
                        if (currentRow1 > r && currentCol1 > c) {
                            int r1 = currentRow1 - 1;
                            int c1 = currentCol1 - 1;
                            do {
                                if (map.getTile(r1, c1).getEmpty() || map.getTile(r1, c1).getPenguin())
                                    valid = false;
                                r1--;
                                c1--;
                            } while (r1 >= r && c1 >= c);
                        }

                        //checks if down right move is valid
                        else
                            if (currentRow1 < r && currentCol1 < c) {
                                int r1 = currentRow1 + 1;
                                int c1 = currentCol1 + 1;
                                do {
                                    if (map.getTile(r1, c1).getEmpty() || map.getTile(r1, c1).getPenguin())
                                        valid = false;
                                    r1++;
                                    c1++;
                                } while (r1 <= r && c1 <= c);
                            }

                            //checks if up right move is valid
                            else
                                if (currentRow1 > r && currentCol1 < c) {
                                    int r1 = currentRow1 - 1;
                                    int c1 = currentCol1 + 1;
                                    do {
                                        if (map.getTile(r1, c1).getEmpty() || map.getTile(r1, c1).getPenguin())
                                            valid = false;
                                        r1--;
                                        c1++;
                                    } while (r1 >= r && c1 <= c);
                                }

                                //checks if down left move is valid
                                else
                                    if (currentRow1 < r && currentCol1 > c) {
                                        int r1 = currentRow1 + 1;
                                        int c1 = currentCol1 - 1;
                                        do {
                                            if (map.getTile(r1, c1).getEmpty() || map.getTile(r1, c1).getPenguin())
                                                valid = false;
                                            r1++;
                                            c1--;
                                        } while (r1 <= r && c1 >= c);
                                    }

        return valid;
    }

    //changes the status accordingly
    public void status() {
        //checks if either player no longer has any moves
        if (!orangePlayer.getRemoved())
            finished(orangePlayer);
        if (!yellowPlayer.getRemoved())
            finished(yellowPlayer);

        if (orangePlayer.getRemoved() && yellowPlayer.getRemoved()) { //runs
            // if both players can't move
            if (orangePlayer.getFish() > yellowPlayer.getFish())
                status = ORANGE_WINS;
            else
                if (orangePlayer.getFish() < yellowPlayer.getFish())
                    status = YELLOW_WINS;
                else
                    status = TIE;
        } else
            if (status == ORANGE_TURN && !yellowPlayer.getRemoved())
                status = YELLOW_TURN;
            else
                if (status == YELLOW_TURN && !orangePlayer.getRemoved())
                    status = ORANGE_TURN;
                else
                    if (status == ORANGE_TURN && yellowPlayer.getRemoved())
                        status = ORANGE_TURN;
                    else
                        if (status == YELLOW_TURN && orangePlayer.getRemoved())
                            status = YELLOW_TURN;
    }


    //checks if the player no longer has any moves
    public void finished(HeyThatsMyFishPlayer player) {
        boolean noMoreMoves = true;
        for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {
            int r = player.getPenguin(i).getRowPosition();
            int c = player.getPenguin(i).getColPosition();

            //checks left, top, & top left moves
            if (r != 0 && c != 0) {
                if (valid(r - 1, c, r, c) || valid(r, c - 1, r, c) || valid(r - 1, c - 1, r, c))
                    noMoreMoves = false;
            }
            //checks right, top, and top right moves
            if (r != 0 && c != HeyThatsMyFishMap.NUM_COLUMNS - 1) {
                if (valid(r - 1, c, r, c) || valid(r, c + 1, r, c) || valid(r - 1, c + 1, r, c))
                    noMoreMoves = false;
            }
            //checks left, bottom, and bottom left moves
            if (r != HeyThatsMyFishMap.NUM_ROWS - 1 && c != 0) {
                if (valid(r + 1, c, r, c) || valid(r, c - 1, r, c) || valid(r + 1, c - 1, r, c))
                    noMoreMoves = false;
            }
            //checks right, bottom, and bottom right moves
            if (r != HeyThatsMyFishMap.NUM_ROWS - 1 && c != HeyThatsMyFishMap.NUM_COLUMNS - 1) {
                if (valid(r + 1, c, r, c) || valid(r, c + 1, r, c) || valid(r + 1, c + 1, r, c))
                    noMoreMoves = false;
            }
        }
        if (noMoreMoves) {
            player.setRemoved(true);

            //removes penguin from the board and collects the ice floes
            // beneath them
            for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {
                int r = player.getPenguin(i).getRowPosition();
                int c = player.getPenguin(i).getColPosition();
                //adds fish and removes tile the penguin is currently on
                if (map.getTile(r, c).getOneFish()) {
                    currentPlayer.setFish(currentPlayer.getFish() + 1);
                    map.getTile(r, c).setOneFish(false);
                } else
                    if (map.getTile(r, c).getTwoFish()) {
                        currentPlayer.setFish(currentPlayer.getFish() + 2);
                        map.getTile(r, c).setTwoFish(false);
                    } else
                        if (map.getTile(r, c).getThreeFish()) {
                            currentPlayer.setFish(currentPlayer.getFish() + 3);
                            map.getTile(r, c).setThreeFish(false);
                        }

                //changes the penguin status of the tile
                map.getTile(r, c).setEmpty(true);
                map.getTile(r, c).setPenguin(false);

                player.getPenguin(i).setRowPosition(-1000);
                player.getPenguin(i).setColPosition(-1000);
            }
        }
    }

    public void paint(GraphicsContext gc) {

        //water
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, 500, 300);

        //display
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 300, 500, 200);

        //draws fish and ice floes
        for (int r = 0; r < HeyThatsMyFishMap.NUM_ROWS; r++) {
            for (int c = 0; c < HeyThatsMyFishMap.NUM_COLUMNS; c++) {
                if (map.getTile(r, c).getOneFish()) //draws one-fish tiles
                    gc.drawImage(oneFish, c * 50, r * 50, 50, 50);
                else
                    if (map.getTile(r, c).getTwoFish()) //draws two-fish tiles
                        gc.drawImage(twoFish, c * 50, r * 50, 50, 50);
                    else
                        if (map.getTile(r, c).getThreeFish()) //draws three-fish
                            // tiles
                            gc.drawImage(threeFish, c * 50, r * 50, 50, 50);
            }
        }

        for (int i = 0; i < HeyThatsMyFishPlayer.NUM_PENGUINS; i++) {
            //draws orange penguins
            gc.drawImage(orangePenguin,
                    orangePlayer.getPenguin(i).getColPosition() * 50,
                    orangePlayer.getPenguin(i).getRowPosition() * 50, 50, 50);
            //draws yellow penguins
            gc.drawImage(yellowPenguin,
                    yellowPlayer.getPenguin(i).getColPosition() * 50,
                    yellowPlayer.getPenguin(i).getRowPosition() * 50, 50, 50);
        }
        //draws selected penguin
        if (orangePlayer.getVerified())
            gc.drawImage(orangePenguin, currentCol1 * 50 - 5,
                    currentRow1 * 50 - 5, 60, 60);
        else
            if (yellowPlayer.getVerified())
                gc.drawImage(yellowPenguin, currentCol1 * 50 - 5,
                        currentRow1 * 50 - 5, 60, 60);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Comic Sans MS", 25));
        gc.fillText("Orange Player's Score: " + orangePlayer.getFish(), 5, 325);
        gc.fillText("Yellow Player's Score: " + yellowPlayer.getFish(), 5, 350);

        if (status == ORANGE_TURN)
            gc.fillText("Orange Player's Turn", 5, 450);
        else
            if (status == YELLOW_TURN)
                gc.fillText("Yellow Player's Turn", 5, 450);
            else
                if (status == ORANGE_WINS)
                    gc.fillText("Orange Player Wins!", 5, 450);
                else
                    if (status == YELLOW_WINS)
                        gc.fillText("Yellow Player Wins!", 5, 450);
                    else
                        if (status == TIE)
                            gc.fillText("The game is a tie", 5, 450);

        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setFill(Color.RED);
        gc.fillRect(390, 452, 100, 30);
        gc.setFill(Color.WHITE);
        gc.fillText("RESET", 410, 475);

    }
}