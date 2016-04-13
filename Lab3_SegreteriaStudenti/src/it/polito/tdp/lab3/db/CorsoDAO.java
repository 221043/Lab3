package it.polito.tdp.lab3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;

public class CorsoDAO {

	Connection conn = DBConnection.getConnection();
	
	public List<String> elencoCorsi(){
		Connection conn = DBConnection.getConnection();
		List<String> elenco = new LinkedList<String>();
		try{
			Statement st = conn.createStatement();
			String sql = "select nome from corso";
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				elenco.add(res.getString("nome"));
			}
			elenco.add("");
			res.close();
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore nel caricamento corsi da database");
		}
		return elenco;
	}
	
	public List<Studente> readIscritti(String corso){
		Connection conn = DBConnection.getConnection();
		List<Studente> elenco = new LinkedList<Studente>();
		String sql = "select * from iscrizione,corso,studente where corso.codins=iscrizione.codins and iscrizione.matricola=studente.matricola and corso.nome=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setString(1, corso);
			ResultSet res = st.executeQuery();
			while(res.next()){
				elenco.add(new Studente(res.getInt("matricola"), res.getString("cognome"), 
							res.getString("studente.nome"), res.getString("CDS"), null));			    
			}
			res.close();	
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore nella ricerca iscritti dal database");
		}
		return elenco;
	}
	
	public boolean iscrivi(String codins, int matricola){
		Connection conn = DBConnection.getConnection();
		String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES (?, ?)";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, codins);
			int res = st.executeUpdate();
			conn.close();
			if(res==1)
				return true;
			else
				return false;
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Errore in fase di iscrizione");
		}
		return false;
	}
	
	public Corso read(String corso){
		Connection conn = DBConnection.getConnection();
		String sql = "select * from corso where nome=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setString(1, corso);
			ResultSet res = st.executeQuery();
			if(res.next()){
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd"),null);
				return c;
			}
			res.close();
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore in readCorso");
		}
		return null;
	}
	
}
