package com.jgaap.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgaap.JGAAPConstants;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class JGAAP_UI_AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JGAAP_UI_AboutDialog frame = new JGAAP_UI_AboutDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JGAAP_UI_AboutDialog() {
		setResizable(false);
		setSize(new Dimension(530, 315));
		setTitle("About");
		//setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 530, 315);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(530, 315));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setSize(new Dimension(120, 100));
		ImageIcon jgaapIcon = new ImageIcon(JGAAP_UI_AboutDialog.class.getResource("/com/jgaap/resources/ui/jgaap-icon.png"));
		label.setIcon(new ImageIcon(jgaapIcon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
		label.setBounds(10, 11, 121, 100);
		contentPane.add(label);
		
		JLabel label1 = new JLabel("");
		label1.setSize(new Dimension(120, 100));
		ImageIcon evlIcon = new ImageIcon(JGAAP_UI_AboutDialog.class.getResource("/com/jgaap/resources/ui/EVL_Icon_duq.png"));
		label1.setIcon(new ImageIcon(evlIcon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
		label1.setBounds(383, 11, 121, 100);
		contentPane.add(label1);
		
		JLabel lblNewLabel = new JLabel("<html> JGAAP "+JGAAPConstants.VERSION+", the Java Graphical Authorship Attribution Program, <br/>is an opensource author attribution / text classification tool <br/>Developed by the EVL lab (Evaluating Variation in Language Labratory) <br/> Released by Patrick Juola under the AGPL v3.0");
		lblNewLabel.setBounds(55, 107, 398, 87);
		contentPane.add(lblNewLabel);
		
		InputStream fontIs = JGAAP_UI_AboutDialog.class.getResourceAsStream("/com/jgaap/resources/ui/LucidaGrande.ttf");
		Font lgFont = null;
		try {
			lgFont = Font.createFont(Font.TRUETYPE_FONT, fontIs).deriveFont(Font.BOLD, 46);
		} catch(Exception e) {}
		JLabel lblJgaap = new JLabel("JGAAP");
		lblJgaap.setFont(lgFont);
		lblJgaap.setHorizontalAlignment(SwingConstants.CENTER);
		lblJgaap.setBounds(141, 11, 225, 100);
		contentPane.add(lblJgaap);
		try {
			fontIs.close();
		} catch(Exception e) {}
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(433, 231, 71, 30);
		contentPane.add(btnClose);
		
		JLabel lblNewLabel_1 = new JLabel("<html><a href='http://evllabs.com'>http://evllabs.com</a></html>");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				browseToURL("http://evllabs.com");
			}
		});
		lblNewLabel_1.setBounds(192, 181, 130, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("<html><a href='http://evllabs.com'>http://jgaap.com</a></html>");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				browseToURL("http://jgaap.com");
			}
		});
		lblNewLabel_2.setBounds(192, 201, 130, 25);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u00A9"+JGAAPConstants.YEAR+" EVL lab");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 235, 494, 22);
		contentPane.add(lblNewLabel_3);
	}
	
	public boolean browseToURL(String url) {
		boolean succees = false;
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			succees = true;
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "JGAAP Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return succees;
	}
}
