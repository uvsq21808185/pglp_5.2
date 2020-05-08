package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import JDBC.DAOFactoryJDBC;
/**
 * pattern de la fabrique.
 * @author ZAOUAM Sirageddine
 * @version 2.0
 */
public abstract class AbstractDAOFactory {
    /**
     * renvoie une fabrique selon le type de la fabrique saisie
     * saisir CRUD si on veut une fabrication simple de DAO
     * saisir JDBC si on veut une fabrication avec le JDBC
     * @param t type de fabrique souhaite
     * @return la fabrique, null sinon
     */
    public static Object getFactory(final String t) {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection("jdbc:derby:compositePattern;create=false");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (t=="CRUD") {
        	return new DAOFactory();}
        	else if (t=="JDBC") {
                return new DAOFactoryJDBC(connect);}
        return null;
    }
}

