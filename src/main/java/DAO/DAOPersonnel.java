package DAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import Personnels.Personnel;

/**
 * DAO pour la classe Personnel et qui implemente l'interface DAO<T>.
 */
public class DAOPersonnel extends DAO<Personnel> implements Serializable {
	
	/**
	 * Serial number pour la serialisation
	 */
	private static final long serialVersionUID = 5814519732153071598L;
    /**
     * liste des Personnels du DAO.
     */
    private ArrayList<Personnel> list;
    /**
     * constructeur de la classe DAOPersonnel.
     */
    public DAOPersonnel() {
        list = new ArrayList<Personnel>();
    }
    /**
     * obtenir un objet personnel du DAO.
     * @param id identifiant de l'element a� obtenir
     * @return null si aucun personnel existe avec l'id spécifié
     * et le personnel existant sinon
     */
    public Personnel find(final int id) {
        for (Personnel p : list) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * ajouter un Personnel dans la liste..
     * @param object l'element a� ajouter
     */
    public void ajouter(final Personnel object) {
        list.add(object);
    }
    /**
     * obtenir une liste de tout les personnels du DAO.
     * @return une liste des personnels du DAO.
     */
    @SuppressWarnings("unchecked")
	public ArrayList<Personnel> findAll() {
        return (ArrayList<Personnel>) list.clone();
    }
    /*
     * Modifier l'objet personnel
     * On verifie a chaque fois si la cle est identique a l'attribut et si la valeur est de meme type
     * que l'attribut.
     * @param object l'element qu'on veut modifier
     * @param params les parametres à modifier
     */
    @SuppressWarnings("unchecked")
    public void update(final Personnel object,
            final Map<String, Object> params) {
        if (list.remove(object)) {
            String nom = "";
            if (params.containsKey("nom")) {
                nom = (String) params.get("nom");
            } else {
                nom = object.getNom();
            }
            String prenom = "";
            if (params.containsKey("prenom")) {
                prenom = (String) params.get("prenom");
            } else {
                prenom = object.getPrenom();
            }
            LocalDate dateNaissance;
            if (params.containsKey("Date")) {
                dateNaissance = (LocalDate) params.get("Date");
            } else {
                dateNaissance = object.getDateNaissance();
            }
            ArrayList<String> numeroTelephone;
            if (params.containsKey("tel")) {
                ArrayList<String> tmp;
                tmp = (ArrayList<String>) params.get("tel");
                numeroTelephone = (ArrayList<String>) tmp.clone();
            } else {
                numeroTelephone = object.getTel();
            }
            Personnel p = new Personnel.Personnel_Builder(
                nom, prenom).date(dateNaissance).tel(numeroTelephone).build();
            list.add(p);
        }
    }
    /**
     * supprime un personnel du DAO.
     * @param object élément à supprimer
     */
    public void delete(final Personnel object) {
        list.remove(object);
    }
    /*
	 * Serialiser l'objet Personnel vers le chemin path saisi en parametre
	 * @param path le chemin vers lequel on veut serializer
	 */
	public void serializer_daopersonnel(final String path) {

	    ObjectOutputStream obj = null;
	    try {
	      final FileOutputStream fichier = new FileOutputStream(path);
	      obj = new ObjectOutputStream(fichier);
	      obj.writeChars("Descriptif DAOPersonnel :");
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
	 * @param path le chemin depuis lequel on veut deserializer l'objet Personnel.
	 */
    public static DAOPersonnel deserializer_daopersonnel(final String path) {
        ObjectInputStream obj = null;
        DAOPersonnel daop = null;
        try {
            FileInputStream fichier = new FileInputStream(path);
            obj = new ObjectInputStream(fichier);
            daop = (DAOPersonnel) obj.readObject();
        } catch (IOException e) {
            System.err.println("La deserialization a echouee ");
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
        return daop;
    }
   
}


