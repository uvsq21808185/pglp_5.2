package DAO;

import static org.junit.Assert.*;

import org.junit.Test;

import Personnels.CompositePersonnel;

public class DAOCompositePersonnelTest {


    @Test
    public void testAjouter() {
        DAOCompositePersonnel dcp = new DAOCompositePersonnel();
        CompositePersonnel cp = new CompositePersonnel();
        dcp.ajouter(cp);
        assertTrue(dcp.findAll().size() == 1 && dcp.find(cp.getId()) == cp);
    }

    @Test
    public void testdelete() {
        DAOCompositePersonnel dcp = new DAOCompositePersonnel();
        CompositePersonnel cp = new CompositePersonnel();
        dcp.ajouter(cp);
        dcp.delete(cp);
        assertTrue(dcp.findAll().isEmpty());
    }
    

}
