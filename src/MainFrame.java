package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame {

    // === KOMPONEN GUI ===
    private JFrame          frame;
    private JTextField      txtNIS, txtNama, txtAlamat;
    private JTable          tabel;
    private DefaultTableModel modelTabel;
    private JLabel          lblStatus;

    public MainFrame() {
        buatTampilan();
        muatData();
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

        // Panel tengah menampung entri + tombol
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // BAGIAN 2: ENTRI DATA
        JPanel panelEntri = new JPanel(new GridBagLayout());
        panelEntri.setBorder(BorderFactory.createTitledBorder("Data Siswa"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(4, 6, 4, 6);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        // Baris 0: NIS
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelEntri.add(new JLabel("NIS :"), gbc);
        txtNIS = new JTextField();
        txtNIS.setPreferredSize(new Dimension(160, 24));
        gbc.gridx = 1; gbc.weightx = 1;
        panelEntri.add(txtNIS, gbc);

        // Baris 1: Nama Siswa
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelEntri.add(new JLabel("Nama Siswa :"), gbc);
        txtNama = new JTextField();
        txtNama.setPreferredSize(new Dimension(160, 24));
        gbc.gridx = 1; gbc.weightx = 1;
        panelEntri.add(txtNama, gbc);

        // Baris 2: Alamat
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelEntri.add(new JLabel("Alamat :"), gbc);
        txtAlamat = new JTextField();
        txtAlamat.setPreferredSize(new Dimension(160, 24));
        gbc.gridx = 1; gbc.weightx = 1;
        panelEntri.add(txtAlamat, gbc);

        panelTengah.add(panelEntri, BorderLayout.CENTER);

        // BAGIAN 3: TOMBOL CRUD
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

        // Event: klik baris tabel -> isi form otomatis
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabel.getSelectedRow();
                if (baris >= 0) {
                    txtNIS.setText(modelTabel.getValueAt(baris, 0).toString());
                    txtNama.setText(modelTabel.getValueAt(baris, 1).toString());
                    txtAlamat.setText(modelTabel.getValueAt(baris, 2).toString());
                    txtNIS.setEditable(false); // NIS tidak boleh diubah saat edit
                    setStatus("Data NIS " + txtNIS.getText() + " dipilih.",
                              new Color(41, 128, 185));
                }
            }
        });

        // Event: tombol CRUD
        btnTambah.addActionListener(e -> tambahSiswa());
        btnTampil.addActionListener(e -> muatData());
        btnUpdate.addActionListener(e -> updateSiswa());
        btnHapus.addActionListener(e  -> hapusSiswa());
        btnBersih.addActionListener(e -> bersihkanForm());

        frame.setVisible(true);
    }

    // Membuat JButton dengan teks dan warna latar
    private JButton buatTombol(String teks, Color warnaLatar) {
        JButton btn = new JButton(teks);
        btn.setBackground(warnaLatar);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }

    // Mengatur teks dan warna label status
    private void setStatus(String pesan, Color warna) {
        lblStatus.setText(" " + pesan);
        lblStatus.setForeground(warna);
    }

    private void tambahSiswa() {
        String nis    = txtNIS.getText().trim();
        String nama   = txtNama.getText().trim();
        String alamat = txtAlamat.getText().trim();

        // Validasi field kosong
        if (nis.isEmpty() || nama.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                "Semua field (NIS, Nama, Alamat) harus diisi!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            setStatus("Gagal: ada field yang kosong.", Color.RED);
            return;
        }

        try {
            // Cek duplikasi NIS menggunakan SiswaCSV (utility class)
            if (SiswaCSV.isNisDuplikat(nis)) {
                JOptionPane.showMessageDialog(frame,
                    "NIS \"" + nis + "\" sudah terdaftar!\n" +
                    "Setiap siswa harus memiliki NIS yang unik.",
                    "Error: Duplikasi NIS", JOptionPane.ERROR_MESSAGE);
                setStatus("Error: NIS " + nis + " sudah ada.", Color.RED);
                return;
            }

            // Tambah ke file CSV via utility class (mode append)
            SiswaCSV.tambahSatu(new Siswa(nis, nama, alamat));

            JOptionPane.showMessageDialog(frame,
                "Data siswa berhasil ditambahkan!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            setStatus("Sukses: data " + nama + " ditambahkan.",
                      new Color(39, 130, 70));
            bersihkanForm();
            muatData();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                "Gagal menyimpan data ke file!\nDetail: " + ex.getMessage(),
                "Error File", JOptionPane.ERROR_MESSAGE);
            setStatus("Error: gagal menulis file.", Color.RED);
        }
    }
    private void muatData() {
        modelTabel.setRowCount(0); // kosongkan tabel dulu

        try {
            ArrayList<Siswa> data = SiswaCSV.bacaSemua();

            // File belum ada: bacaSemua() kembalikan list kosong
            if (data.isEmpty()) {
                setStatus("Info: " + SiswaCSV.NAMA_FILE +
                          " belum ada atau kosong. Silakan tambahkan data.",
                          new Color(150, 80, 0));
                return;
            }

            // Isi tabel dari ArrayList<Siswa>
            for (Siswa s : data) {
                modelTabel.addRow(new String[]{s.getNis(), s.getNama(), s.getAlamat()});
            }
            setStatus("Berhasil memuat " + data.size() + " data siswa dari "
                      + SiswaCSV.NAMA_FILE + ".", new Color(39, 130, 70));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                "Gagal membaca file!\nDetail: " + ex.getMessage(),
                "Error Membaca File", JOptionPane.ERROR_MESSAGE);
            setStatus("Error: gagal membaca file.", Color.RED);
        }
    }
    // Update data siswa
        private void updateSiswa() {
        String nis    = txtNIS.getText().trim();
        String nama   = txtNama.getText().trim();
        String alamat = txtAlamat.getText().trim();

        if (nis.isEmpty() || nama.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                "Pilih data dari tabel, lalu ubah Nama atau Alamat!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            setStatus("Gagal update: pilih data dari tabel dulu.", Color.RED);
            return;
        }

        try {
            ArrayList<Siswa> data = SiswaCSV.bacaSemua();
            boolean ditemukan = false;

            for (Siswa s : data) {
                if (s.getNis().equalsIgnoreCase(nis)) {
                    s.setNama(nama);
                    s.setAlamat(alamat);
                    ditemukan = true;
                    break;
                }
            }

            if (!ditemukan) {
                JOptionPane.showMessageDialog(frame,
                    "NIS \"" + nis + "\" tidak ditemukan!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                setStatus("Error: NIS " + nis + " tidak ditemukan.", Color.RED);
                return;
            }

            // Tulis ulang file via utility class
            SiswaCSV.simpanSemua(data);

            JOptionPane.showMessageDialog(frame,
                "Data siswa berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            setStatus("Sukses: data NIS " + nis + " diperbarui.", new Color(39, 130, 70));
            bersihkanForm();
            muatData();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                "Gagal memperbarui data!\nDetail: " + ex.getMessage(),
                "Error File", JOptionPane.ERROR_MESSAGE);
            setStatus("Error: gagal menulis file.", Color.RED);
        }
    }

    // Hapus data siswa
        private void hapusSiswa() {
        String nis = txtNIS.getText().trim();

        if (nis.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                "Pilih data dari tabel terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            setStatus("Gagal hapus: pilih data dari tabel dulu.", Color.RED);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(frame,
            "Yakin ingin menghapus siswa dengan NIS: " + nis + "?\n" +
            "Data yang dihapus tidak dapat dikembalikan.",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            ArrayList<Siswa> data = SiswaCSV.bacaSemua();
            boolean dihapus = data.removeIf(s -> s.getNis().equalsIgnoreCase(nis));

            if (!dihapus) {
                JOptionPane.showMessageDialog(frame,
                    "NIS tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                setStatus("Error: NIS " + nis + " tidak ditemukan.", Color.RED);
                return;
            }

            SiswaCSV.simpanSemua(data);

            JOptionPane.showMessageDialog(frame,
                "Data siswa berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            setStatus("Sukses: data NIS " + nis + " dihapus.", new Color(39, 130, 70));
            bersihkanForm();
            muatData();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame,
                "Gagal menghapus data!\nDetail: " + ex.getMessage(),
                "Error File", JOptionPane.ERROR_MESSAGE);
            setStatus("Error: gagal menulis file.", Color.RED);
        }
    }
}
