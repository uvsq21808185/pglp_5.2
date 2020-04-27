package JDBC;

import java.sql.Connection;

import DAO.AbstractDAO;
import DAO.AbstractDAOFactory;
import Personnels.CompositePersonnel;
import Personnels.Personnel;


/**
 * pattern factory.
 */
public class DAOFactoryJDBC extends AbstractDAOFactory {
    /**
     * Connection à la bdd.
     */
    private Connection connect;
    /**
     * Les classes utilitaires ne doivent pas
     * avoir de constructeur par défaut ou public.
     * @param connection se connecter
     */
    public DAOFactoryJDBC(final Connection connection) {
        connect = connection;
    }
    /**
     * fabrique Dao pour Personnel.
     * @return un Dao pour la classe Personnel
     */
    public AbstractDAO<Personnel> getDaoPersonnel() {
        return new DAOPersonnelJDBC(connect);
    }
    /**
     * fabrique Dao pour CompositePersonnel.
     * @return un Dao pour la classe CompositePersonnels
     */
    public AbstractDAO<CompositePersonnel>
    getDaoCompositePersonnels() {
        return new DAOCompositePersonnelJDBC(connect);
    }
}
