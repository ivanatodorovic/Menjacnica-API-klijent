package menjacnica.sistemskeoperacije;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import menjacnica.Konverzija;

public class SOUcitajKonverziju {
	public static LinkedList<Konverzija> izvrsi(String path) {
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
}
