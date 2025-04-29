package menu;

import java.util.*;

import applicazione.CampoCaratteristico;
import applicazione.Categoria;
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
			if(g.getNomeComprensorio().equals(fruit.getNomeComprensorio())) {
				gerarch.add(g);
			}
		}
		Gerarchia gScelta;
		if(gerarch.isEmpty()) {
			System.out.println("Non ci sono gerarchie presenti per il tuo comprensorio.");
			return;
		} else {
			gScelta = selezionaGerarchia(gerarch);
            navigaCategoria(gScelta.getCatRadice(), new HashMap<>()); // Inizializziamo la mappa dei valori di campo per la navigazione
		}
		
		//DA TERMINARE METODO RICORSIVO PER ANDARE AVANTI E INDIETRO
	}

	private Gerarchia selezionaGerarchia(ArrayList<Gerarchia> gerarch) {
		for(int i = 0; i < gerarch.size(); i++) {
			System.out.println(i + ": " + gerarch.get(i).getNomeComprensorio());
		}
		
		int scelta = InputDati.leggiIntero("Seleziona una gerarchia > ", 0, gerarch.size() - 1);
		return gerarch.get(scelta);
	}
	

	private void navigaCategoria(Categoria categoriaCorrente, Map<String, String> valoriImpostati) {
        System.out.println("\nCategoria corrente: " + categoriaCorrente.getNome());
        if (!valoriImpostati.isEmpty()) {
            System.out.println("Valori impostati finora: " + valoriImpostati);
        }

        if (categoriaCorrente.isFoglia()) {
            System.out.println("Questa è una categoria foglia. Ritorno al menu principale.");
            return;
        }

        List<Categoria> sottocategorie = categoriaCorrente.getSottoCateg();
        if (sottocategorie.isEmpty()) {
            System.out.println("Non ci sono sottocategorie. Ritorno al menu principale.");
            return;
        }

        Categoria prossimaCategoria = null;
        if (categoriaCorrente.getCampCaratt() != null) {
            CampoCaratteristico campoCaratt = categoriaCorrente.getCampCaratt();
            System.out.println("Campo caratteristico: " + campoCaratt.getNomeCampo());
            Map<String, String> valoriDisponibili = campoCaratt.getValori();
            if (!valoriDisponibili.isEmpty()) {
                System.out.println("Valori disponibili: " + valoriDisponibili.keySet());
                String valoreScelto = InputDati.leggiStringa("Seleziona un valore per '" + campoCaratt.getNomeCampo() + "' (o lascia vuoto per non specificare): ");
                if (!valoreScelto.isEmpty() && valoriDisponibili.containsKey(valoreScelto)) {
                    valoriImpostati.put(campoCaratt.getNomeCampo(), valoreScelto);
                    // Trova la sottocategoria che corrisponde al valore scelto (se la logica lo prevede)
                    // In questo esempio, assumiamo che la scelta del valore guidi verso una specifica sottocategoria.
                    // Potrebbe essere necessario adattare questa parte in base alla tua logica di gerarchia.
                    boolean trovata = false;
                    for (Categoria subCat : sottocategorie) {
                        if (subCat.getNome().equalsIgnoreCase(valoreScelto)) { // Esempio semplificato: nome sottocategoria = valore
                            prossimaCategoria = subCat;
                            trovata = true;
                            break;
                        }
                    }
                    if (!trovata && !sottocategorie.isEmpty()) {
                        System.out.println("Valore non trovato come sottocategoria diretta. Si mostrano le sottocategorie disponibili:");
                        prossimaCategoria = selezionaSottoCategoria(sottocategorie);
                    } else if (trovata) {
                        System.out.println("Selezionata sottocategoria basata sul valore: " + prossimaCategoria.getNome());
                    } else if (sottocategorie.isEmpty()) {
                        System.out.println("Nessuna sottocategoria disponibile dopo la selezione del valore.");
                        return;
                    }
                } else if (!sottocategorie.isEmpty()) {
                    prossimaCategoria = selezionaSottoCategoria(sottocategorie);
                } else {
                    System.out.println("Nessuna sottocategoria disponibile.");
                    return;
                }
            } else if (!sottocategorie.isEmpty()) {
                prossimaCategoria = selezionaSottoCategoria(sottocategorie);
            } else {
                System.out.println("Nessuna sottocategoria disponibile.");
                return;
            }
        } else if (!sottocategorie.isEmpty()) {
            prossimaCategoria = selezionaSottoCategoria(sottocategorie);
        } else {
            System.out.println("Nessuna sottocategoria disponibile.");
            return;
        }

        if (prossimaCategoria != null) {
            navigaCategoria(prossimaCategoria, new HashMap<>(valoriImpostati)); // Passa una copia dei valori impostati
        }
    }

    private Categoria selezionaSottoCategoria(List<Categoria> sottocategorie) {
        System.out.println("Sottocategorie disponibili:");
        for (int i = 0; i < sottocategorie.size(); i++) {
            System.out.println((i + 1) + ". " + sottocategorie.get(i).getNome());
        }

        int scelta = InputDati.leggiIntero("Seleziona una sottocategoria: ", 1, sottocategorie.size());
        return sottocategorie.get(scelta - 1);
    }

}
