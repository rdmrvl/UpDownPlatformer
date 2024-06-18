// Saya [Marvel Ravindra Dioputra 2200481] mengerjakan evaluasi Tugas Masa Depan
// dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
// maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package model;                                  // deklarasi gameModel ada di folder model

import javax.sound.sampled.AudioInputStream;    // import AudioInputStream untuk menangani audio
import javax.sound.sampled.AudioSystem;         // import AudioSystem untuk menangani audio
import javax.sound.sampled.Clip;                // import Clip untuk play suara asset bgm atau sfx
import javax.sound.sampled.FloatControl;        // import FloatControl untuk mengatur volume audio
import java.io.File;                            // import File untuk menangani file dan direktori

public class GameModel {
    // PLAYER
    private String username;                // username untuk menyimpan data pemain saat ini
    private int characterX;                 // posisi X karakter
    private int characterY;                 // posisi Y karakter
    private int velocityY;                  // kecepatan vertikal karakter (Y)
    private boolean jumping;                // status apakah karakter sedang melompat
    private final int gravity = 1;          // nilai gravitasi untuk karakter
    private final int jumpStrength = 15;    // kekuatan lompatan karakter
    private int animationFrame = 0;         // frame saat ini untuk animasi karakter
    private int animationCounter = 0;       // penghitung untuk kecepatan animasi
    private final int animationSpeed = 8;   // kecepatan animasi karakter

    // GAME
    private int backgroundX;                // posisi X background untuk efek pergerakan dari kanan-kiri
    private int obstacleX;                  // posisi X dari obstacle bawah
    private int obstacleY;                  // posisi Y dari obstacle bawah
    private int topObstacleX;               // posisi X dari obstacle gelantungan atas (sbg background)
    private int topObstacleY;               // posisi Y dari obstacle gelantungan atas (sbg background)
    private int stepableX;                  // posisi X dari platform yang bisa diinjak (untuk top obstacle)
    private int stepableY;                  // posisi Y dari platform yang bisa diinjak (untuk top obstacle)
    private boolean bottomObstacle;         // menandai obstacle agar obstacle bergiliran munculnya up/down
    private boolean onObstacle = false;     // menandai apakah karakter menginjak obstacle bawah (down)
    private boolean onStepable = false;     // menandai apakah karakter menginjak obstacle atas stepable (up)
    private final int backgroundSpeed = 3;  // kecepatan background
    private final int obstacleSpeed = 3;    // kecepatan obstacle
    private int score;                      // skor user
    private int up;                         // menghitung berapa kali karakter mendarat di stepable (up)
    private int down;                       // menghitung berapa kali karakter mendarat di obstacle (down)
    private boolean exploded;               // untuk menandai apakah karakter sudah meledak (kalau menabrak obstacle/collision)
    private int explosionX;                 // posisi X ledakan
    private int explosionY;                 // posisi Y ledakan
    private boolean explosionSoundPlayed;   // menandai sfx ledakan jika karakter menabrak
    private Clip backgroundClip;            // objek Clip untuk memutar bgm
    private boolean gameOver;               // status apakah game berakhir/belum
    private boolean jumpFlag = false;

    public GameModel(String username) {
        this.username = username;       // mengatur nama username untuk melacak pemain saat ini
        characterX = 50;                // mengatur posisi X awal karakter
        characterY = 200;               // mengatur posisi Y awal karakter
        backgroundX = 0;                // mengatur posisi X awal background
        velocityY = 0;                  // mengatur kecepatan Y awal karakter
        jumping = false;                // mengatur status melompat karakter
        obstacleX = 612;                // mengatur posisi X awal "down"
        obstacleY = 200;                // mengatur posisi Y awal "down"
        topObstacleX = 612;             // mengatur posisi X awal "up"
        topObstacleY = 0;               // mengatur posisi Y awal "up"
        stepableX = topObstacleX;       // mengatur posisi awal platform yang bisa diinjak (untuk "up")
        stepableY = 200;                // mengatur posisi awal platform yang bisa diinjak (untuk "up")
        gameOver = false;               // mengatur status awal game sebagai belum berakhir
        score = 0;                      // mengatur skor awal
        up = 0;                         // mengatur hitungan awal mendarat di "up"
        down = 0;                       // mengatur hitungan awal mendarat di "down"
        bottomObstacle = true;          // mengatur obstacle mana yang spawn duluan
        exploded = false;               // mengatur status awal ledakan karakter sebagai tidak meledak (karena belum nabrak)
        explosionSoundPlayed = false;   // mengatur status awal sfx ledakan sebagai belum diputar
        playBackgroundMusic();          // memanggil metode untuk bgm
        jumpFlag = false;               // menandakan agar karakter tidak meledak saat spawn
    }


