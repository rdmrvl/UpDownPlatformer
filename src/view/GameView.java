// Saya [Marvel Ravindra Dioputra 2200481] mengerjakan evaluasi Tugas Masa Depan
// dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
// maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package view; // deklarasi GameView ada di folder view

import viewmodel.GameViewModel;         // import GameViewModel dari paket 'viewmodel'
import javax.swing.*;                   // import javax.swing untuk membuat GUI
import java.awt.*;                      // import java.awt untuk layout GUI
import java.awt.event.ActionEvent;      // import ActionEvent untuk menangani event
import java.awt.event.ActionListener;   // import ActionListener untuk menangani action
import java.awt.event.KeyAdapter;       // import KeyAdapter untuk menangani event keyboard (input keyboard)
import java.awt.event.KeyEvent;         // import KeyEvent untuk menangani event key press (input keyboard)
import java.util.HashMap;               // import HashMap untuk menyimpan data key value
import java.util.Map;                   // import Map untuk menggunakan GUI

public class GameView extends JPanel {
    private final GameViewModel viewModel;              // deklarasi variabel viewModel dari GameViewModel
    private Image backgroundImage;                      // deklarasi variabel untuk menyimpan background
    private Image obstacleImage;                        // deklarasi variabel untuk menyimpan asset obstacle (down)
    private Image topObstacleImage;                     // deklarasi variabel untuk menyimpan asset obstacle (up)
    private Image stepableImage;                        // deklarasi variabel untuk menyimpan asset obstacle (stepable)
    private Image explosionImage;                       // deklarasi variabel untuk menyimpan asset explosion
    private Map<String, Image[]> characterAnimations;   // deklarasi variabel untuk menyimpan animasi karakter
    private boolean leftPressed = false;                // deklarasi variabel untuk menandakan tombol kiri ditekan
    private boolean rightPressed = false;               // deklarasi variabel untuk menandakan tombol kanan ditekan
    private JButton restartButton;                      // deklarasi variabel untuk tombol restart

