import model.DB;    // import kelas DB folder model untuk koneksi database

import javax.swing.*;                       // import javax.swing untuk membuat GUI
import javax.swing.table.DefaultTableModel; // import DefaultTableModel untuk tabel GUI
import java.awt.*;                          // import java.awt untuk layout GUI
import java.awt.event.ActionEvent;          // import ActionEvent untuk menangani event
import java.awt.event.ActionListener;       // import ActionListener untuk menangani action
import java.io.File;                        // import File untuk file handling
import java.io.IOException;                 // import IOException untuk exception handling
import java.sql.ResultSet;                  // import ResultSet untuk menangani hasil query database
import java.sql.SQLException;               // import SQLException untuk menangani exception SQL

// JPanel MainMenu
public class MainMenu extends JPanel {
    private final Runnable onStart;         // deklarasi variabel Runnable dijalankan ketika permainan dimulai
    private DefaultTableModel tableModel;   // deklarasi variabel tableModel untuk menampilkan tabel
    private JTextField usernameField;       // deklarasi variabel usernameField untuk memasukkan username
    private JTable userTable;               // deklarasi variabel userTable untuk menampilkan data usernam dalam tabel
    private CardLayout cardLayout;          // CardLayout untuk navigasi antar panel
    private JPanel cardsPanel;              // Panel untuk menyimpan kartu (halaman) yang berbeda
    private Font customFont;                // Font custom

    public MainMenu(Runnable onStart) {
        this.onStart = onStart;                     // insialsiasi bahwa game dimulai
        setPreferredSize(new Dimension(460, 382));  // set dimensi windows JPanel
        setBackground(Color.WHITE);                 // set warna background
        setLayout(new BorderLayout());              // Menggunakan BorderLayout untuk layout utama

        // Load custom font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/04B_30__.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            customFont = new Font("Helvetica", Font.BOLD, 24); // Fallback font
        }

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Panel How to Play
        JPanel htpPanel = new HTP();
        // Panel Credits
        JPanel creditsPanel = new Credits();

        // menambahkan panel ke cardsPanel
        cardsPanel.add(mainPanel, "MainMenu");
        cardsPanel.add(htpPanel, "HowToPlay");
        cardsPanel.add(creditsPanel, "Credits");

        // Membuat panel atas untuk title dan input username
        JPanel topPanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("UP DOWN");
        titleLabel.setFont(customFont);
        usernameField = new JTextField(15);                 // input field untuk input username
        JButton addButton = new JButton("Add User");        // tombol untuk add username yang telah di isi

        // setup layout untuk top panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        topPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        topPanel.add(new JLabel("Enter Username:"), gbc);

        gbc.gridx = 1;
        topPanel.add(usernameField, gbc);

        gbc.gridx = 2;
        topPanel.add(addButton, gbc);

        // tabel untuk menampilkan data dari updown.db tuser
        // deklarasi apa saja yang akan ditampilkan pada tabel
        tableModel = new DefaultTableModel(new Object[]{"Username", "Score", "Up", "Down"}, 0);
        // membuat objek user table yang berisi atribut sebelumnya
        userTable = new JTable(tableModel);
        // agar tabel dapat di scroll (apabila datanya banyak)
        JScrollPane tableScrollPane = new JScrollPane(userTable);

        // memasukan data username dsb dari tabel pada database
        loadUserData();

        // Panel bawah untuk tombol-tombol
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
        JButton startButton = new JButton("Start");    // tombol start untuk memulai game
        JButton htpButton = new JButton("Controls");     // tombol How to Play
        JButton creditsButton = new JButton("Credits");     // tombol Credits
        JButton exitButton = new JButton("Exit");           // tombol Exit

        // tombol start game
        startButton.setFont(customFont.deriveFont(10f));
        startButton.setBorderPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTable.getSelectedRow() != -1) {
                    // harus memilih username terlebih dahulu agar dapat bermain
                    onStart.run();
                } else {
                    // jika tidak maka muncul error handling
                    JOptionPane.showMessageDialog(null, "Please select a user to start the game.");
                }
            }
        });

        // tombol add username
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    // username yang di input akan dimasukan ke database, memanggil fungsi addUser di DB.java (model)
                    DB.addUser(username);
                    tableModel.addRow(new Object[]{username, 0, 0, 0});
                    usernameField.setText("");
                } else {
                    // jika username kosong, tidak bisa add dan error handling
                    JOptionPane.showMessageDialog(null, "Please enter a username.");
                }
            }
        });

        // tombol how to play
        htpButton.setFont(customFont.deriveFont(10f));
        htpButton.setBorderPainted(false);
        htpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "HowToPlay");
            }
        });

        // tombol credits
        creditsButton.setFont(customFont.deriveFont(10f));
        creditsButton.setBorderPainted(false);
        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "Credits");
            }
        });

        // tombol exit
        exitButton.setFont(customFont.deriveFont(10f));
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // keluar dari aplikasi
            }
        });

        // Menambahkan tombol ke bottom panel
        bottomPanel.add(startButton);
        bottomPanel.add(htpButton);
        bottomPanel.add(creditsButton);
        bottomPanel.add(exitButton);

        // Menambahkan panel ke mainPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(cardsPanel, BorderLayout.CENTER);
    }

    // method untuk refresh data pada tabel
    public void refreshUserData() {
        tableModel.setRowCount(0);
        loadUserData();
    }

    // method untuk menampilkan data user dari tabel pada database
    private void loadUserData() {
        ResultSet rs = DB.getUsers();
        try {
            while (rs != null && rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                int up = rs.getInt("up");
                int down = rs.getInt("down");
                tableModel.addRow(new Object[]{username, score, up, down});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method untuk mengambil username yang dipilih untuk bermain
    public String getSelectedUsername() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            return tableModel.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }
}
