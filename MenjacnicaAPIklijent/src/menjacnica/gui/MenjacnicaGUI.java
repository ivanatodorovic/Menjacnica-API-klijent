package menjacnica.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
		btnKonvertuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String zahtev = vratiCurrencyId(comboBoxIzValuteZemlje.getSelectedItem().toString()) + "_"
						+ vratiCurrencyId(comboBoxUValutuZemlje.getSelectedItem().toString());
				String url = "http://free.currencyconverterapi.com/api/v3/convert?q=";
				url += zahtev;

				try {
					String sadrzaj = URLConnectionUtil.getContent(url);
					JsonParser jsPraser = new JsonParser();
					JsonObject jsObj = jsPraser.parse(sadrzaj).getAsJsonObject().getAsJsonObject("results")
							.getAsJsonObject(zahtev);
					Gson gson = new GsonBuilder().create();
					Valuta valuta = gson.fromJson(jsObj, Valuta.class);
					if (valuta != null) {
						konvertuj(valuta.getVal());
						sacuvajKonverziju(zahtev, valuta.getVal(), "data/log.json");
					} else {
						JOptionPane.showMessageDialog(contentPane, "Nije pronadjen zahtev: " + zahtev, "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});
		btnKonvertuj.setBounds(163, 216, 89, 23);
		contentPane.add(btnKonvertuj);
	}

	private LinkedList<Konverzija> ucitajKonverziju(String path) {
		LinkedList<Konverzija> lista = new LinkedList<Konverzija>();

		try {
			FileReader reader = new FileReader(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonArray array = gson.fromJson(reader, JsonArray.class);
			if (array == null)
				return null;
			for (int i = 0; i < array.size(); i++) {
				lista.add(gson.fromJson(array.get(i), Konverzija.class));
			}

		} catch (FileNotFoundException e) {

			System.out.println("Kreiran fajl");
		}

		return lista;
	}

	private void sacuvajKonverziju(String zahtev, double kurs, String path) {
		LinkedList<Konverzija> lista = ucitajKonverziju(path);
		Konverzija konverzija = new Konverzija();
		konverzija.setIzValuta(zahtev.split("_")[0]);
		konverzija.setuValuta(zahtev.split("_")[1]);
		konverzija.setKurs(kurs);
		konverzija.setDatumVreme(new GregorianCalendar().getTime().toString());
		if (lista == null)
			lista = new LinkedList<Konverzija>();
		lista.add(konverzija);

		try {
			FileWriter writer = new FileWriter(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(lista, writer);
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private String vratiCurrencyId(String ime) {
		for (int i = 0; i < drzave.size(); i++) {
			if (drzave.get(i).getName().equals(ime))
				return drzave.get(i).getCurrencyId();

		}
		return null;
	}

	private void konvertuj(double val) {
		try {
			double iznosKojiKovertujemo = Double.parseDouble(textFieldIzValuteZemlje.getText());
			textFieldUValutuZemlje.setText(iznosKojiKovertujemo * val + "");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(contentPane, "Treba uneti broj! ", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	private String[] drzave() {
		try {
			String sadrzaj = URLConnectionUtil.getContent("http://free.currencyconverterapi.com/api/v3/countries");

			Gson gson = new GsonBuilder().create();
			JsonParser jsParser = new JsonParser();
			JsonObject jsObj = jsParser.parse(sadrzaj).getAsJsonObject().getAsJsonObject("results");

			for (Map.Entry<String, JsonElement> entry : jsObj.entrySet()) {
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