    public void resetGame() {
        this.username = username;       // mengatur nama username untuk melacak pemain saat ini
        characterX = 50;                // mengatur posisi X awal karakter
        characterY = 200;               // mengatur posisi Y awal karakter
        backgroundX = 0;                // mengatur posisi X awal background
        velocityY = 0;                  // mengatur kecepatan Y awal karakter
        jumping = false;                // mengatur status melompat karakter
        obstacleX = 612;                // mengatur posisi X awal "down"
        obstacleY = 200;                // mengatur posisi Y awal "down"
        topObstacleX = 612;             // mengatur posisi X awal "up"
        topObstacleY = 0;               // mengatur posisi Y awal "up"
        stepableX = topObstacleX;       // mengatur posisi awal platform yang bisa diinjak (untuk "up")
        stepableY = 200;                // mengatur posisi awal platform yang bisa diinjak (untuk "up")
        gameOver = false;               // mengatur status awal game sebagai belum berakhir
        score = 0;                      // mengatur skor awal
        bottomObstacle = true;          // mengatur obstacle mana yang spawn duluan
        exploded = false;               // mengatur status awal ledakan karakter sebagai tidak meledak (karena belum nabrak)
        explosionSoundPlayed = false;   // mengatur status awal sfx ledakan sebagai belum diputar
        animationFrame = 0;             // frame saat ini untuk animasi karakter
        animationCounter = 0;           // penghitung untuk kecepatan animasi
        up = 0;                         // mengatur hitungan awal mendarat di "up"
        down = 0;                       // mengatur hitungan awal mendarat di "down"
        jumpFlag = false;
    }

    // getter untuk melempar data ke viewmodel
    public int getStepableX() {
        return stepableX;
    }

    public int getStepableY() {
        return stepableY;
    }

    public int getTopObstacleX() {
        return topObstacleX;
    }

    public int getTopObstacleY() {
        return topObstacleY;
    }

    public int getCharacterX() {
        return characterX;
    }

    public int getCharacterY() {
        return characterY;
    }

