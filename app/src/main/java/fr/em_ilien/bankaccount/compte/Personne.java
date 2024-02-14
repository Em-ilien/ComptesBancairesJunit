package fr.em_ilien.bankaccount.compte;

import fr.em_ilien.bankaccount.compte.adresse.Adresse;

/**
 * Représente un titulaire de compte. Peut être lié à un ou plusieurs comptes.
 */
public abstract class Personne {
	protected String nom;
	protected String prenom;
	protected Adresse adresse;

	/**
	 * Créer une personne
	 * 
	 * @param nom     le nom de famille
	 * @param prenom  le prénom
	 * @param adresse l'adresse postale
	 */
	public Personne(String nom, String prenom, Adresse adresse) {
		  this.nom = nom;
	        this.prenom = prenom;
	        this.adresse = adresse;
	}

	public abstract String nom();

	public abstract String prenom();

	public abstract Adresse adresse();

	/**
	 * Changer l'adresse de la personne
	 * 
	 * @param nouvelleAdresse
	 */
	public abstract void changerAdresse(Adresse nouvelleAdresse);

}