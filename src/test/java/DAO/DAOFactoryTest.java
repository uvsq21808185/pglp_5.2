package DAO;

import static org.junit.Assert.*;

import org.junit.Test;

import Personnels.CompositePersonnel;
import Personnels.Personnel;


public class DAOFactoryTest {

	@Test
    public void testDaoPersonnel() {
        DAO<Personnel> dao = DAOFactory.getDAOPersonnel(null);
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void testDaoCompositePersonnels() {
        DAO<CompositePersonnel> dao = DAOFactory.getDAOCompositePersonnels(null);
        assertTrue(dao.findAll().isEmpty());
    }

}
