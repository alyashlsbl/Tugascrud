
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


public class Delete extends JFrame{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mahasiswa";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;
    
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"NIM", "Nama", "Alamat"};
    
    JLabel ljudul = new JLabel("Hapus Data Mahasiswa");
    JButton btnKembali = new JButton("Kembali"); 
    JButton btnHapus = new JButton("Hapus");
    
    public void HapusData(){
        try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }
        

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setSize(500, 500);
        
        
        tableModel = new DefaultTableModel(namaKolom,0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);        

        add(scrollPane);
        scrollPane.setBounds(20, 180, 400, 250);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
             
        add(ljudul);
        ljudul.setBounds(180, 10, 300, 60);
               
        add(btnKembali);
        add(btnHapus);
            
        btnKembali.setBounds(160, 90, 80, 30);        
        btnHapus.setBounds(280, 90, 80, 30);   
        
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                setVisible (false);
                new Menu();
            }
        });
        
        if (this.getBanyakData() != 0) {  
            String dataMahasiswa[][] = this.readMahasiswa();  
            table.setModel((new JTable(dataMahasiswa, namaKolom)).getModel());
             
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada!");
        }
        
        if (this.getBanyakData() != 0) {  
            String dataMahasiswa[][] = this.readMahasiswa();  
            table.setModel((new JTable(dataMahasiswa, namaKolom)).getModel());
             
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada!");
        }
        
        table.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = table.getSelectedRow();
               int kolom = table.getSelectedColumn();  
               
               String dataterpilih = table.getValueAt(baris, 0).toString();
               btnHapus.addActionListener((ActionEvent f) -> {
                  hapusMahasiswa(dataterpilih);
                  String dataMahasiswa[][] = readMahasiswa();
                table.setModel(new JTable(dataMahasiswa,namaKolom).getModel());
                }); 
           }
        });
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

    void hapusMahasiswa(String nim) {
        try{
            String query = "DELETE FROM `data_mhs` WHERE `nim` = '"+nim+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus "+nim+"" );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }

    

    

}
