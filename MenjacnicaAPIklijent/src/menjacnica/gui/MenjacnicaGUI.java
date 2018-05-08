package menjacnica.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Drzava;
import menjacnica.util.URLConnectionUtil;

import java.awt.Frame;
import java.io.IOException;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class MenjacnicaGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIzValuteZemlje;
	private JTextField textFieldUValutuZemlje;
	
	private LinkedList<Drzava> drzave = new LinkedList<Drzava>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenjacnicaGUI frame = new MenjacnicaGUI();
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
	public MenjacnicaGUI() {
		setFont(null);
		setResizable(false);
		setTitle("Menjacnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] naziviDrzava = drzave();
		
		JLabel lblIzValuteZemlje = new JLabel("Iz valute zemlje:");
		lblIzValuteZemlje.setBounds(53, 46, 87, 14);
		contentPane.add(lblIzValuteZemlje);
		
		JLabel lblUValutuZemlje = new JLabel("U valutu zemlje: ");
		lblUValutuZemlje.setBounds(239, 46, 87, 14);
		contentPane.add(lblUValutuZemlje);
		
		JLabel lblIznos = new JLabel("Iznos:");
		lblIznos.setBounds(53, 116, 55, 14);
		contentPane.add(lblIznos);
		
		JLabel lblIznos_1 = new JLabel("Iznos:");
		lblIznos_1.setBounds(239, 116, 46, 14);
		contentPane.add(lblIznos_1);
		
		JComboBox comboBoxIzValuteZemlje = new JComboBox();
		comboBoxIzValuteZemlje.setModel(new DefaultComboBoxModel(naziviDrzava));
		comboBoxIzValuteZemlje.setBounds(53, 71, 102, 20);
		contentPane.add(comboBoxIzValuteZemlje);
		
		JComboBox comboBoxUValutuZemlje = new JComboBox();
		comboBoxUValutuZemlje.setModel(new DefaultComboBoxModel(naziviDrzava));
		comboBoxUValutuZemlje.setBounds(239, 71, 102, 20);
		contentPane.add(comboBoxUValutuZemlje);
		
		textFieldIzValuteZemlje = new JTextField();
		textFieldIzValuteZemlje.setBounds(53, 154, 86, 20);
		contentPane.add(textFieldIzValuteZemlje);
		textFieldIzValuteZemlje.setColumns(10);
		
		textFieldUValutuZemlje = new JTextField();
		textFieldUValutuZemlje.setBounds(239, 154, 86, 20);
		contentPane.add(textFieldUValutuZemlje);
		textFieldUValutuZemlje.setColumns(10);
		
		JButton btnKonvertuj = new JButton("Konvertuj");
		btnKonvertuj.setBounds(163, 216, 89, 23);
		contentPane.add(btnKonvertuj);
	}

	private String[] drzave() {
	try {
		String sadrzaj = URLConnectionUtil.getContent("http://free.currencyconverterapi.com/api/v3/countries");
		
		Gson gson = new GsonBuilder().create();
		JsonParser jsParser = new JsonParser();
		JsonObject jsObj = jsParser.parse(sadrzaj).getAsJsonObject().getAsJsonObject("results");
		
		for(Map.Entry<String, JsonElement> entry: jsObj.entrySet()) {
			Drzava drzava = gson.fromJson(entry.getValue(), Drzava.class);
			drzave.add(drzava);
		}
		String[] naziviDrzava = vratiNazive(drzave);
		
		return naziviDrzava;
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
		return null;
	}

	
	private String[] vratiNazive(LinkedList<Drzava> drzave) {
	String[] niz = new String[drzave.size()];
	int br = 0; 
		for (int i = 0; i < drzave.size(); i++) {
			niz[br] = drzave.get(i).getName();
			br++;
		}
		return niz;
	}
}
