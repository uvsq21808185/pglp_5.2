package DAO;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import Personnels.Personnel;

public class DAOPersonnelTest {

	 @Test
	    public void testAjouter() {
	        DAOPersonnel dp = new DAOPersonnel();
	        ArrayList<String> numero = new ArrayList<String>();
	        numero.add("06.05.56.75.23");
	        numero.add("01.30.25.90.55");
	        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
	        dp.ajouter(p);
	        assertTrue(dp.findAll().size() == 1 && dp.findAll().get(0) == p);
	    }

	    @Test
	    public void testdelete() {
	        DAOPersonnel dp = new DAOPersonnel();
	        ArrayList<String> numero = new ArrayList<String>();
	        numero.add("06.18.12.15.95");
	        numero.add("01.25.46.85.16");
	        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
	        dp.ajouter(p);
	        dp.delete(p);
	        assertTrue(dp.findAll().isEmpty());
	    }
	    @Test
	    public void testUpdate() {
	        DAOPersonnel dp = new DAOPersonnel();
	        ArrayList<String> numero = new ArrayList<String>();
	        numero.add("06.18.12.15.95");
	        numero.add("01.25.46.85.16");
	        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
	        dp.ajouter(p);
	        Map<String, Object> params = new HashMap<String, Object> ();
	        params.put("nom", "Si");
	        params.put("prenom", "Karim");
	        dp.update(p, params);
	        @SuppressWarnings("unused")
			Personnel p2 = new Personnel.Personnel_Builder("ZAOUAM", "Ali").date(LocalDate.of(1996,10,12)).tel(numero).build();
	        assertNotNull(dp.findAll().get(0));
	    }

}
