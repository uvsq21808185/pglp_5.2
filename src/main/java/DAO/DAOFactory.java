package DAO;

import Personnels.CompositePersonnel;
import Personnels.Personnel;

public final class DAOFactory {
	
	    /**
	     * Constructeur par defaut
	     */
	    public DAOFactory() {
	    }
	    
	    /**
	     * fabrique DAO pour Personnel.
	     * @param deserialize charger une sauvegarde
	     * @return DAOPersonnel deserializer ou vide si param = null
	     */
	    public static DAO<Personnel> getDAOPersonnel(final String deserialize) {
	        if (deserialize == null) {
	            return new DAOPersonnel();
	        } else {
	            return DAOPersonnel.deserializer_daopersonnel(deserialize);
	        }
	    }
	    /**
	     * fabrique DAO pour CompositePersonnel.
	     * @param deserialize charger une sauvegarde
	     * @return DaoCompositePersonnel deserializer ou vide si param = null
	     */
	    public static DAO<CompositePersonnel>
	    getDAOCompositePersonnels(final String deserialize) {
	        if (deserialize == null) {
	            return new DAOCompositePersonnel();
	        } else {
	            return DAOCompositePersonnel.deserializer_daocompositepersonnel(deserialize);
	        }
	    }

}
