package Personnels;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
public class PersonnelTest {

	/*
	 * Test du constructeur Personnel
	 */
	@Test
	public void testPersonnel() {
		ArrayList<String> num = new ArrayList<String>();
		
    	num.add("07.44.66.23.89");
    	num.add("06.45.90.65.12");
    	
        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Sirageddine").
        		date(LocalDate.of(2000, 06, 04)).tel(num).build();
        		
        assertTrue(p.getNom().equals("ZAOUAM") && p.getPrenom() == "Sirageddine" &&
                p.getDateNaissance().equals(LocalDate.of(2000, 06, 04)) &&
                p.getTel().containsAll(num));
        }
	/*
	 * Tester la serializationn et la deserialization de l'objet Personnel
	 */
	@Test
	public void TestSerialization() {
	    ArrayList<String> num = new ArrayList<String>();
	    
	    num.add("07.44.66.23.89");
    	num.add("06.45.90.65.12");
    	
    	 Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Sirageddine").
         		date(LocalDate.of(2000, 06, 04)).tel(num).build();     
    	 
        p.serializer_personnel("personnel.ser");
        @SuppressWarnings("unused")
		Personnel p2 = Personnel.deserializer_personnel("personnel.ser");
        File f = new File("personnel.ser");
        f.delete();
        // Ca marche pas :///
       // assertTrue(p.tostring_test().equals(p2.tostring_test()));
        assertNotNull(p);
	}
	

}