    public int getBackgroundX() {
        return backgroundX;
    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public boolean isJumping() {
        return jumping;
    }

    public int getObstacleX() {
        return obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }

    public int getScore() {
        return score;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isExploded() {
        return exploded;
    }

    public int getExplosionX() {
        return explosionX;
    }

    public int getExplosionY() {
        return explosionY;
    }

    public void moveCharacter(int deltaX) {
        characterX += deltaX;
    }

    // method untuk memutar sfx apabila karakter lompat
    public void startJump() {
        if (!jumping) {
            try {
                // play sfx dari folder assets
                jumpFlag = true;
                File soundFile = new File("assets/jump.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                // atur volume 50%
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volume = volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * 0.5f;
                volumeControl.setValue(volume);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            velocityY = -jumpStrength;
            jumping = true;
        }
    }

    // method untuk memutar bgm
    private void playBackgroundMusic() {
        try {
            // play bgm dari folder assets
            File soundFile = new File("assets/bgm.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);

            // atur volume 50%
            FloatControl volumeControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float volume = volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * 0.7f;
            volumeControl.setValue(volume);

            // agar bgm looping
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method untuk memberhentikan bgm jika balik ke mainmenu
    public void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    // method untuk update game agar terus berjalan
    public void updateGame() {
        // jika game over maka game berhenti
        if (gameOver || exploded) {
            return;
        }

        // pergerakan background agar dari kanan -kiri
        backgroundX -= backgroundSpeed;

        // spawn obstacle "up" / "down" berdasarkan variabel booolean bottomObstacle sebelumnya
        if (bottomObstacle) {
            // spawn "down"
            obstacleX -= obstacleSpeed;
            if (obstacleX < -48) {
                obstacleX = 200;
                bottomObstacle = false; // mengubah status agar obstacle berikutnya "up"
            }
        } else {
            // spawn "up"
            topObstacleX -= obstacleSpeed;
            stepableX = topObstacleX;
            if (topObstacleX < -48) {
                topObstacleX = 200;
                bottomObstacle = true;  // mengubah status agar obstacle berikutnya "down"
            }
        }

        // menentukan jarak horizontal atau jarak X antara obstacle "up" dan "down"
        int minGap = 240; // Adjust as needed

        // spawn obstacle baru jika jarak nya sudha jarak minimum (minGap)
        if (obstacleX < 612 - minGap || topObstacleX < 612 - minGap) {
            if (bottomObstacle) {
                topObstacleX = obstacleX + minGap; // set posisi obstacle "up"
                stepableX = topObstacleX;
            } else {
                obstacleX = topObstacleX + minGap; // set posisi obstacle "down"
            }
        }

        // gravitasi dan lompat karakter
        characterY += velocityY;
        velocityY += gravity;

        // mengecek jika karakter sedang menginjak obstacle
        if (checkStepping(obstacleX, obstacleY, 48, 100, false) || checkStepping(stepableX, stepableY, 53, 5, true)) {
            characterY = obstacleY - 50;
            velocityY = 0;
            jumping = false;
        }

        boolean steppedOnObstacle = checkStepping(obstacleX, obstacleY, 48, 100, false);
        boolean steppedOnStepable = checkStepping(stepableX, stepableY, 53, 10, true);

        // memeriksa kembali apakah sedang menginjak obstacle
        if (!steppedOnObstacle && !steppedOnStepable) {
            onObstacle = false;
            onStepable = false;
        }

        // mengecek collision dengan sisi obstacle, jika nabrak play sfx ledakan
        if (checkSideCollision(obstacleX, obstacleY, 48, 100) && !steppedOnObstacle  || checkSideCollision(stepableX, stepableY, 53, 1)) {
            triggerExplosion(characterX, characterY);
            playExplosionSound();
            gameOver = true;
        }

        // mengecek collision dengan lantai selain obstacle
        if (!onObstacle && !onStepable && characterY >= 200 && jumpFlag == true) {
            triggerExplosion(characterX, characterY);
            playExplosionSound();
            gameOver = true;
        }

        // ground collision agar karakter dapat memijak
        if (characterY >= 200) {
            characterY = 200;
            velocityY = 0;
            jumping = false;
        }

        // update animasi
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationCounter = 0;
            animationFrame = (animationFrame + 1) % 3;
        }

        // update score, up, down ke database DB.java
        if (score > 0 && (score > DB.getHighScore(username) || DB.getHighScore(username) == -1)) {
            DB.updateScore(username, score, up, down);
        }
    }

    // method untuk mengtrigger efek ledakan, posisi ledakan dan status ledakan
    private void triggerExplosion(int x, int y) {
        exploded = true;
        explosionX = x;
        explosionY = y;
    }

    // method untuk memutar sfx ledakan
    private void playExplosionSound() {
        if (!explosionSoundPlayed) {
            try {
                File soundFile = new File("assets/explosion.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                // Adjust the volume to 50%
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volume = volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * 0.5f;
                volumeControl.setValue(volume);

                clip.start();
                explosionSoundPlayed = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // method untuk mengecek side collision antara karakter dengan sisi obstacle
    private boolean checkSideCollision(int obsX, int obsY, int obsWidth, int obsHeight) {
        int characterRight = characterX + 50;
        int characterBottom = characterY + 50;

        int obstacleRight = obsX + obsWidth;
        int obstacleBottom = obsY + obsHeight;

        boolean collisionX = characterRight > obsX && characterX < obstacleRight;
        boolean collisionY = characterBottom > obsY && characterY < obstacleBottom;

        // memeriksa collison pada sisi obstacle
        if (collisionX && collisionY) {
            if (characterRight > obsX && characterX < obstacleRight) {
                // set status gameover
                return true;
            }
        }

        return false;
    }

    // method untuk mengecek apakah karakter memijak obstacle atau tidak
    private boolean checkStepping(int obsX, int obsY, int obsWidth, int obsHeight, boolean isStepable) {
        int characterRight = characterX + 50;
        int characterBottom = characterY + 50;

        int obstacleRight = obsX + obsWidth;
        int obstacleTop = obsY;

        boolean collisionX = characterRight > obsX && characterX < obstacleRight;
        boolean collisionY = characterBottom >= obstacleTop && characterBottom <= obstacleTop + 10;

        // jika terdapat collision antara karakter dan obstacle
        if (collisionX && collisionY) {
            if (isStepable) {
                if (!onStepable) {
                    score += 10;
                    up++;
                    onStepable = true;
                    onObstacle = false;
                }
            } else {
                if (!onObstacle && !onStepable) {
                    down++;
                    score += 10;
                    onObstacle = true;
                    onStepable = false;
                }
            }
            return true;
        }

        return false;
    }

    // method untuk reset posisi background
    public void resetBackgroundPosition() {
        backgroundX = 0;
    }
}
