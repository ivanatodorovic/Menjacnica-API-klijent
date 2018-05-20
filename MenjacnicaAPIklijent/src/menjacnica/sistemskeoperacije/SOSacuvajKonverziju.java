package menjacnica.sistemskeoperacije;

import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import menjacnica.Konverzija;

public class SOSacuvajKonverziju {

	
	
	public static void izvrsi(String zahtev, double kurs, String path, LinkedList<Konverzija> lista) {
	
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
}
