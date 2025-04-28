package menu;

import java.util.ArrayList;

import applicazione.Gerarchia;
import persistenza.LogicaPersistenza;
import utenti.Fruitore;
import util.InputDati;
import util.Menu;

public class MenuFruitore extends Menu{
	
	private Fruitore fruit;
	private LogicaPersistenza logica;
	
	private static final String titolo = "\tMENU FRUITORE";
	
	private static final String NAVIGA = "Naviga tra le gerarchie";
	
	private static String[] vociFruit = {NAVIGA};
	
	public MenuFruitore(Fruitore fruit, LogicaPersistenza logica) {
		super(titolo, vociFruit);
		this.fruit = fruit;
		this.logica = logica;
	}

	public void naviga() {
		System.out.println("Gerarchie presenti nel tuo comprensorio:");
		ArrayList<Gerarchia> gerarch = new ArrayList<Gerarchia>();
		for(Gerarchia g: logica.getGerarchie()) {
			if(g.getComprensorio().equals(fruit.getComprensorio())) {
				gerarch.add(g);
			}
		}
		Gerarchia gScelta;
		if(gerarch.isEmpty()) {
			System.out.println("Non ci sono gerarchia presenti per il tuo comprensorio.");
			return;
		} else {
			gScelta = selezionaGerarchia(gerarch);
		}
		
		//DA TERMINARE METODO RICORSIVO PER ANDARE AVANRI E INDIETRO
	}

	private Gerarchia selezionaGerarchia(ArrayList<Gerarchia> gerarch) {
		for(int i = 0; i < gerarch.size(); i++) {
			System.out.println(i + ": " + gerarch.get(i));
		}
		
		int scelta = InputDati.leggiIntero("Seleziona una gerarchia > ", 0, gerarch.size());
		return gerarch.get(scelta);
	}

}
