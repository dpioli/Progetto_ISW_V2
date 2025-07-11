package persistenza;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import applicazione.CategoriaFoglia;
import applicazione.Comprensorio;
import applicazione.FatConversione;
import applicazione.Gerarchia;
import utenti.Configuratore;
import utenti.Fruitore;


/**
 * Classe per la gestione della persistenza dei dati
 * 
 * @author Erjona Maxhaku 735766
 */
public class GestorePersistenza {
	
	private static final String FILE_CONFIGURATORI = "../Progetto_ISW_V2/src/dati/configuratori.json";
	private static final String FILE_GERARCHIE = "../Progetto_ISW_V2/src/dati/gerarchie.json";
	private static final String FILE_COMPRENSORI = "../Progetto_ISW_V2/src/dati/comprensori.json";
	private static final String FILE_FATT_CONVERSIONE = "../Progetto_ISW_V2/src/dati/fattConversione.json";
	private static final String FILE_CATEGORIEFOGLIA = "../Progetto_ISW_V2/src/dati/categorieFoglia.json";
	private static final String FILE_FRUITORI = "../Progetto_ISW_V2/src/dati/fruitori.json";	
	
	private static final String MSG_ERRORE_SALVATAGGIO = "Errore durante il salvataggio: ";
	private static final String MSG_FILE_NON_TROVATO = "File non trovato: ";
	private static final String MSG_ERRORE_CARICAMENTO_FILE = "Errore durante il caricamento: ";
	
	private static Gson gson;
	
	/**
	 * Costruttore della classe GestorePersistenza
	 */
	public GestorePersistenza() {
		GestorePersistenza.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	/**
	 * Metdo generico per salvare i dati su un file json
	 * @param <T>
	 * @param oggetto
	 * @param fpath
	 */
	private static <T> void salva(T oggetto, String fpath) {
		try(FileWriter wr = new FileWriter(fpath)){
			gson.toJson(oggetto, wr);
			wr.close();
		} catch (IOException e) {
			System.err.println(MSG_ERRORE_SALVATAGGIO + e.getMessage());
		}
	}
	
	/**
	 * Metodo generico per cericare i dati salvati su un file json
	 * @param <T>
	 * @param obj
	 * @param fpath
	 * @return oggetto generico salvato sul file
	 */
	private static <T> T carica(Type typeOfT, String fpath) {
	    T oggetto = null;
	    File file = new File(fpath);
	    if (!file.exists()) {
	    	System.err.println(MSG_FILE_NON_TROVATO + fpath);
	    	return null;
	    }
	    try (FileReader rd = new FileReader(fpath)){
	        oggetto = gson.fromJson(rd, typeOfT);
	    } catch (IOException e) {
	        System.err.println(MSG_ERRORE_CARICAMENTO_FILE + e.getMessage());
	    }
	    return oggetto != null ? oggetto : null; // per collezioni non creiamo nuovi oggetti vuoti
	}
	
	/**
	 * Metodo generico per andare a creare l'oggetto se questo non è inizializzato
	 * @param <T>
	 * @param classe
	 * @return oggetto inizializzato
	 */
	private static <T> T creaOggettoVuoto(Class<T> classe) {
        try {
            return classe.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/*
	 * 
	 * 
	 * METODI PER SALVARE SU FILE TRAMITE JSON
	 * 
	 *  
	 */
	
	/**
	 * Metodo per salvare le gerarchie
	 * @param gerarchie
	 */
	public static void salvaGerarchie(ArrayList<Gerarchia> gerarchie) {
		salva(gerarchie, FILE_GERARCHIE);
	}
	
	/**
	 * Metodo per salvare i comprensori
	 * @param comprensori
	 */
	public static void salvaComprensori(ArrayList<Comprensorio> comprensori) {
		salva(comprensori, FILE_COMPRENSORI);
	}
	
	/**
	 * Metodo per salvare i configuratori
	 * @param configuratori
	 */
	public static void salvaConfiguratori(ArrayList<Configuratore> configuratori) {
		salva(configuratori, FILE_CONFIGURATORI);
	}
	
	/**
	 * Metodo per salvare i fattori di conversione
	 * @param fatConversione
	 */
	public static void salvaFatConversione(FatConversione fatConversione) {
		salva(fatConversione, FILE_FATT_CONVERSIONE);
	}
	
	public static void salvaCategorieFoglia(ArrayList<CategoriaFoglia> categorieFoglia) {
		salva(categorieFoglia, FILE_CATEGORIEFOGLIA);
	}
	
	public static void salvaFruitori(ArrayList<Fruitore> fruitori) {
		salva(fruitori, FILE_FRUITORI);
	}
	
	/*
	 * 
	 * 
	 * METODI PER CARICARE I FILE DA JSON
	 * 
	 * 
	 */
	
	/**
	 * Metodo per caricare le gerarchie
	 * @return insieme delle gerarchie
	 */
	public static ArrayList<Gerarchia> caricaGerarchie(){
	    Type listType = new TypeToken<ArrayList<Gerarchia>>() {}.getType();
	    ArrayList<Gerarchia> gerarchie = carica(listType, FILE_GERARCHIE);
	    if(gerarchie == null) {
	    	return new ArrayList<Gerarchia>();
	    }
	    return gerarchie;
	}

	
	/**
	 * Metodo per caricare i comprensori
	 * @return insieme dei comprensori
	 */
	public static ArrayList<Comprensorio> caricaComprensorio(){
	    Type listType = new TypeToken<ArrayList<Comprensorio>>() {}.getType();
	    ArrayList<Comprensorio> comprensori = carica(listType, FILE_COMPRENSORI);
	    if (comprensori == null) {
	        return new ArrayList<Comprensorio>();
	    }
	    return comprensori;
	}
	
	/**
	 * Metodo per caricare i configuratori
	 * @return insieme dei configuratori
	 */
	public static ArrayList<Configuratore> caricaConfiguratori(){
	    Type listType = new TypeToken<ArrayList<Configuratore>>() {}.getType();
	    ArrayList<Configuratore> configuratori = carica(listType, FILE_CONFIGURATORI);
	    if(configuratori == null) {
	    	return new ArrayList<Configuratore>();
	    }
	    return configuratori;
	}
	
	/**
	 * Metodo per caricare i fattori di conversione
	 * @return insieme dei fattori di conversione
	 */
	public static FatConversione caricaFatConversione(){
		Type listType = new TypeToken<FatConversione>() {}.getType();
		FatConversione fatConversione = carica(listType, FILE_FATT_CONVERSIONE);
		if(fatConversione == null) {
			return new FatConversione();
		}
		return fatConversione;
	}
	
	/**
	 * Metodo per caricare le categorie foglia presenti
	 * @return lista delle categorie foglia
	 */
	public static ArrayList<CategoriaFoglia> caricaCategorieFoglia() {
		Type listType = new TypeToken<ArrayList<CategoriaFoglia>>() {}.getType();
		ArrayList<CategoriaFoglia> categorieFoglia = carica(listType, FILE_CATEGORIEFOGLIA);
		if(categorieFoglia == null) {
			return new ArrayList<CategoriaFoglia>();
		}
		return categorieFoglia;
	}
	
	public static ArrayList<Fruitore> caricaFruitori() {
		Type listType = new TypeToken<ArrayList<Fruitore>>() {}.getType();
		ArrayList<Fruitore> fruitori = carica(listType, FILE_FRUITORI);
		if(fruitori == null) {
			return new ArrayList<Fruitore>();
		}
		return fruitori;
	}
	
}
