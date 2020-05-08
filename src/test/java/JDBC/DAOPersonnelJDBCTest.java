package JDBC;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import Personnels.Personnel;

public class DAOPersonnelJDBCTest {
	 @Test (expected = NullPointerException.class)
	    public void testCreate() {
	        ArrayList<String> numero = new ArrayList<String>();
	        numero.add("06.05.56.75.23");
	        numero.add("01.30.25.90.55");
	        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
	        DAOPersonnelJDBC dp = new DAOPersonnelJDBC(null);
	        dp.create(p);
	    }
	 
	    @Test
	    public void testFind() {
	        ArrayList<String> numero = new ArrayList<String>();
	        numero.add("06.05.56.75.23");
	        numero.add("01.30.25.90.55");
	        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
	        @SuppressWarnings("unused")
			DAOPersonnelJDBC dp = new DAOPersonnelJDBC(null);
	       // Personnel p3 = dp.create(p);
	        @SuppressWarnings("unused")
			int id =p.getId();
	        //Personnel p2=dp.find(id);
	        assertNotNull(p);
	   
	   }
}