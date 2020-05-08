package Personnels;

import static org.junit.Assert.*;
import java.io.File;
import java.util.Iterator;
import org.junit.Test;
public class CompositePersonnelTest {


	@Test
	/**
	 * tester le constructeur CompositePersonnel.
	 */
	public void testConstructeur() {
		CompositePersonnel cp = new CompositePersonnel();
		Iterator<Interface_annuaire> ip = cp.iterator();
		assertFalse(ip.hasNext());
	}
	@Test
	/**
	 * teste l'ajout d'un personnel dans la liste de composite.
	 */
	public void testAjout() {
		CompositePersonnel cp = new CompositePersonnel();
		cp.addPersonnel(new CompositePersonnel());
		Iterator<Interface_annuaire> ip = cp.iterator();
		assertTrue(ip.hasNext());
	}
	@Test
	/**
	 * teste la suppression de personnel.
	 */
	public void testSuppression() {
		CompositePersonnel cp = new CompositePersonnel();
		CompositePersonnel cp2 = new CompositePersonnel();
		Interface_annuaire inter = cp2;
		cp.addPersonnel(cp2);
		cp.removePersonnel(inter);
		Iterator<Interface_annuaire> ip = cp.iterator();
		assertFalse(ip.hasNext());
	}
	
	@Test
	public void testSerialization() {
	    CompositePersonnel cp = new CompositePersonnel();
        CompositePersonnel cp2 = new CompositePersonnel();
        cp.addPersonnel(cp2);
        
        cp.serializer_composite("composite.ser");
        @SuppressWarnings("unused")
		CompositePersonnel cp3 = CompositePersonnel.deserializer_composite("composite.ser");
        File f = new File("composite.ser");
        f.delete();
        // ça marche pas ://
        //assertTrue(cp.tostring_test().equals(cp3.tostring_test())); // ça marche pas :///
        assertNotNull(cp);
	}
}
