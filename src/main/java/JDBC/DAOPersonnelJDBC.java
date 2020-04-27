package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import DAO.AbstractDAO;
import DAO.AbstractDAOFactory;
import Personnels.CompositePersonnel;
import Personnels.Personnel;


/**
 * DAO pour la classe Personnel.
 */

public class DAOPersonnelJDBC extends AbstractDAO<Personnel> {
    /**
     * constructeur de la classe.
     * @param connect se connecter a la base de donnee
     */
    public DAOPersonnelJDBC(final Connection connect) {
        super(connect);
    }
    /**
     * cree un element dans la bdd.
     * @param object element a† ajouter
     */
    @SuppressWarnings("deprecation")
    @Override
    public Personnel create(final Personnel object) {
        final int  annee = 1900;
        try {
            PreparedStatement prepare = connect.prepareStatement(
            "INSERT INTO personnel"
            + " (id,nom,prenom,dateNaissance)"
            + " VALUES(?, ?, ?, ?)");
            prepare.setInt(1, object.getId());
            prepare.setString(2, object.getNom());
            prepare.setString(3, object.getPrenom());
            Date date = new Date(object.getDateNaissance().getYear() - annee,
                    object.getDateNaissance().getMonthValue() - 1,
                    object.getDateNaissance().getDayOfMonth());
            prepare.setDate(4, date);
            int result = prepare.executeUpdate();
            assert result == 1;
            for (String num : object.getTel()) {
                createNumeroTelephone(num, object.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.delete(object);
        }
        System.out.println(" CREATE PERSONAL DONE !");

        return object;
    }
    /**
     * cherche un element dans la bdd.
     * @param id identifiant de l'objet a chercher
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public Personnel find(final int id) {
        final int annee = 1900;
        Personnel p = null;
        LocalDate dateNaissance;
        try {
            PreparedStatement prepare = connect.prepareStatement("SELECT * FROM personnel WHERE id = ?");
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                dateNaissance = LocalDate.of(
                        result.getDate("dateNaissance").getYear() + annee,
                        result.getDate("dateNaissance").getMonth() + 1,
                        result.getDate("dateNaissance").getDay());
                result.getString("nom");
                p = new Personnel.Personnel_Builder(
                        result.getString("nom"),
                        result.getString("prenom")).date(dateNaissance).tel(this.findNumeroTelephone(id)).build();    
                p.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(" FIND PERSONAL DONE !");
        return p;
    }
    /**
     * modifier un personnel.
     * @param object donn√©es pour modifier
     */
    @Override
    public Personnel update(final Personnel object) {
        final int annee = 1900;
        final Personnel before = this.find(object.getId());
        ArrayList<CompositePersonnel> composantBefore = null;
        try {
            composantBefore = findComposantPersonnel(object.getId());
        } catch (SQLException e1) {
            e1.printStackTrace();
            return before;
        }
        if (before != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                "UPDATE personnel SET nom = ?, prenom = ?, dateNaissance = ?" + " WHERE id = ?");
                prepare.setString(1, object.getNom());
                prepare.setString(2, object.getPrenom());
                @SuppressWarnings("deprecation")
                Date date = new Date(
                        object.getDateNaissance().getYear() - annee,
                        object.getDateNaissance().getMonthValue() - 1,
                        object.getDateNaissance().getDayOfMonth());
                prepare.setDate(3, date);
                prepare.setInt(4, object.getId());
                int result = prepare.executeUpdate();
                assert result == 1;
                this.deleteNumeros(object.getId());
                for (String num : object.getTel()) {
                    this.createNumeroTelephone(num, object.getId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.delete(object);
                this.create(before);
                DAOFactoryJDBC factorytmp = (DAOFactoryJDBC)
                        AbstractDAOFactory.getFactory("JDBC");
                DAOCompositePersonnelJDBC daoComposite = (DAOCompositePersonnelJDBC)
                        factorytmp.getDaoCompositePersonnels();
                for (CompositePersonnel cp : composantBefore) {
                    try {
                        daoComposite.createComposantPersonnel(cp.getId(), object.getId());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                return before;
            }
        }
        System.out.println(" UPDATE PERSONAL DONE !");

        return object;
    }
    /**
     * supprime de la bdd.
     * @param object l'objet a supprimer
     */
    @Override
    public void delete(final Personnel object) {
        final int un = 1;
        try {
            this.deleteComposantPersonnel(object.getId());
            this.deleteNumeros(object.getId());
            PreparedStatement prepare = connect.prepareStatement("DELETE FROM personnel WHERE id = ?");
            prepare.setInt(un, object.getId());
            int result = prepare.executeUpdate();
            assert result == un;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(" DELETE PERSONAL DONE !");

    }
    /**
     * Inserer les numeros de telephone du personnel dans la base de donnee
     * @param object le numero de telephone a inserer
     * @param idP l'identifiant du personnel
     * @return le numero de telephone
     * @throws SQLException si la creation echoue
     */
    private String createNumeroTelephone(final String object, final int idP)
            throws SQLException {
        PreparedStatement prepare = connect.prepareStatement("INSERT INTO numeroTelephone"+ " (idP,numero)"
        + " VALUES(?, ?)");
        prepare.setInt(1, idP);
        prepare.setString(2, object);
        int result = prepare.executeUpdate();
        assert result == 1;
        return object;
    }
    /**
     * rechercher tous les numeros de telephone d'un personnel.
     * @param idP l'id du personnel
     * @return un tableau avec les numeros de telephone
     * @throws SQLException si la recherche echoue
     */
    private ArrayList<String> findNumeroTelephone(final int idP)
            throws SQLException {
        ArrayList<String> numeroTelephone = new ArrayList<String> ();
        PreparedStatement prepare = connect.prepareStatement("SELECT numero FROM numeroTelephone WHERE idP = ?");
        prepare.setInt(1, idP);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            numeroTelephone.add(result.getString("numero"));
        }
        return numeroTelephone;
    }
    /**
     * supprimer de la bdd tous les numeros de telephone d'un personnel.
     * @param idPersonnel l'id du personnel
     * @throws SQLException si la suppression echoue
     */
    private void deleteNumeros(final int idP) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM numeroTelephone WHERE idP = ?");
        prepare.setInt(1, idP);
        prepare.executeUpdate();
    }
    private ArrayList<CompositePersonnel> findComposantPersonnel(final int idP)
            throws SQLException {
        ArrayList<CompositePersonnel> array =
                new ArrayList<CompositePersonnel> ();
        DAOFactoryJDBC factorytmp = (DAOFactoryJDBC) AbstractDAOFactory.getFactory("JDBC");
        DAOCompositePersonnelJDBC daoComposite = (DAOCompositePersonnelJDBC)
                factorytmp.getDaoCompositePersonnels();
        PreparedStatement prepare = connect.prepareStatement("SELECT idComposite FROM composantPersonnel WHERE idP = ?");
        prepare.setInt(1, idP);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            CompositePersonnel finder = daoComposite.find(result.getInt("idComposite"));
            if(finder != null) array.add(finder);
        }
        return array;
    }
    /**
     * supprime les relations de compositions de ce Personnel
     * @param idP le Personnel en question
     * @throws SQLException Echec de la recherche
     */
    private void deleteComposantPersonnel(final int idP) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantPersonnel WHERE idP = ?");
        prepare.setInt(1, idP);
        prepare.executeUpdate();
    }
}
