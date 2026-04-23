package src;

import java.io.*;
import java.util.ArrayList;

public class SiswaCSV {

    static final String NAMA_FILE = "data/siswa.csv";

    /** Buat folder data/ otomatis jika belum ada. */
    private static void pastikanFolderAda() {
        new File("data").mkdirs();
    }

    public static ArrayList<Siswa> bacaSemua() throws IOException {
        ArrayList<Siswa> data = new ArrayList<>();
        File fileCsv = new File(NAMA_FILE);

        if (!fileCsv.exists()) return data;

        try (BufferedReader br = new BufferedReader(new FileReader(fileCsv))) {
            String baris;
            while ((baris = br.readLine()) != null) {
                baris = baris.trim();
                if (!baris.isEmpty()) {
                    String[] bagian = baris.split(",", 3);
                    if (bagian.length == 3) {
                        data.add(new Siswa(bagian[0].trim(), bagian[1].trim(), bagian[2].trim()));
                    }
                }
            }
        }
        return data;
    }

    public static void simpanSemua(ArrayList<Siswa> dataSiswa) throws IOException {
        pastikanFolderAda();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (Siswa s : dataSiswa) {
                // toString() sudah menghasilkan format CSV: NIS,Nama,Alamat
                bw.write(s.toString());
                bw.newLine();
            }
        }
    }
    public static void tambahSatu(Siswa siswa) throws IOException {
        pastikanFolderAda();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NAMA_FILE, true))) {
            bw.write(siswa.toString());
            bw.newLine();
        }
    }


    public static boolean isNisDuplikat(String nis) throws IOException {
        for (Siswa s : bacaSemua()) {
            if (s.getNis().equalsIgnoreCase(nis)) return true;
        }
        return false;
    }
}