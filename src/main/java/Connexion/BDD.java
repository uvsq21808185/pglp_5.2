package Connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * La classe qui intéragit la base de données.
 * 
 * @author ZAOUAM Sirageddine.
 * @version 3.0
 */
public abstract class BDD {
    /**
     * créer la base de donnée
     * @throws SQLException i
     */
    public static void CreateDataBase() throws Exception {
        Connection connect = DriverManager.getConnection("jdbc:derby:compositePattern;create=false");
        BDD.deleteTables(connect);
        BDD.initTablePersonnel(connect);
        BDD.initTableNumeroTelephone(connect);
        initTableCompositePersonnels(connect);
        initTableRelationCC(connect);
        initTableRelationCP(connect);
    }
    /*
     * Se connecter à la base de donnée Derby.
     */
    public static void connexion() throws SQLException {
        DriverManager.getConnection("jdbc:derby:compositePattern;create=true");
    }
    /*
     * Supprimer toutes les tables de la bases de données.
     */
    private static void deleteTables(Connection connect) {
        Statement stat = null;
        try {
            stat = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stat.execute("drop table numeroTelephone");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table composantComposite");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table composantPersonnel");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table compositePersonnels");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table personnel");
        } catch (SQLException e) {
        }
    }
    /*
     * Initialisation de la table Personnel.
     */
    private static void initTablePersonnel(Connection connect) throws SQLException {
        String table = "create table personnel ("
                + "id int primary key,"
                + "nom varchar(30),"
                + "prenom varchar(30),"
                + "dateNaissance date"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /*
     * Initialisation de la table NumeroTelephone.
     */
    private static void initTableNumeroTelephone(Connection connect) throws SQLException {
        String table = "create table numeroTelephone ("
                + "idP int,"
                + "numero varchar(30),"
                + "primary key (idP,numero),"
                + "foreign key (idP) references personnel (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /*
     * Initialisation de la table CompositePersonnels.
     */
    private static void initTableCompositePersonnels(Connection connect) throws SQLException {
        String table = "create table compositePersonnels ("
                + "id int primary key"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /**
     * Initialisation de la tbale RelationCC
     * @param connect
     * @throws SQLException
     */
    private static void initTableRelationCC(Connection connect) throws SQLException {
        String table = "create table composantComposite ("
                + "idComposite int,"
                + "idComposant int,"
                + "primary key (idComposite, idComposant),"
                + "foreign key (idComposite) references compositePersonnels (id),"
                + "foreign key (idComposant) references compositePersonnels (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /*
     *Initialisation de la tbale RelationCP.
     */
    private static void initTableRelationCP(Connection connect) throws SQLException {
        String table = "create table composantPersonnel ("
                + "idComposite int,"
                + "idP int,"
                + "primary key (idComposite, idP),"
                + "foreign key (idComposite) references compositePersonnels (id),"
                + "foreign key (idP) references personnel (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
}
