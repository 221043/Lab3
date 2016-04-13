package it.polito.tdp.lab3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;

public class StudenteDAO {
	
	public Studente read(int matricola){
		Connection conn = DBConnection.getConnection();
		String sql = "SELECT * FROM studente WHERE matricola=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			if(res.next()){
				Studente studente = new Studente(res.getInt("matricola"), res.getString("cognome"), res.getString("nome"), res.getString("CDS"), null);
				return studente;
			}
			res.close();
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Errore nel cercare studente nel database");
		}
		return null;
	}
	
	public List<Corso> readCorsi(int matricola){
		Connection conn = DBConnection.getConnection();
		List<Corso> elenco = new LinkedList<Corso>();
		String sql = "select corso.codins, corso.crediti, corso.nome, corso.pd from corso, iscrizione, studente where corso.codins=iscrizione.codins and iscrizione.matricola=studente.matricola and studente.matricola=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			while(res.next()){
				elenco.add(new Corso(res.getString("codins"), res.getInt("crediti"),
							res.getString("nome"), res.getInt("pd"), null));
			}
			res.close();
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore nella ricerca corsi associati allo studente nel database");
		}
		return elenco;
	}	

	public boolean isIscritto(String corso, int matricola){
		Connection conn = DBConnection.getConnection();
		String sql = "select matricola from iscrizione, corso where iscrizione.codins=corso.codins and corso.nome=? and matricola=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setString(1, corso);
			st.setInt(2, matricola);
			ResultSet res = st.executeQuery();
			if(res.next())
				return true;
		} catch(SQLException e){
			System.out.println("Errore nella ricerca corrispondenza studente-corso da database");
		}
		return false;
	}
	
}
