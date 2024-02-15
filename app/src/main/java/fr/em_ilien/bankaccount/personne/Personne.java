package fr.em_ilien.bankaccount.personne;

import fr.em_ilien.bankaccount.adresse.Adresse;

/**
 * Représente un titulaire de compte. Peut être lié à un ou plusieurs comptes.
 */
public interface Personne {

	public String nom();

	public String prenom();

	public Adresse adresse();

	/**
	 * Changer l'adresse de la personne
	 * 
	 * @param nouvelleAdresse
	 */
	public void changerAdresse(Adresse nouvelleAdresse);

}