package soundTransfer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class FileSender extends JDialog implements Runnable {
	File f;
	Socket cltSock;
	DataOutputStream dos;
    FileInputStream fis;
    BufferedInputStream bis;
	
	public FileSender(String id) {
		 try {
				cltSock = new Socket("127.0.0.1" ,8888);
				dos = new DataOutputStream(cltSock.getOutputStream());
            // 파일 이름 전송
            String fName = id+".wav";
            System.out.println(fName);
            dos.writeUTF(fName);
            String name = "/Users/igeonhui/Documents/workspace/ProjectTGIntergrate/"+id+".wav";
            fis = new FileInputStream(name);
            
            Thread th = new Thread(this);
			th.start();
            // 파일 내용을 읽으면서 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	@Override
	public void run() {
        try {
			dos.flush();
	        dos.close();
	        fis.close();
	        this.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
}