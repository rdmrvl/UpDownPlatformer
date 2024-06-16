// Saya [Marvel Ravindra Dioputra 2200481] mengerjakan evaluasi Tugas Masa Depan
// dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
// maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package viewmodel;  // deklarasi GameViewModel ada di folder viewmodel

import model.GameModel;                 // import GameModel dari folder model
import javax.swing.*;                   // import javax.swing untuk membuat GUI
import java.awt.event.ActionEvent;      // import ActionEvent untuk menangani event
import java.awt.event.ActionListener;   // import ActionListener untuk menangani action

public class GameViewModel {
    private final GameModel model;                  // deklarasi model
    private final Timer timer;                      // timer untuk mengupdate game teterusan
    private final Runnable showMainMenuCallback;    // callback untuk kembali ke mainmenu

    // konstruktor GameViewModel
    public GameViewModel(GameModel model, Runnable showMainMenuCallback) {
        this.model = model;                                 // inisialisasi model
        this.showMainMenuCallback = showMainMenuCallback;   // inisialisasi callback mainmenu
        this.timer = new Timer(28, new ActionListener() {   // inisialisasi timer dengan interval 28 milisec
            @Override
            // method dipanggil setiap timer berjalan
            public void actionPerformed(ActionEvent e) {
                model.updateGame();         // memanggil updateGame di model untuk refresh game
                if (model.isGameOver()) {   // memeriksa apakah game over
                    stopGame();             // memanggil stopGame jika permainan berakhir
                }
            }
        });
    }

    // memanggil data yang dilempar dari GameModel.java
    public int getCharacterX() {
        return model.getCharacterX();
    }

    public int getCharacterY() {
        return model.getCharacterY();
    }

    public int getBackgroundX() {
        return model.getBackgroundX();
    }

    public int getAnimationFrame() {
        return model.getAnimationFrame();
    }

    public boolean isJumping() {
        return model.isJumping();
    }

    public int getObstacleX() {
        return model.getObstacleX();
    }

    public int getObstacleY() {
        return model.getObstacleY();
    }

    public int getTopObstacleX() {
        return model.getTopObstacleX();
    }

    public int getTopObstacleY() {
        return model.getTopObstacleY();
    }

    public int getStepableX() {
        return model.getStepableX();
    }
    
    public int getStepableY() {
        return model.getStepableY();
    }

    public int getScore() {
        return model.getScore();
    }

    public int getUp() {
        return model.getUp();
    }
    
    public int getDown() {
        return model.getDown();
    }    

    public boolean isGameOver() {
        return model.isGameOver();
    }

    public void moveCharacterLeft() {
        model.moveCharacter(-5);
    }

    public void moveCharacterRight() {
        model.moveCharacter(5);
    }

    public void jump() {
        model.startJump();
    }

    public void updateGame() {
        model.updateGame();
    }

    public void resetBackgroundPosition() {
        model.resetBackgroundPosition();
    }

    public void stopBackgroundMusic() {
        model.stopBackgroundMusic();
    }

    public void startGame() {
        model.resetBackgroundPosition();
        model.resetGame();
        timer.start();
    }

    public void resetGame() {
        model.resetGame();
        timer.start();
    }

    public void stopGame() {
        timer.stop();
    }

    public void showMainMenu() {
        stopGame();
        showMainMenuCallback.run();
    }

}