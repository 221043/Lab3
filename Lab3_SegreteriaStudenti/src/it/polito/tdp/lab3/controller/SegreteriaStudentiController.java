package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab3.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	private Model model = new Model();
	ObservableList<String> corsi = FXCollections.observableArrayList(model.riempiCorsi());
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbCorso;

    @FXML
    private TextField txtStudente;

    @FXML
    private Button btnV;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCerca;

    @FXML
    private Button btnIscriviti;

    @FXML
    private TextArea txtRes;

    @FXML
    private Button btnReset;

    @FXML
    void doCerca(ActionEvent event) {
    	String corso = cbCorso.getValue();
    	String m = txtStudente.getText();
    	String r="";
		Corso c = model.getCorso(corso);
    	if(corso.compareTo("")!=0 && m.compareTo("")!=0){
    		if(!m.matches("[0-9]+")){
        		txtRes.setText("Formato errato");
        		return;
        	}
    		int matricola = Integer.parseInt(m);
    		Studente s = model.getStudent(matricola);
    		if(s==null)
        		txtRes.setText("Matricola non presente nel sistema");
    		if(model.isIscritto(corso, s)){
    			txtRes.setText(model.stringaIscritto(s, corso));
    		}
    		else{
    			txtRes.setText(model.stringaNonIscritto(s, corso));
    		}
    			
    	} else if(corso.compareTo("")!=0){
    		if(!model.getIscritti(c).isEmpty()){
    			for(Studente stud:model.getIscritti(c)){
            		r += model.stampaStudente(stud) + "\n";
            	}
            	txtRes.setText(r);
    		}
    		else
    			txtRes.setText("Corso senza iscritti");  		
    	} else if(m.compareTo("")!=0){
    		if(!m.matches("[0-9]+")){
        		txtRes.setText("Formato errato");
        		return;
        	}
    		int matricola = Integer.parseInt(m);
    		Studente s = model.getStudent(matricola);
    		if(s==null)
    			txtRes.setText("Matricola non presente nel sistema");
    		else if(!model.getCorsi(s).isEmpty()){
    			for(Corso cor:model.getCorsi(s)){
            		r += model.stampaCorso(cor) + "\n";
            	}
            	txtRes.setText(r);
    		}
    		else
    			txtRes.setText("Studente senza corsi");
    	}	 
    	else
    		txtRes.setText("Dati mancanti");
    }

    @FXML
    void doCompleta(ActionEvent event) {
    	String m = txtStudente.getText();
    	if(m.compareTo("")==0){
    		txtRes.setText("Dati mancanti");
    		return;
    	} else if(!m.matches("[0-9]+")){
    		txtRes.setText("Formato errato");
    		return;
    	} 
    	int matricola = Integer.parseInt(m);
		Studente studente = model.getStudent(matricola);
    	if(studente==null)
    		txtRes.setText("Matricola non presente");
    	else{
    		txtNome.setText(studente.getNome());
        	txtCognome.setText(studente.getCognome());
    	}
    }

    @FXML
    void doIscriviti(ActionEvent event) {
    	String corso = cbCorso.getValue();
    	String m = txtStudente.getText();
    	if(corso.compareTo("")==0 || m.compareTo("")==0){
    		txtRes.setText("Dati mancanti");
    		return;
    	} else if(!m.matches("[0-9]+")){
    		txtRes.setText("Formato errato");
    		return;
    	} 
    	int matricola = Integer.parseInt(m);
		Studente studente = model.getStudent(matricola);
		String codins = model.getCodIns(corso);
    	if(studente==null)
        		txtRes.setText("Matricola non presente nel sistema");
    	else{
    		if(model.isIscritto(corso, studente))
    			txtRes.setText("Studente già iscritto al corso");
    		else if(model.iscrivi(codins, matricola))
    			txtRes.setText(studente.getCognome()+" "+studente.getNome()+" ("+matricola+")"
    					+ " è stato iscritto al corso '"+corso+"'");
    		else 
    			txtRes.setText(studente.getCognome()+" "+studente.getNome()+" ("+matricola+")"
    					+ " non è stato iscritto al corso '"+corso+"'");
    	}
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtStudente.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtRes.clear();
    	cbCorso.setValue("");
    }

    public void setModel(Model model){
    	this.model = model;
    }
    
    @FXML
    void initialize() {
        assert cbCorso != null : "fx:id=\"cbCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtStudente != null : "fx:id=\"txtStudente\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnV != null : "fx:id=\"btnV\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscriviti != null : "fx:id=\"btnIscriviti\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtRes != null : "fx:id=\"txtRes\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

        cbCorso.setValue("");
        cbCorso.setItems(corsi);
    }
    
}
