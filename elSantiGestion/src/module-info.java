/**
 * 
 */
/**
 * 
 */
module elSantiGestion {
	requires transitive javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	exports com.elsantigestion;
	exports com.elsantigestion.ui;
	exports com.elsantigestion.model;
    exports com.elsantigestion.dao;
    exports com.elsantigestion.utils;
}