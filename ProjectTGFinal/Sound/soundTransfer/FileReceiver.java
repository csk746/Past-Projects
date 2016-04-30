package soundTransfer;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
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
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.SwingConstants;

public class FileReceiver implements  Runnable{

	private ServerSocket serverSock;
	private Socket cltSock;
	private DataInputStream dis;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    
	 public FileReceiver(String id) {
		try {
			serverSock = new ServerSocket(8888);

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		Thread th = new Thread(this);
		th.start();
	}

	public void run(){
		try {
			cltSock = serverSock.accept();
            dis = new DataInputStream(cltSock.getInputStream());
 
            String fName = dis.readUTF();
            System.out.println("파일리시버가 받은 파일 이름"+fName);
            // 지정 폴더에 파일을 생성하고 파일에 대한 출력 스트림 생성
            File f = new File("/Users/igeonhui/Documents/workspace/ProjectTGIntergrate/" + fName);
            dis.close();
            cltSock.close();
            serverSock.close();
            System.out.println("받은 파일의 사이즈 : " + f.length());

        } catch (IOException e) {
            e.printStackTrace();
		}
	}
}

