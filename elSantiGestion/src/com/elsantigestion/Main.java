package com.elsantigestion;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.DatabaseSetup;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.ui.ClienteApp;

import javafx.application.Application;


public class Main {
    public static void main(String[] args) {
    	DatabaseSetup.init();
    	
    	//ClienteDAO clienteDAO = new ClienteDAO();
    	//Cliente clientePrueba = new Cliente(0, "Pedro Miguel Gomez Perez", "12345678", "peter@mail.com", false);
        //clienteDAO.agregarCliente(clientePrueba);
    	
        Application.launch(ClienteApp.class, args);
    }
}