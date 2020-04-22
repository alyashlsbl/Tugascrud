import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Form extends JFrame{
    JLabel lnim,lnama,lalamat,judul;
    JTextField txnim,txnama,txalamat;
    JButton simpan,kembali;
    Statement statement;
    
    
    public void FormData (){
        
        setTitle("Input Data Mahasiswa");
        judul= new JLabel("INPUT DATA MAHASISWA");
        lnim = new JLabel("NIM");
        lnama = new JLabel("Nama");        
        lalamat = new JLabel("Alamat");
        
        txnim = new JTextField("");
        txnama = new JTextField("");
        txalamat = new JTextField("");
        
        
        simpan = new JButton("Simpan");
        kembali = new JButton("Kembali");
        
        setLayout(null);
        add(judul);
        add(lnim);
        add(lnama);
        add(lalamat);
        add(txnim);
        add(txnama);
        add(txalamat);
        add(simpan);
        add(kembali);
        
        judul.setBounds(100, 50, 300, 20);
        lnama.setBounds(100, 80, 50, 20);
        lnim.setBounds(100, 120, 50, 20);
        lalamat.setBounds(100, 160, 50, 20);
        txnama.setBounds(200, 80, 150, 20);
        txnim.setBounds(200, 120, 150, 20);
        txalamat.setBounds(200, 160, 150, 20);
        simpan.setBounds(120, 200, 80, 20);
        kembali.setBounds(220, 200, 80, 20);
        
        setSize(500,400); //untuk luas jendela
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        simpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                int a1 =  Integer.parseInt(txnim.getText());
                String a2 = txnama.getText();
                String a3 = txalamat.getText();
                
                KoneksiDB koneksi = new KoneksiDB();
                    try {
                        statement = koneksi.getKoneksi().createStatement();
                        statement.executeUpdate("INSERT INTO data_mhs VALUES ('" + a2 + "','" +
                                                a1 + "','" + a3 + "')");
                        JOptionPane.showMessageDialog(rootPane, "Data Tersimpan");
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
               // String a4 = Laki.getText();
                //String a5 = Perempuan.getText();
                System.out.println("NIM = "+a1);
                System.out.println("Nama = "+a2);
                System.out.println("Alamat = "+a3);
                    
                } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(rootPane,"TIPE DATA SALAH");
                } catch (Error ext){
                 JOptionPane.showMessageDialog(rootPane,"SALAH");
                 
                }
                
                
                
                
            }

           
        });
        
        kembali.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new main();
            }
        });
    }
}
