package Personnels;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/** Classe CompositePersonnel qui herite de la classe Abstraire Interface_annuaire
 * @author ZAOUAM Sirageddine
 * @version 2.0
 *
 */
public class CompositePersonnel implements Interface_annuaire, Serializable {
	/*
	 * Le numero de serie pour la serialisation.
	 */
	private static final long serialVersionUID = -7949412292635467767L;
	/*
	 * Identifiant d'un composite.
	 */
    private  int id;
    /*
     * La liste des personnels.
     */
    private ArrayList<Interface_annuaire> listPersonnels;
    /*
     * une valeur pour incrementer a chaque fois l'id du nouveau composite.
     */
	private static int next = 1;
	/*
	 * Modifier l'Id du composite
	 * @param new_id le nouveau Id
	 */
	public void setId(int new_id) {
		this.id=new_id;
	}
	 /*
     * Retourne l'identifiant du composite.
     * @return l'identifiant de personnel.
     */
    public final int getId() {
        return id;
    }
    @SuppressWarnings("unchecked")
	public ArrayList<Interface_annuaire> getlistPersonnels() {
        return (ArrayList<Interface_annuaire>) listPersonnels.clone();
    }
    /*
     * La methode qui implemente l'interface Interface_annuaire.
     */
    public void tostring() 
    {
        System.out.println("ID : "+id);
        for (Interface_annuaire ip : listPersonnels) {
			ip.tostring();}   
    }
    /*
	 * String representant l'objet Personnel : on aura besoin de cette methode pour le test
	 * tester si le String de l'objet Personnel est le meme que le string de l'objet deseralizer.
	 * @return un String de personnel.
	 */
	public String tostring_test() {
		String s ="ID : " + id;
		for (Interface_annuaire ip : listPersonnels) {
			s+= ip.tostring_test() + "  ";
		}
		return s + "\n" ;
	}
    /* 
     * Constructeur qui prend l'identifiant du composite en entree.
     */
    public CompositePersonnel(int id)
    {
    	 this.id = id;
    	 listPersonnels = new ArrayList<Interface_annuaire>();
    }
    /*
     * Constructeur par defaut de CompositePersonnels.
     */
    public CompositePersonnel() {
        id = next++;
        listPersonnels = new ArrayList<Interface_annuaire>();
    }
    /* 
     * Methode pour ajouter des personnels dans la liste.
     * @param personnelle personnel à ajouter.
     * @return le composite personnel après l'ajout. 
     */
    public CompositePersonnel addPersonnel(Interface_annuaire personnel)
    {
        listPersonnels.add(personnel);
        return this;
    }
    /* 
     * Methode pour supprimer des personnels de la liste.
     * @param personnel le personnel qu'on veut supprimer.
     * @return le composite personnel après la suppression.
     */

    public CompositePersonnel removePersonnel(Interface_annuaire personnel)
    {
        listPersonnels.remove(personnel);
        return this;
    }
    /* 
     * Cette methode renvoie un iterateur sur ListPersonnels.
     * @return un itérateur sur listPersonnels.
     */
    public Iterator<Interface_annuaire> iterator() {
		return listPersonnels.iterator();
	}
    /*
	 * Serialiser l'objet CompositePersonnel vers le chemin path
	 * @param path le chemin vers lequel on veut serializer
	 */
	public void serializer_composite(final String path) {

	    ObjectOutputStream obj = null;
	    try {
	    	
	    	
	      final FileOutputStream fichier = new FileOutputStream(path);
	      obj = new ObjectOutputStream(fichier);
	      obj.writeChars("Descriptif CompositePersonnel :");
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
	 * Deserializer vers le chemin voulu "path"
	 * @param path le chemin depuis lequel on veut deserializer l'objet CompositePersonnel.
	 * @return un CompositePersonnel.
	 */
    public static CompositePersonnel deserializer_composite(final String path) {
        ObjectInputStream obj = null;
        CompositePersonnel p = null;
        try {
        	
            FileInputStream fichier = new FileInputStream(path);
            obj = new ObjectInputStream(fichier);
            p = (CompositePersonnel) obj.readObject();
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
    
    /**
     * vider la liste des personnels.
     */
    public void reset() {
        listPersonnels.clear();
    }
}
