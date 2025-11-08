package com.elsantigestion.controller;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.ui.Alerta;
import com.elsantigestion.ui.ClienteForm;
import com.elsantigestion.ui.ClienteView;

import javafx.collections.FXCollections;

public class ClienteController {
	
	private ClienteView view;
	private ClienteDAO dao;
	
	public ClienteController(ClienteView view, ClienteDAO dao) {
        this.view = view;
        this.dao = dao;
        inicializar();
    }
	
	public ClienteView getView() {
		return view;
	}
	
	public void guardarCliente(Cliente cliente) {
	    try {
	        cliente.validar();
	        if(dao.existeClientePorId(cliente.getId())) {
	        	dao.actualizarCliente(cliente);
	        }
	        else{
	        	dao.agregarCliente(cliente);
	        }
	        Alerta.info("Formulario de clientes", "El cliente se guardo correctamente!");
	    } catch (IllegalStateException e) {
	    	Alerta.warning("Error al validar datos", e.getMessage());
	    } catch (Exception e) {
	        Alerta.warning("Error al guardar el cliente", e.getMessage());
	    }
	}

    private void inicializar() {
        view.getTabla().setItems(FXCollections.observableArrayList(dao.obtenerClientes()));
        
        view.getBtnNuevo().setOnAction(e -> mostrarFormularioNuevo());
        view.getBtnModificar().setOnAction(e -> mostrarFormularioModificar());
        view.getBtnEliminar().setOnAction(e -> eliminarSeleccionado());
    }
    
    public void refrescarTabla() {
    	view.getTabla().getItems().setAll(dao.obtenerClientes());
    }

    private void mostrarFormularioNuevo() {
    	ClienteForm form = new ClienteForm(null);
		form.showAndWait();
		
		Cliente cliente = form.getCliente();
	    if (form.getCliente() != null) {
	        guardarCliente(cliente);
	        refrescarTabla();
	    }
    }
    
    private void mostrarFormularioModificar() {
    	Cliente seleccionado = view.getTabla().getSelectionModel().getSelectedItem();
	    
	    if (seleccionado != null) {
	        ClienteForm form = new ClienteForm(seleccionado);
	        
	        form.showAndWait();
	        
	        Cliente cliente = form.getCliente();
		    if (form.getCliente() != null) {
		        guardarCliente(cliente);
		        refrescarTabla();
		    }
	    } else {
	    	Alerta.warning("Atención", "Debe seleccionar un cliente para modificar.");
	    }
    }
    
    private void eliminarSeleccionado() {
    	Cliente seleccionado = view.getTabla().getSelectionModel().getSelectedItem();

	    if (seleccionado != null) {
	        boolean confirmado = Alerta.confirmar(
	            "Confirmar eliminación",
	            "¿Está seguro de eliminar el cliente: " + seleccionado.getNombre() + "?"
	        );
	        if (confirmado) {
	            dao.eliminarCliente(seleccionado.getId());
	            //view.refrescarTabla();
	        }
	    } else {
	        Alerta.warning("Atención", "Debe seleccionar un cliente para eliminar.");
	    }
	    refrescarTabla();
    }
    
}