// Saya [Marvel Ravindra Dioputra 2200481] mengerjakan evaluasi Tugas Masa Depan
// dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
// maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

import model.DB;    // import kelas DB folder model untuk koneksi database

import javax.swing.*;                       // import javax.swing untuk membuat GUI
import javax.swing.table.DefaultTableModel; // import DefaultTableModel untuk tabel GUI
import java.awt.*;                          // import java.awt untuk layout GUI
import java.awt.event.ActionEvent;          // import ActionEvent untuk menangani event
import java.awt.event.ActionListener;       // import ActionListener untuk menangani action
import java.sql.ResultSet;                  // import ResultSet untuk menangani hasil query database
import java.sql.SQLException;               // import SQLException untuk menangani exception SQL

// JPanel MainMenu
public class MainMenu extends JPanel {
    private final Runnable onStart;         // deklarasi variabel Runnable dijalankan ketika permainan dimulai
    private DefaultTableModel tableModel;   // deklarasi variabel tableModel untuk menampilkan tabel
    private JTextField usernameField;       // deklarasi variabel usernameField untuk memasukkan username
    private JTable userTable;               // deklarasi variabel userTable untuk menampilkan data usernam dalam tabel

    public MainMenu(Runnable onStart) {
        this.onStart = onStart;                     // insialsiasi bahwa game dimulai
        setPreferredSize(new Dimension(460, 382));  // set dimensi windows JPanel
        setBackground(Color.WHITE);                 // set warna background
        setLayout(new GridBagLayout());             // untuk layout tampilan main menu

        // membuat komponen yang ada pada main menu
        usernameField = new JTextField(15);                 // input field untuk input username
        JButton addButton = new JButton("Add User");        // tombol untuk add username yang telah di isi
        JButton startButton = new JButton("Start Game");    // tombol start untuk memulai game

        // tabel untuk menampilkan data dari updown.db tuser
        // deklarasi apa saja yang akan ditampilkan pada tabel
        tableModel = new DefaultTableModel(new Object[]{"Username", "Score", "Up", "Down"}, 0);
        // membuat objek user table yang berisi atribut sebelumnya
        userTable = new JTable(tableModel);
        // agar tabel dapat di scroll (apabila datanya banyak)
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setPreferredSize(new Dimension(400, 200));

        // memasukan data username dsb dari tabel pada database
        loadUserData();

        // tombol start game
        startButton.setFont(new Font("Helvetica", Font.PLAIN, 24));
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

        // setup layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(new JLabel("Enter Username:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 2;
        add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(tableScrollPane, gbc);

        gbc.gridy = 2;
        add(startButton, gbc);
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