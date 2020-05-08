
import java.time.LocalDate;
import java.util.ArrayList;

import Connexion.BDD;
import DAO.AbstractDAOFactory;
import JDBC.DAOFactoryJDBC;
import JDBC.DAOPersonnelJDBC;
import Personnels.Personnel;

/*
 * La classe main.
 * 
 * @author ZAOUAM Sirgaeddine
 * @version 2.0
 */
public class Main {

    public static void main(String[] args) throws Exception {
    	//Connexion a la base de donnee DERBY
        BDD.connexion();
        // Creation de la base de donnee
        BDD.CreateDataBase();
        // La fabrique abstraite de Dao Personnel
        DAOFactoryJDBC factory = (DAOFactoryJDBC) AbstractDAOFactory.getFactory("JDBC");
        DAOPersonnelJDBC daop = (DAOPersonnelJDBC) factory.getDaoPersonnel();
        // Creation d'un objet Personnel
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.05.56.75.23");
        numero.add("01.30.25.90.55");
        ArrayList<String> numero_ali = new ArrayList<String>();
        numero_ali.add("07.52.67.90.25");
        Personnel p = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
        Personnel p2 = new Personnel.Personnel_Builder("Abichou", "Ali").date(LocalDate.of(1970, 04, 07)).tel(numero_ali).build();
        // Insertion de deux personnels p et p2 dans la table Personnel de la base de donnee 
        daop.create(p);
        daop.create(p2);
        // prendre les deux identifiants de deux personnels
        int id1 = p.getId();
        int id2 = p2.getId();
        // Tester la fonction find en cherchant le personnel dont le id est celui de ZAOUAM
        Personnel res = daop.find(id1);
        Personnel res2 = daop.find(id2);
        // afficher son descriptif
    	res.tostring();
    	res2.tostring();
    	numero.add("01.03.00.00.02");
    	// Tester le update en ajoutant un numero de telephone a ZAOUAM
        Personnel p_update = new Personnel.Personnel_Builder("ZAOUAM", "Siragedine").date(LocalDate.of(1996,10,12)).tel(numero).build();
        p_update.setId(id1);
    	System.out.println("AFTER UPDATE");
    	Personnel p_new = daop.update(p_update);
    	// Re afficher son descriptif
    	p_new.tostring();
    	// test delete table personnel p
    	daop.delete(p);
    	
    }
}
