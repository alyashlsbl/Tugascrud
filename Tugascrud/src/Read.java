
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;


public class Read extends JFrame {
    
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
    
    JLabel judul = new JLabel("TAMPILAN DATA MAHASISWA");
        
    
    JButton kembali = new JButton("Kembali");
    
    
    public void ReadData (){
        
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
        judul.setBounds(200,20,300,60);
        add(kembali);
        kembali.setBounds(250, 220, 80, 30);
        
        kembali.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new main();
            }
        });
        
        if (this.getBanyakData() != 0) {  
            String dataMahasiswa[][] = this.readMahasiswa();  
            tabel.setModel((new JTable(dataMahasiswa, namaKolom)).getModel());
             
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada!");
        }
        
      
    }
    
    String[][] readMahasiswa() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][3];
            String query = "Select * from `data_mhs`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("nim");
                data[jmlData][1] = resultSet.getString("nama");
                data[jmlData][2] = resultSet.getString("alamat");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

    int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `data_mhs`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }
    
    }
    