package menjacnica.gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Drzava;
import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.sistemskeoperacije.SOVratiDrzave;
import menjacnica.util.URLConnectionUtil;

public class GUIKontroler {
	
	public static MenjacnicaGUI glavniProzor ;
	public static Menjacnica sistem;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new Menjacnica();
					GUIKontroler.glavniProzor = new MenjacnicaGUI();
					GUIKontroler.glavniProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public static String[] vratiDrzave(LinkedList<Drzava> drzave) {
		 return sistem.preuzmiDrzave(drzave);
		}
	
	
	
	
	public static void konvertovanje() {
		String zahtev = GUIKontroler.vratiCurrencyId(glavniProzor.getIzValuteComboBox().getSelectedItem().toString()) + "_"
				+ GUIKontroler.vratiCurrencyId(glavniProzor.getUValutuComboBox().getSelectedItem().toString());
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
				GUIKontroler.konvertujValutu(valuta.getVal(), glavniProzor.getIzValuteTextField(), glavniProzor.getUValutuTextField());
				sistem.upamtiKonverziju(zahtev, valuta.getVal(), "data/log.json");
			} else {
				JOptionPane.showMessageDialog(GUIKontroler.glavniProzor.getContentPane(), "Nije pronadjen zahtev: " + zahtev, "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void konvertujValutu(double val, JTextField jTextField, JTextField jTextField2) {
		try {
			double iznosKojiKovertujemo = Double.parseDouble(jTextField.getText());
			jTextField2.setText(iznosKojiKovertujemo * val + "");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(GUIKontroler.glavniProzor.getContentPane(), "Treba uneti broj! ", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	
	public static String vratiCurrencyId(String ime) {
		for (int i = 0; i < glavniProzor.drzave.size(); i++) {
			if (glavniProzor.drzave.get(i).getName().equals(ime))
				return glavniProzor.drzave.get(i).getCurrencyId();

		}
		return null;
	}

	
	
}
