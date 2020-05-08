package Personnels;
/*
 * Interface pour le pattern composite associe a CompositePersonnel.
 * 
 * @author ZAOUAM Sirageddine
 * @version 2.0
 */
public interface  Interface_annuaire {
	/*
	 * La methode abstraite pour afficher le personnel ou le composite.
	 */
	public abstract void tostring();
	/*
	 * Retourne le descriptif du Personnel ou de Composite sous forme d'une chaine de caractere
	 * On aura besoin de cette methode pour le test
	 */
	public abstract String tostring_test();
    int getId();

}
