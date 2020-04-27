package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.AbstractDAO;
import DAO.AbstractDAOFactory;
import Personnels.CompositePersonnel;
import Personnels.Interface_annuaire;
import Personnels.Personnel;


/**
 * Dao pour la classe CompositePersonnels.
 */
public class DAOCompositePersonnelJDBC extends AbstractDAO<CompositePersonnel> {
    /**
     * constructeur de la classe.
     * @param connect se connecter a la base de donnee
     */
    public DAOCompositePersonnelJDBC(final Connection connect) {
        super(connect);
    }
    /**
     * creer un element dans la base de donnee.
     * @param object element a� ajouter
     */
    @Override
    public CompositePersonnel create(final CompositePersonnel object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO compositePersonnels (id)"
                    + " VALUES(?)");
            prepare.setInt(1, object.getId());
            int result = prepare.executeUpdate();
            assert result == 1;
            for (Interface_annuaire ip : object.getlistPersonnels()) {
                if (ip.getClass() == CompositePersonnel.class) {
                    createComposantComposite(object.getId(), ip.getId());
                } else {
                    createComposantPersonnel(object.getId(), ip.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.delete(object);
        }
        return object;
    }
    /**
     * cherche un element dans la base de donner.
     * @param id identifiant de l'objet a chercher
     */
    @Override
    public CompositePersonnel find(final int id) {
        CompositePersonnel cp = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM compositePersonnels WHERE id = ?");
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            if (result.first()) {
                cp = new CompositePersonnel();
                cp.setId(id);
                ArrayList<Interface_annuaire> array =
                        this.findComposant(id);
                for (Interface_annuaire ip : array) {
                    cp.addPersonnel(ip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cp;
    }
    /**
     * modifier un CompositePersonnels.
     * @param object donnees pour modifier
     */
    @Override
    public CompositePersonnel update(final CompositePersonnel object) {
        CompositePersonnel before = this.find(object.getId());
        if (before != null) {
            try {
                this.deleteComposant(object.getId());
                for (Interface_annuaire ip : object.getlistPersonnels()) {
                    if (ip.getClass() == CompositePersonnel.class) {
                        createComposantComposite(object.getId(), ip.getId());
                    } else {
                        createComposantPersonnel(object.getId(), ip.getId());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.delete(object);
                this.create(before);
                return before;
            }
        }
        return object;
    }
    /**
     * supprime de la bdd.
     * @param object l'objet a supprimer
     */
    @Override
    public void delete(final CompositePersonnel object) {
        try {
            this.deleteComposant(object.getId());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM compositePersonnels WHERE id = ?");
            prepare.setInt(1, object.getId());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creer la relation de composition entre 2 CompositePersonnels.
     * @param idComposite le composee
     * @param idComposant le composant
     * @throws SQLException Echec de la creation
     */
    private void createComposantComposite(final int idComposite, final int idComposant) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement("INSERT INTO composantComposite (idComposite,idComposant)"
        + " VALUES(?, ?)");
        prepare.setInt(1, idComposite);
        prepare.setInt(2, idComposant);
        int result = prepare.executeUpdate();
        assert result == 1;
    }
    /**
     * creer la relation de composition entre un CompositePersonnels et un Personnel.
     * @param idComposite le composee
     * @param idComposant le composant
     * @throws SQLException Echec de la creation
     */
    public void createComposantPersonnel(final int idComposite, final int idP) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement("INSERT INTO composantPersonnel (idComposite,idP)"
                + " VALUES(?, ?)");
        prepare.setInt(1, idComposite);
        prepare.setInt(2, idP);
        int result = prepare.executeUpdate();
        assert result == 1;
    }
    /**
     * cherche les composants du composite de type CompositePersonnels.
     * @param idComposite le composite en question
     * @return une liste de ses composants 
     * @throws SQLException Echec de la recherche
     */
    private ArrayList<Interface_annuaire> findComposantComposite(final int idComposite) throws SQLException {
        ArrayList<Interface_annuaire> array =
                new ArrayList<Interface_annuaire> ();
        PreparedStatement prepare = connect.prepareStatement("SELECT idComposant FROM composantComposite WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            CompositePersonnel finder = this.find(result.getInt("idComposant"));
            if(finder != null) array.add(finder);
        }
        return array;
    }
    /**
     * cherche les composants du composite de type Personnel.
     * @param idComposite le composite en question
     * @return une liste de ses composants 
     * @throws SQLException Echec de la recherche
     */
    private ArrayList<Interface_annuaire> findComposantPersonnel(final int idComposite) throws SQLException {
        ArrayList<Interface_annuaire> array =
                new ArrayList<Interface_annuaire> ();
        DAOFactoryJDBC factorytmp = (DAOFactoryJDBC) AbstractDAOFactory.getFactory("JDBC");
        DAOPersonnelJDBC daoPersonnels = (DAOPersonnelJDBC) factorytmp.getDaoPersonnel();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT idP FROM composantPersonnel WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            Personnel finder = daoPersonnels.find(result.getInt("idP"));
            if(finder != null) array.add(finder);
        }
        return array;
    }
    /**
     * cherche les composants du composite.
     * @param idComposite le composite en question
     * @return une liste de ses composants
     * @throws SQLException Echec de la recherche
     */
    private ArrayList<Interface_annuaire> findComposant(final int idComposite) throws SQLException {
        ArrayList<Interface_annuaire> arrayC =
                findComposantComposite(idComposite);
        ArrayList<Interface_annuaire> arrayP =
                findComposantPersonnel(idComposite);
        for (Interface_annuaire ip : arrayP) {
            arrayC.add(ip);
        }
        return arrayC;
    }
    /**
     * supprime la relation de composition d'un CompositePersonnel.
     * @param idComposite le composite composee d'element
     * @throws SQLException echec de suppression
     */
    private void deleteComposantComposite(final int idComposite) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantComposite WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        prepare.executeUpdate();
    }
    /**
     * supprime la relation de composition d'un Personnel.
     * @param idComposite le composite composee d'elements
     * @throws SQLException Echec de suppression
     */
    private void deleteComposantPersonnel(final int idComposite) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantPersonnel WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        prepare.executeUpdate();
    }
    /**
     * supprime la relation de composition.
     * @param idComposite le composite composé d'éléments
     * @throws SQLException échec de suppression
     */
    private void deleteComposant(final int idComposite) throws SQLException {
        deleteComposantPersonnel(idComposite);
        deleteComposantComposite(idComposite);
    }
}
