
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;


public class Edit extends JFrame {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mahasiswa";
    static final String USER = "root";
    static final String PASS = "";
    Connection koneksi;
    Statement statement;
    
    JTable tabel;
    DefaultTableModel model;
    JScrollPane scrollPane;
    Object namaKolom[] = {"NIM", "Nama", "Alamat"};
    
    JLabel judul = new JLabel("EDIT DATA MAHASISWA");
    JLabel lnim = new JLabel("NIM");
    JLabel lnama = new JLabel("Nama");        
    JLabel lalamat = new JLabel("Alamat");
        
    JTextField txnim = new JTextField("");
    JTextField txnama = new JTextField("");
    JTextField txalamat = new JTextField("");
        
        
    JButton editt = new JButton("Edit");
    JButton kembali = new JButton("Kembali");
    
    
    public void EditData (){
        setTitle("Tampilan Data Mahasiswa");
        judul = new JLabel("TAMPILAN DATA MAHASISWA");
        add(judul);
        try {
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        } catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(550,600);
        
        model = new DefaultTableModel(namaKolom,0);
        tabel = new JTable(model);
        scrollPane = new JScrollPane(tabel);
        add(scrollPane);
        scrollPane.setBounds(20,270,480,250);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(judul);
        judul.setBounds(230,10,300,60);
        add(lnim);
        add(lnama);
        add(lalamat);
        add(txnim);
        add(txnama);
        add(txalamat);
        add(editt);
        add(kembali);
        
        lnim.setBounds(40, 80, 80, 20);
        lnama.setBounds(40, 110, 80, 20);
        lalamat.setBounds(40, 140, 80, 20);
        txnim.setBounds(140, 80, 220, 20);
        txnama.setBounds(140,110,80,20);
        txalamat.setBounds(140, 140, 220, 20);
        editt.setBounds(150, 220, 80, 30);
        kembali.setBounds(350, 220, 80, 30);
        
        kembali.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new main();
            }
        });
        
        editt.addActionListener((ActionEvent e) -> {
            if(txnim.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!");
            } else {
                String nim = txnim.getText();
                String nama = txnama.getText();
                String alamat = txalamat.getText();
                
                this.simpanMahasiswa(nim,nama,alamat);
                
                String dataMahasiswa[][] = this.readMahasiswa();
                tabel.setModel(new JTable(dataMahasiswa,namaKolom).getModel());
            }
        });
        
        if(this.getBanyakData() != 0){
            String dataMahasiswa[][] = this.readMahasiswa();
            tabel.setModel((new JTable(dataMahasiswa, namaKolom)).getModel());
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ada!");
        }
        
        tabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabel.getSelectedRow();
                int kolom = tabel.getSelectedColumn();
                String dataterpilih = tabel.getValueAt(baris,0).toString();
        }
        });
        
    }
        
        public void simpanMahasiswa(String nim, String nama, String alamat) {
            try {
                String query = "UPDATE data_mhs SET nama='" + txnama.getText() + "'," + "alamat='"+
                                                txalamat.getText() +"' WHERE nim='" + txnim.getText() +"'";
                statement = (Statement) koneksi.createStatement();
                statement.executeUpdate(query);
                
                JOptionPane.showMessageDialog(null, "Berhasil diedit "+nim+"");
            } catch(Exception sql){
                JOptionPane.showMessageDialog(null, sql.getMessage());
            }
        }
        
        String[][] readMahasiswa() {
            try{
                int jmlData = 0;
                String data[][] = new String[getBanyakData()][3];
                String query = "SELECT *from data_mhs";
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    data[jmlData][0] = resultSet.getString("nim");
                    data[jmlData][1] = resultSet.getString("nama");
                    data[jmlData][2] = resultSet.getString("alamat");
                    jmlData++;
                }
                return data;
            }catch(SQLException e){
                return null;
            }
        }
        
        int getBanyakData() {
            int jmlData = 0;
            try{
                statement = koneksi.createStatement();
                String query = "SELECT *from data_mhs";
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    jmlData++;
                }
                return jmlData;
            } catch(SQLException e){
                System.out.println(e.getMessage());
                System.out.println("SQL error");
                return 0;
            }
        }
        
        
        
    }
    
