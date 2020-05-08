package DAO;

import java.sql.Connection;

/**
 * Pattern DAO pour la fabrique abstraite.
 * @param <T> type pour le DAO
 * @author ZAOUAM Sirageddine
 * @version 2.0
 */
public abstract class AbstractDAO<T> {
    /**
     * La variable connect pour se connecter a la base de donnee "Derby"
     */
    protected Connection connect;
    /**
     * constructeur de la classe AbstractDAO.
     * @param connection connection a la base de donnee
     */
    public AbstractDAO(final Connection connection) {
        this.connect = connection;
    }
    /**
     * Permet d'ajouter un element au DAO.
     * @param object l'element qu'on veut ajouter
     * @return la creation du DAO
     */
    public abstract T create(T object);
    /**
     * Permet d'obtenir un element par son identifiant.
     * @param id identifiant de l'element qu'on veut obtenir
     * @return l'element souhaite
     */
    public abstract T find(int id);
    /**
     * Perment de modifier un element du DAO.
     * @param object l'element qu'on veut modifier
     * @return la modification de notre DAO.
     */
    public abstract T update(T object);
    /**
     * Permet de supprimer un element du DAO.
     * @param object l'element a supprimer
     */
    public abstract void delete(T object);
}
