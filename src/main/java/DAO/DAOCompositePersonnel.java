package DAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import Personnels.CompositePersonnel;
import Personnels.Interface_annuaire;
/**
 * DAO pour la classe CompositePersonnel.
 * @author ZAOUAM Sirageddine
 * @version 2.0
 */

public class DAOCompositePersonnel extends DAO<CompositePersonnel> implements Serializable {
	
    /**
	 * Serial number pour la serialisation
	 */
	private static final long serialVersionUID = -1954034720189563285L;
	/**
     * La liste des CompositePersonnels du DAO.
     */
    private ArrayList<CompositePersonnel> list;
    /**
     * constructeur de la classe CompositePersonnels.
     */
    public DAOCompositePersonnel() {
        list = new ArrayList<CompositePersonnel>();
    }
    /**
     * Permet d'obtenir un CompositePersonnels du DAO.
     * @param id identifiant de l'element à obtenir
     * @return null si aucun CompositePersonnels existe avec l'id specifie
     */
    public CompositePersonnel find(final int id) {
        for (CompositePersonnel cp : list) {
            if (cp.getId() == id) {
                return cp;
            }
        }
        return null;
    }
    /**
     * Permet d'ajouter un CompositePersonnels dans la liste du DAO.
     * @param object l'element à ajouter
     */
    public void ajouter(final CompositePersonnel object) {
        list.add(object);
    }
    /**
     * Permet d'obtenir la liste de tout les CompositePersonnels du DAO.
     * @return une liste des CompositePersonnels du DAO
     */
    @SuppressWarnings("unchecked")
    public ArrayList<CompositePersonnel> findAll() {
        return (ArrayList<CompositePersonnel>) list.clone();
    }
    /*
     * Modifier l'objet CompositePersonnel
     * On verifie a chaque fois si la cle est identique a l'attribut et si la valeur est de meme type
     * que l'attribut.
     * @param object l'element qu'on veut modifier
     * @param params les parametres à modifier
     */
    @SuppressWarnings("unchecked")
    public void update(final CompositePersonnel object,
            final Map<String, Object> params) {
        if (list.contains(object)) {
            if (params.containsKey("personnels")) {
                ArrayList<Interface_annuaire> remplace =
                (ArrayList<Interface_annuaire>)
                params.get("personnels");
                object.reset();
                for (Interface_annuaire ip : remplace) {
                    object.addPersonnel(ip);
                }
            }
        }
    }
    /**
     * supprime un CompositePersonnels du DAO.
     * @param object l'element a� supprimer
     */
    public void delete(final CompositePersonnel object) {
        list.remove(object);
    }
    /*
	 * Serialiser l'objet Personnel vers le chemin path
	 * @param path le chemin vers lequel on veut serializer l'objet DAOCompositePersonnel
	 */
	public void serializer_daocompositepersonnel(final String path) {

	    ObjectOutputStream obj = null;
	    try {
	      final FileOutputStream fichier = new FileOutputStream(path);
	      obj = new ObjectOutputStream(fichier);
	      obj.writeChars("Descriptif DAOComposite personnel :");
	      obj.writeObject(this);		     
	      obj.flush();
          obj.close();
	    } catch (final java.io.IOException e) {
	      e.printStackTrace();
	    }
	    finally {
	      try {
	        if (obj != null) {
	          obj.flush();
	          obj.close();
	        }
	      } catch (final IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	    System.out.print("DONE !");
	}
	/*
	 * Deserializer vers le chemin voulu "path" saisi en parametre
	 * @param path le chemin depuis lequel on veut deserializer l'objet DAOCompositePersonnel.
	 * @return un DAOCompositePersonnel.
	 */
    public static DAOCompositePersonnel deserializer_daocompositepersonnel(final String path) {
        ObjectInputStream obj = null;
        DAOCompositePersonnel p = null;
        try {
            FileInputStream fichier = new FileInputStream(path);
            obj = new ObjectInputStream(fichier);
            p = (DAOCompositePersonnel) obj.readObject();
        } catch (IOException e) {
            System.err.println("La deserialization a échoué");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return p;
    }
	

}
