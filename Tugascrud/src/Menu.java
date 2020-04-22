
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class Menu extends JFrame{
    
    JButton input,cetak,hapus,edit,exit;

    public void MenuData () {
        // TODO code application logic here
        setTitle("GUI Mahasiswa");
        
        input = new JButton("Input Data Mahasiswa");
        cetak = new JButton("Tampilkan Data Mahasiswa");
        hapus = new JButton("Hapus Data Mahasiswa");
        edit = new JButton("Edit Data Mahasiswa");
        exit = new JButton("Keluar");
        
        setLayout(null);
        add(input);
        add(cetak);
        add(hapus);
        add(edit);
        add(exit);
        
        input.setBounds(100, 50, 200, 20);
        cetak.setBounds(100, 80, 200, 20);
        hapus.setBounds(100, 110, 200, 20);
        edit.setBounds(100, 140, 200, 20);
        exit.setBounds(100, 170, 200, 20);
        
        setSize(420,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        input.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Form isi = new Form();
                isi.FormData();
            }
        });
        
        cetak.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Read lihat = new Read();
                lihat.ReadData();
            }
        });
        
        edit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Edit ubah = new Edit();
                ubah.EditData();
            }
        });
        
        hapus.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete del = new Delete();
                del.HapusData();
            }
        });
        
        exit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
    }
    
}
