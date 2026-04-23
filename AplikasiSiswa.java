import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class AplikasiSiswa {
    
    // === KOMPONEN GUI ===
    private JFrame frame;
    private JTextField txtNIS, txtNama, txtAlamat;
    private JTable tabel;
    private DefaultTableModel modelTabel;
    private JLabel lblStatus;
 
    // === KONSTANTA ===
    private static final String NAMA_FILE = "siswa.csv";
 
    public AplikasiSiswa() {
        buatTampilan();
        muatDataDariFile();
    }

    private void buatTampilan() {
        // === FRAME UTAMA ===
        frame = new JFrame("Aplikasi Data Siswa - Perpustakaan SMP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 620);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(5, 5));
 
        // BAGIAN 1: JUDUL PROGRAM (NORTH)
        JPanel panelJudul = new JPanel();
        panelJudul.setBackground(new Color(33, 97, 140));
        panelJudul.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblJudul = new JLabel("APLIKASI DATA SISWA PERPUSTAKAAN SMP");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        lblJudul.setForeground(Color.WHITE);
        panelJudul.add(lblJudul);
        frame.add(panelJudul, BorderLayout.NORTH);
 
        // PANEL TENGAH: ENTRI DATA + TOMBOL
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
 
        // BAGIAN 2: ENTRI DATA
        // Menggunakan GridBagLayout agar kolom label dan field bisa diatur lebar masing-masing secara proporsional
        JPanel panelEntri = new JPanel(new GridBagLayout());
        panelEntri.setBorder(BorderFactory.createTitledBorder("Data Siswa"));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(4, 6, 4, 6);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
 
        // Baris 0 – NIS
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelEntri.add(new JLabel("NIS :"), gbc);
 
        txtNIS = new JTextField();
        txtNIS.setPreferredSize(new Dimension(160, 24));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        panelEntri.add(txtNIS, gbc);
 
        // Baris 1 – Nama Siswa
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelEntri.add(new JLabel("Nama Siswa :"), gbc);
 
        txtNama = new JTextField();
        txtNama.setPreferredSize(new Dimension(160, 24));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        panelEntri.add(txtNama, gbc);
 
        // Baris 2 – Alamat
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelEntri.add(new JLabel("Alamat :"), gbc);
 
        txtAlamat = new JTextField();
        txtAlamat.setPreferredSize(new Dimension(160, 24));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        panelEntri.add(txtAlamat, gbc);
 
        panelTengah.add(panelEntri, BorderLayout.CENTER);
 
        // BAGIAN 3: TOMBOL CRUD (FlowLayout)
        JPanel panelTombolWrapper = new JPanel(new BorderLayout());
 
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        panelTombol.setBorder(BorderFactory.createTitledBorder("Operasi CRUD"));
 
        JButton btnTambah = buatTombol("\u2795 Tambah",  new Color(39, 174, 96));
        JButton btnTampil = buatTombol("\u21BA Tampil",  new Color(41, 128, 185));
        JButton btnUpdate = buatTombol("\u270E Update",  new Color(243, 156, 18));
        JButton btnHapus  = buatTombol("\u2716 Hapus",   new Color(192, 57, 43));
        JButton btnBersih = buatTombol("\u2715 Bersih",  new Color(127, 140, 141));
 
        panelTombol.add(btnTambah);
        panelTombol.add(btnTampil);
        panelTombol.add(btnUpdate);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersih);
 
        // Label status kecil di bawah panel tombol
        lblStatus = new JLabel(" Siap.", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        lblStatus.setForeground(new Color(80, 80, 80));
 
        panelTombolWrapper.add(panelTombol, BorderLayout.CENTER);
        panelTombolWrapper.add(lblStatus,   BorderLayout.SOUTH);
 
        panelTengah.add(panelTombolWrapper, BorderLayout.SOUTH);
        frame.add(panelTengah, BorderLayout.CENTER);
 
        // BAGIAN 4: TABEL DATA (SOUTH)
        String[] kolom = {"NIS", "Nama Siswa", "Alamat"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabel = new JTable(modelTabel);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.getTableHeader().setBackground(new Color(33, 97, 140));
        tabel.getTableHeader().setForeground(Color.WHITE);
        tabel.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabel.setRowHeight(24);
        tabel.setFont(new Font("Arial", Font.PLAIN, 12));
 
        JScrollPane scrollPane = new JScrollPane(tabel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Siswa"));
        scrollPane.setPreferredSize(new Dimension(700, 260));
        frame.add(scrollPane, BorderLayout.SOUTH);
 
        // EVENT: Klik baris tabel -> isi form secara otomatis
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabel.getSelectedRow();
                if (baris >= 0) {
                    txtNIS.setText(modelTabel.getValueAt(baris, 0).toString());
                    txtNama.setText(modelTabel.getValueAt(baris, 1).toString());
                    txtAlamat.setText(modelTabel.getValueAt(baris, 2).toString());
                    txtNIS.setEditable(false);
                    setStatus("Data NIS " + txtNIS.getText() + " dipilih.", new Color(41, 128, 185));
                }
            }
        });
 
        // EVENT: Tombol CRUD
        btnTambah.addActionListener(e -> tambahSiswa());
        btnTampil.addActionListener(e -> muatDataDariFile());
        btnUpdate.addActionListener(e -> updateSiswa());
        btnHapus.addActionListener(e  -> hapusSiswa());
        btnBersih.addActionListener(e -> bersihkanForm());
 
        frame.setVisible(true);
    }
 
    // Buat JButton dengan teks dan warna latar seragam
    private JButton buatTombol(String teks, Color warnaLatar) {
        JButton btn = new JButton(teks);
        btn.setBackground(warnaLatar);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }
 
    // Atur teks dan warna label status
    private void setStatus(String pesan, Color warna) {
        lblStatus.setText(" " + pesan);
        lblStatus.setForeground(warna);
    }

    private void tambahSiswa() {
        String nis    = txtNIS.getText().trim();
        String nama   = txtNama.getText().trim();
        String alamat = txtAlamat.getText().trim();

        // semua field harus terisi
        if (nis.isEmpty() || nama.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                "Semua field (NIS, Nama, Alamat) harus diisi!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            setStatus("Gagal: ada field yang kosong.", Color.RED);
            return;
        }

        // NIS tidak boleh duplikat
        ArrayList<String[]> dataSiswa = bacaSemuaData();
        for (String[] s : dataSiswa) {
            if (s[0].equalsIgnoreCase(nis)) {
                JOptionPane.showMessageDialog(frame,
                    "NIS \"" + nis + "\" sudah terdaftar!\n" +
                    "Setiap siswa harus memiliki NIS yang unik.",
                    "Error: Duplikasi NIS", JOptionPane.ERROR_MESSAGE);
                setStatus("Error: NIS " + nis + " sudah ada.", Color.RED);
                return;
            }
        }

        // Simpan ke file CSV (file dibuat otomatis jika belum ada)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NAMA_FILE, true))) {
            bw.write(nis + "," + nama + "," + alamat);
            bw.newLine();
            JOptionPane.showMessageDialog(frame,
                "Data siswa berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            setStatus("Sukses: data " + nama + " ditambahkan.", new Color(39, 130, 70));
            bersihkanForm();
            muatDataDariFile();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                "Gagal menyimpan data ke file!\nDetail: " + ex.getMessage(),
                "Error File", JOptionPane.ERROR_MESSAGE);
            setStatus("Error: gagal menulis file.", Color.RED);
        }
    }
}


    