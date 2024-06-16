// Saya [Marvel Ravindra Dioputra 2200481] mengerjakan evaluasi Tugas Masa Depan
// dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
// maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

import model.GameModel;         // import kelas GameModel dari folder model
import view.GameView;           // import kelas GameView dari folder view
import viewmodel.GameViewModel; // import kelas GameViewModel folder  viewmodel
import javax.swing.*;           // import javax.swing untuk GUI
import java.awt.*;              // import java.awt untuk komponen komponen GUI

public class Main {
    private static JFrame frame;            // deklarasi variabel JFrame
    private static GameView gameView;       // deklarasi variabel GameView
    private static GameViewModel viewModel; // deklarasi variabel GameViewModel
    private static MainMenu mainMenu;       // deklarasi variabel MainMenu untuk membuka tampilan awal game

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // judul windows
            frame = new JFrame("Up Down | TMD | 2200481 - Marvel Ravindra Dioputra");   
            // agar windows dapat di exit
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                             
            // mengatur ukuran/dimensi windows
            frame.setSize(460, 382);                                             
            // mengatur layout windows
            frame.setLayout(new BorderLayout());                                              
            // set biar windows di tengah
            frame.setLocationRelativeTo(null);                                              
            // agar windows muncul
            frame.setVisible(true);                                                         

            mainMenu = new MainMenu(Main::startGame);   // membuat objek mainMenu saat memulai game
            showMainMenu();                             // menampilkan main menu (MainMenu.java)
        });
    }

    private static void showMainMenu() {
        mainMenu.refreshUserData();                 // update data dari tabel setiap kali ke MainMenu

        frame.getContentPane().removeAll();         // menghaapus tampilan game untuk kembali ke menu
        frame.add(mainMenu, BorderLayout.CENTER);   // menambahkan mainMenu ke panel
        frame.revalidate();                         // validasi ulang layout panel
        frame.repaint();                            // menampilkan panel dari main menu
    }

    private static void startGame() {
        String username = mainMenu.getSelectedUsername();               // mendapatkan username yang dipilih dari mainMenu
        if (username != null) {
            // jika user dipilih
            GameModel model = new GameModel(username);                  // membuat objek model dan melempar username yang bermain
            viewModel = new GameViewModel(model, Main::showMainMenu);   // membuat objek viewModel dan showMainMenu
            gameView = new GameView(viewModel);                         // membuat objek gameView untuk menampilkan tampilan

            frame.getContentPane().removeAll(); // menghaapus tampilan menu untuk pindah ke game
            frame.add(gameView);                // menambahkan gameView/tampilan gamenya ke panel
            frame.revalidate();                 // validasi ulang layout panel
            frame.repaint();                    // menampilkan panel dari game

            gameView.requestFocusInWindow();    // agar windows nya ditampilkan
            viewModel.startGame();              // memulai game melalui viewModel
        } else {
            // jika user tidak ada yang dipilih
            // error handling harus milih user
            JOptionPane.showMessageDialog(frame, "Please select a user to start the game.");
        }
    }
}
