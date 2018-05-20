package menjacnica.sistemskeoperacije;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Valuta;
import menjacnica.util.URLConnectionUtil;

public class SOKonvertuj {

	
public static Valuta izvrsi(String url, String zahtevUrl) {
		
		url = url + zahtevUrl;
		try {
			String content = URLConnectionUtil.getContent(url);
			JsonParser jsonPraser = new JsonParser();
			JsonObject jsonObj = jsonPraser.parse(content).getAsJsonObject().getAsJsonObject("results")
					.getAsJsonObject(zahtevUrl);
			Gson gson = new GsonBuilder().create();
			Valuta valuta = gson.fromJson(jsonObj, Valuta.class);
			return valuta;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
