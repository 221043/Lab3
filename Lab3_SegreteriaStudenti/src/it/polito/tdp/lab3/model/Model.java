package it.polito.tdp.lab3.model;

import java.util.List;

import it.polito.tdp.lab3.db.*;

public class Model {
	
	CorsoDAO cdao = new CorsoDAO();
	StudenteDAO sdao = new StudenteDAO();

	public List<String> riempiCorsi(){
		return cdao.elencoCorsi();
	}
	
	public Studente getStudent(int matricola){
		return sdao.read(matricola);
	}
	
	public Corso getCorso(String corso){
		return cdao.read(corso);
	}
	
	public boolean iscrivi(String codins, int matricola){
		Studente a = getStudent(matricola);
		Corso b = getCorso(codins);
		if(a!=null && b!=null){
			a.addCorso(b);
			b.addStudente(a);
		}
		return cdao.iscrivi(codins, matricola);
	}

	public String getCodIns(String nomeCorso){
		Corso c = getCorso(nomeCorso);
		return c.getCodIns();
	}
	
	public boolean isIscritto(String corso, Studente s){
		int matricola = s.getMatricola();
		return sdao.isIscritto(corso, matricola);
	}
	
	public String stringaIscritto(Studente s, String corso){
		return s.getNome()+" "+s.getCognome()+" ("+s.getMatricola()+") è iscritto al corso '"+corso+"'";
	}
	
	public String stringaNonIscritto(Studente s, String corso){
		return s.getNome()+" "+s.getCognome()+" ("+s.getMatricola()+") non è iscritto al corso '"+corso+"'";
	}
	
	public String stampaCorso(Corso c){
		return c.toString();
	}
	
	public String stampaStudente(Studente s){
		return s.toString();
	}
	
	public List<Corso> getCorsi(Studente s){
		int matricola = s.getMatricola();
		List<Corso> corsi = sdao.readCorsi(matricola);
		s.setCorsi(corsi);
		return corsi;
	}
	
	public List<Studente> getIscritti(Corso c){
		String nome = c.getNome();
		List<Studente> studenti = cdao.readIscritti(nome);
		c.setStudenti(studenti);
		return studenti;
	}
	
	public String nomeStudente(Studente s){
		return s.getNome();
	}
	
	public String cognomeStudente(Studente s){
		return s.getCognome();
	}
	
}
