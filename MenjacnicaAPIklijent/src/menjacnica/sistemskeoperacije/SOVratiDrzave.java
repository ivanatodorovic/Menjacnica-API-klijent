package menjacnica.sistemskeoperacije;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Drzava;
import menjacnica.util.URLConnectionUtil;

public class SOVratiDrzave {

	public static String[] izvrsi(LinkedList<Drzava> drzave) {
		try {
			String sadrzaj = URLConnectionUtil.getContent("http://free.currencyconverterapi.com/api/v3/countries");

			Gson gson = new GsonBuilder().create();
			JsonParser jsParser = new JsonParser();
			JsonObject jsObj = jsParser.parse(sadrzaj).getAsJsonObject().getAsJsonObject("results");

			for (Map.Entry<String, JsonElement> entry : jsObj.entrySet()) {
				Drzava drzava = gson.fromJson(entry.getValue(), Drzava.class);
				drzave.add(drzava);
			}

			String[] naziviDrzava = new String[drzave.size()];
			for (int i = 0; i < drzave.size(); i++)
				naziviDrzava[i] = drzave.get(i).getName();

			return naziviDrzava;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
	
	
	
	
	/*
	 * 	private String[] vratiNazive(LinkedList<Drzava> drzave) {
		String[] niz = new String[drzave.size()];
		int br = 0;
		for (int i = 0; i < drzave.size(); i++) {
			niz[br] = drzave.get(i).getName();
			br++;
		}
		return niz;
	}
	 * */
}
