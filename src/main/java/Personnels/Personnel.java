package Personnels;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe decrcrivant personnel et qui implemente l'interface "Interface_annuaire"
 * 
 */
public final class Personnel  implements Interface_annuaire, Serializable{
	
	/*
	 * Le numero de serie pour la serialisation
	 */
	private static final long serialVersionUID = 3230786493236051254L;

	/*
	 * L'identifiant de personnel.
	 */
    private int id;
    /*
     * Le nom de personnel
     */
	private final String nom;
	/*
	 * Le prenom de personnel.
	 */
	private final String prenom;
	/*
	 * Liste des numeros de telephone de la personnel.
	 */
	private final ArrayList<String> tel;
	/*
	 * La date de naissance de la personne.
	 */
	private final java.time.LocalDate Date;
	/*
	 * retourne la liste des numero de telephone de la personnel
	 * @return une liste contenant les numeros de telephone.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getTel() {
        return (ArrayList<String>) tel.clone();
    }
	/*
	 * retourne la date de naissance de la personne.
	 * @return la date de naissance.
	 */
    public java.time.LocalDate getDateNaissance() {
        return Date;
    }
    /*
     * retourne le prenom de la personne
     * @return le prenom de la personne.
     */
    public String getPrenom() {
        return prenom;
    }
    /*
     * retourne le nom de la personne
     * @return le nom de la personne
     */
    public String getNom() {
		return nom;
	}
    /*
     * obtenir l'identifiant de la personne.
     * @return l'id du personnel
     */
    public int getId() {
        return id;
    }
    /*
     * Modifier l'id de personnel
     * @param new-id le nouveau id
     */
    public void setId(int new_id) {
    	this.id=new_id;
    }
	/*
	 * Un constructeur qui utilise le builder pour creer le personnel
	 * @return un builder pour personnel
	 */
	private Personnel(final Personnel_Builder builder)
	{
		this.id = builder.id;
		this.nom=builder.nom;
		this.prenom=builder.prenom;
		this.tel= builder.tel;
		this.Date= builder.date;
	}
	/*
	 * Implementation de la methode tostring de l'interface.
	 * Cette methode tostring represente un descriptif d'un personnel.
	 */
	public void tostring() {
		System.out.print(prenom + " " + nom + ", Nee le : " + Date + ", numero de telephone : ");
		for (String i : tel) {
			System.out.print(i + "  ");
		}
		System.out.println("\n");
	}
	/*
	 * String representant l'objet Personnel : on aura besoin de cette methode pour le test
	 * tester si le String de l'objet Personnel est le meme que le string de l'objet deseralizer
	 */
	public String tostring_test() {
		String s = prenom + " " + nom + ", Nee le : " + Date + ", numero de telephone : ";
		for (String i : tel) {
			s+= i + "  ";
		}
		return s + "\n" ;
	}
	/*
	 * Le pattern Builder pour la classe Personnel.
	 */
	public static class Personnel_Builder {
		/*
		 * Identifiant personnel.
		 */
        private final int id;
        /*
         * une valeur pour incrementer a chaque fois l'id de personnel.
         */
        private static int next = 1;
        /*
         * Nom de la personne.
         */
		private final String nom;
		/*
		 * Le prenom de la personne.
		 */
		private final String prenom;
		/*
		 * La date de naissance de la personne.
		 */
		private java.time.LocalDate date;
		/*
		 * La liste de numeros de telephone de la personne.
		 */
		private ArrayList<String> tel;
		/*
		 * Constructeur pour la classe Personnel_Builder.
		 * @param nomP nom de la personne.
		 * @param prenomP le prenom de la personne.
		 */
		public Personnel_Builder(final String nomP, final String prenomP) {
			this.nom = nomP;
			this.prenom = prenomP;
			this.id = next++;
		
		}
		/*
		 * Une nouvelle classe de personnel instanciee en ajoutant la liste des numeros de telephone
		 * @param tel La liste de numeros de tel qu'on veut ajouter.
		 */
		public Personnel_Builder tel (ArrayList<String> tel) {
			this.tel=tel;
			return this;
		}
		/*
		 * Une nouvelle classe de personnel instanciee en ajoutant la date de naissance de la personne
		 * @param tel La date de naissance de la personne qu'on veur ajouter.
		 */
		public Personnel_Builder date (java.time.LocalDate date) {
			this.date=date;
			return this;
		}
		/*
		 * Construire un personnel à partir d'un builder.
		 * @return une instance de personnel utilisant le builder.
		 */
		public Personnel build() {
			return new Personnel(this);
		}
	}
	/*
	 * Serialiser l'objet Personnel vers le chemin path saisi en parametre
	 * @param path le chemin vers lequel on veut serializer
	 */
	public void serializer_personnel(final String path) {

	    ObjectOutputStream obj = null;
	    try {
	      final FileOutputStream fichier = new FileOutputStream(path);
	      obj = new ObjectOutputStream(fichier);
	      obj.writeChars("Descriptif personnel :");
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
    public static Personnel deserializer_personnel(final String path) {
        ObjectInputStream obj = null;
        Personnel p = null;
        try {
            FileInputStream fichier = new FileInputStream(path);
            obj = new ObjectInputStream(fichier);
            p = (Personnel) obj.readObject();
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