    public GameView(GameViewModel viewModel) {
        this.viewModel = viewModel;                 // inisialisasi variabel viewModel
        loadImages();                               // memanggil metode loadImages untuk memuat assets
        setPreferredSize(new Dimension(460, 382));  // Mengatur ukuran windows
        setBackground(Color.WHITE);                 // Mengatur warna background

        Timer gameTimer = new Timer(28, e -> {  // timer untuk refresh ulang panel
            update();                           // memanggil metode update untuk refresh logika game
            repaint();                          // memanggil metode repaint untuk menggambar ulang panel/frame agar animasi berjalan
        });
        gameTimer.start();

        setFocusable(true);                     // mengatur panel agar menerima fokus
        requestFocusInWindow();                 // agar fokus pada panel menerima event key
        addKeyListener(new KeyAdapter() {       // KeyListener untuk menangani event key press
            @Override
            // method yang dijalankan ketika tombol ditekan
            public void keyPressed(KeyEvent e) {
                if (!viewModel.isGameOver()) {      // memeriksa apakah game over
                    switch (e.getKeyCode()) {       // memeriksa tombol mana yang ditekan
                        case KeyEvent.VK_LEFT:
                            leftPressed = true;     // leftPressed menjadi true jika tombol kiri ditekan
                            break;
                        case KeyEvent.VK_RIGHT:
                            rightPressed = true;    // rightPressed menjadi true jika tombol kanan ditekan
                            break;
                        case KeyEvent.VK_UP:
                            viewModel.jump();       // memanggil metode jump di viewModel jika tombol atas ditekan
                            break;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {  // memeriksa apakah tombol spasi ditekan
                    viewModel.stopBackgroundMusic();        // menghentikan bgm
                    viewModel.showMainMenu();               // menampilkan mainMenu
                }
            }

            @Override
            // method ketika tombol dilepas
            public void keyReleased(KeyEvent e) {
                if (!viewModel.isGameOver()) {      // memeriksa apakah game over
                    switch (e.getKeyCode()) {       // memeriksa tombol mana yang dilepas
                        case KeyEvent.VK_LEFT:
                            leftPressed = false;    // leftPressed menjadi false jika tombol kiri dilepas
                            break;
                        case KeyEvent.VK_RIGHT:
                            rightPressed = false;   // rightPressed menjadi false jika tombol kanan dilepas
                            break;
                    }
                }
            }
        });

        restartButton = new JButton("Restart");                 // membuat tombol "Restart"
        restartButton.setVisible(false);                        // mengatur tombol restart tidak terlihat awalnya
        restartButton.addActionListener(new ActionListener() {  // ActionListener untuk tombol restart
            @Override
            // method dijalankan ketika tombol ditekan
            public void actionPerformed(ActionEvent e) {
                viewModel.resetGame();                      // memanggil metode resetGame di viewModel untuk mengulang permainan
                restartButton.setVisible(false);            // menyembunyikan tombol restart
                requestFocusInWindow();                     // memastikan panel fokus untuk menerima event keyboard
            }
        });
        setLayout(null);                                    // mengatur layout panel posisi absolut/diem
        restartButton.setBounds(180, 176, 100, 30);         // mengatur posisi dan ukuran tombol restart
        add(restartButton);                                 // menambahkan tombol restart
    }

    // method untuk memuat gambar/asset yang dipakai
    private void loadImages () {
        backgroundImage = new ImageIcon("assets/background.png").getImage();    // asset background
        obstacleImage = new ImageIcon("assets/down.png").getImage();            // asset "down"
        topObstacleImage = new ImageIcon("assets/up.png").getImage();           // asset "up"
        stepableImage = new ImageIcon("assets/stepable.png").getImage();        // asset stepable
        explosionImage = new ImageIcon("assets/explosion.png").getImage();      // asset ledakan

        characterAnimations = new HashMap<>();                              // inisialisasi untuk animasi karakter
        characterAnimations.put("run", new Image[]{                         // menambahkan animasi lari
                // asset karakter lari
                new ImageIcon("assets/character_run_1.png").getImage(),
                new ImageIcon("assets/character_run_2.png").getImage(),
                new ImageIcon("assets/character_run_3.png").getImage()
        });
        characterAnimations.put("jump", new Image[]{                        // Menambahkan animasi lompat
                new ImageIcon("assets/character_jump.png").getImage()
        });
        characterAnimations.put("jump_run", new Image[]{                    // Menambahkan animasi lari sambil lompat (sama aja sebenernya)
                new ImageIcon("assets/character_jump_run.png").getImage()
        });
    }

    // method untuk memperbarui permainan
    private void update () {
        if (!viewModel.isGameOver()) {          // memeriksa apakah game over
            if (leftPressed) {                  // jika tombol kiri ditekan
                viewModel.moveCharacterLeft();  // memanggil metode moveCharacterLeft di viewModel
            }
            if (rightPressed) {                 // jika tombol kanan ditekan
                viewModel.moveCharacterRight(); // memanggil metode moveCharacterRight di viewModel
            }
            viewModel.updateGame();             // memanggil metode updateGame di viewModel
        }
    }

    @Override
    // method untuk menggambar komponen
    protected void paintComponent (Graphics g){
        super.paintComponent(g);                                // memanggil metode paintComponent dari JPanel
        int backgroundX = viewModel.getBackgroundX();           // mendapatkan posisi X latar belakang
        int backgroundWidth = backgroundImage.getWidth(this);   // mendapatkan lebar gambar latar belakang

        // digambar tiga kali biar looping background halus
        g.drawImage(backgroundImage, backgroundX, 0, this);
        g.drawImage(backgroundImage, backgroundX + backgroundWidth, 0, this);
        g.drawImage(backgroundImage, backgroundX + backgroundWidth * 2, 0, this);

        // jika gambar pertama keluar dari layar, reset posisinya
        if (backgroundX <= -backgroundWidth) {
            viewModel.resetBackgroundPosition(); // memanggil metode resetBackgroundPosition di viewModel
        }

        // menggambar obstacle
        g.drawImage(obstacleImage, viewModel.getObstacleX(), viewModel.getObstacleY(), this);           // menggambar down
        g.drawImage(topObstacleImage, viewModel.getTopObstacleX(), viewModel.getTopObstacleY(), this);  // menggambar up
        g.drawImage(stepableImage, viewModel.getStepableX(), viewModel.getStepableY(), this);           // menggambar stepable

        Image[] currentAnimation;   // deklarasi array untuk animasi saat ini
        if (viewModel.isJumping()) {                                        // jika karakter melompat
            if (leftPressed || rightPressed) {                              // jika tombol kiri atau kanan ditekan
                currentAnimation = characterAnimations.get("jump_run");     // menggunakan animasi lari sambil lompat
            } else {
                currentAnimation = characterAnimations.get("jump");         // menggunakan animasi lompat
            }
        } else {
            currentAnimation = characterAnimations.get("run");              // menggunakan animasi lari
        }

        // animationFrame berada dalam array animasi
        int animationFrame = viewModel.getAnimationFrame() % currentAnimation.length;

        if (viewModel.isGameOver()) { // jika game over
            // menggambar gambar explosion
            g.drawImage(explosionImage, viewModel.getCharacterX(), viewModel.getCharacterY(), this);
            // menampilkan tombol restart
            restartButton.setVisible(true);
        } else {
            // lanjut menggambar karakter
            g.drawImage(currentAnimation[animationFrame], viewModel.getCharacterX(), viewModel.getCharacterY(), this);
        }

        // menampilkan score
        g.setColor(Color.BLACK);                                // mengatur warna teks menjadi hitam
        g.setFont(new Font("Helvetica", Font.BOLD, 16));        // mengatur font teks
        g.drawString("Score: " + viewModel.getScore(), 20, 20); // teks score
        g.drawString("Up: " + viewModel.getUp(), 20, 50);       // teks nilai 'Up'
        g.drawString("Down: " + viewModel.getDown(), 20, 80);   // teks nilai 'Down'
    }
}