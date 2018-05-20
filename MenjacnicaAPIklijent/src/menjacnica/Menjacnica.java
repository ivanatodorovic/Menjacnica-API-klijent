package menjacnica;

import java.util.LinkedList;

import menjacnica.sistemskeoperacije.SOKonvertuj;
import menjacnica.sistemskeoperacije.SOSacuvajKonverziju;
import menjacnica.sistemskeoperacije.SOUcitajKonverziju;
import menjacnica.sistemskeoperacije.SOVratiDrzave;

public class Menjacnica {

	public String[] preuzmiDrzave(LinkedList<Drzava> drzave) {
		return SOVratiDrzave.izvrsi(drzave);
	}

	public Valuta preuzmiValutu(String url, String zahtevUrl) {
		return SOKonvertuj.izvrsi(url, zahtevUrl);
	}

	public void upamtiKonverziju(String zahtevUrl, double valuta, String filepath) {
		SOSacuvajKonverziju.izvrsi(zahtevUrl, valuta, filepath, SOUcitajKonverziju.izvrsi(filepath));
	}

}
