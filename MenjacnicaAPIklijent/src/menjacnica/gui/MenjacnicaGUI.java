package menjacnica.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Drzava;
import menjacnica.Konverzija;
import menjacnica.Valuta;
import menjacnica.sistemskeoperacije.SOSacuvajKonverziju;
import menjacnica.sistemskeoperacije.SOUcitajKonverziju;
import menjacnica.sistemskeoperacije.SOVratiDrzave;
import menjacnica.util.URLConnectionUtil;

import java.awt.Frame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenjacnicaGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblIzValuteZemlje;
	public JComboBox IzValuteComboBox;
	private JLabel lblUValutuZemlje;
	public JComboBox UValutuComboBox;
	private JLabel lblIznos;
	private JLabel label;
	private JTextField IzValuteTextField;
	private JTextField UValutuTextField;
	private JButton btnKonvertuj;

	public LinkedList<Drzava> drzave = new LinkedList<Drzava>(); 
	public String[] nizZemalja;

	public MenjacnicaGUI() {

		nizZemalja = GUIKontroler.vratiDrzave(drzave);
		setResizable(false);
		setTitle("Menjacnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 426, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblIzValuteZemlje());
		contentPane.add(getIzValuteComboBox());
		contentPane.add(getLblUValutuZemlje());
		contentPane.add(getUValutuComboBox());
		contentPane.add(getLblIznos());
		contentPane.add(getLabel());
		contentPane.add(getIzValuteTextField());
		contentPane.add(getUValutuTextField());
		contentPane.add(getBtnKonvertuj());
		setLocationRelativeTo(null);

	}

	private JLabel getLblIzValuteZemlje() {
		if (lblIzValuteZemlje == null) {
			lblIzValuteZemlje = new JLabel("Iz valute zemlje:");
			lblIzValuteZemlje.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			lblIzValuteZemlje.setBounds(59, 32, 162, 36);
		}
		return lblIzValuteZemlje;
	}

	public JComboBox getIzValuteComboBox() {
		if (IzValuteComboBox == null) {

			IzValuteComboBox = new JComboBox();
			IzValuteComboBox.setBounds(59, 79, 122, 20);
			IzValuteComboBox.setModel(new DefaultComboBoxModel(nizZemalja));
		}
		return IzValuteComboBox;
	}

	public JLabel getLblUValutuZemlje() {
		if (lblUValutuZemlje == null) {
			lblUValutuZemlje = new JLabel("U valutu zemlje:");
			lblUValutuZemlje.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			lblUValutuZemlje.setBounds(231, 37, 162, 26);

		}
		return lblUValutuZemlje;
	}

	public JComboBox getUValutuComboBox() {
		if (UValutuComboBox == null) {
			UValutuComboBox = new JComboBox();
			UValutuComboBox.setBounds(231, 79, 122, 20);
			UValutuComboBox.setModel(new DefaultComboBoxModel(nizZemalja));
		}
		return UValutuComboBox;
	}

	private JLabel getLblIznos() {
		if (lblIznos == null) {
			lblIznos = new JLabel("Iznos:");
			lblIznos.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			lblIznos.setBounds(231, 110, 122, 26);
		}
		return lblIznos;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Iznos:");
			label.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			label.setBounds(59, 110, 122, 26);
		}
		return label;
	}

	public JTextField getIzValuteTextField() {
		if (IzValuteTextField == null) {
			IzValuteTextField = new JTextField();
			IzValuteTextField.setBounds(59, 147, 122, 20);
			IzValuteTextField.setColumns(10);
		}
		return IzValuteTextField;
	}

	public JTextField getUValutuTextField() {
		if (UValutuTextField == null) {
			UValutuTextField = new JTextField();
			UValutuTextField.setBounds(231, 147, 122, 20);
			UValutuTextField.setColumns(10);
		}
		return UValutuTextField;
	}

	private JButton getBtnKonvertuj() {
		if (btnKonvertuj == null) {
			btnKonvertuj = new JButton("Konvertuj");
			btnKonvertuj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					GUIKontroler.konvertovanje();

				}

			});
			btnKonvertuj.setBounds(147, 205, 122, 23);
		}
		return btnKonvertuj;
	}

}
