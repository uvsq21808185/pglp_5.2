package DAO;

import java.util.ArrayList;
import java.util.Map;

/**
 * Pattern DAO.
 * @param <T> type pour le DAO
 */
public abstract class DAO<T>{
	/*
	 * Obtenir un element par son identifiant
	 * @param id l'identifiant de l'element qu'on veut obtenir
	 * @return l'element souhaite
	 */
	public abstract T find(int id);
	
	/*
	 * Ajout un element au DAO
	 * @param obj l'objet a ajouter
	 */
	public abstract void ajouter(T obj) ;
	/*
	 * Obtenir la liste de tous les elements du DAO
	 * @return la liste des elements du DAO
	 */
	public abstract ArrayList<T> findAll();
	/*
	 * Permet de modifier un element du DAO
	 * @param obj l'element a modifier
	 * @param params les parametres qu'on veut modifier
	 */
	public abstract void update(T obj, Map<String, Object> params);
	/*
	 * Permet la supprission d'un element du DAO
	 * @param obj l'element a supprimer
	 */
	public abstract void delete(T obj);
}