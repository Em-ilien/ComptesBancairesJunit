package fr.em_ilien.bankaccount.personne;

import fr.em_ilien.bankaccount.adresse.Adresse;

public class PersonneImpl implements Personne {
	private String nom;
	private String prenom;
	private Adresse adresse;

	/**
	 * Créer une personne
	 * 
	 * @param nom     le nom de famille
	 * @param prenom  le prénom
	 * @param adresse l'adresse postale
	 */
	public PersonneImpl(String nom, String prenom, Adresse adresse) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}

	/**
	 * @return le nom de famille de la personne
	 */
	public String nom() {
		return nom;
	}
	/**
	 * @return le prénom de la personne
	 */
	public String prenom() {
		return prenom;
	}

	/**
	 * @return l'adresse de la personne
	 */
	public Adresse adresse() {
		return adresse;
	}

	/**
	 * Changer l'adresse de la personne
	 *
	 * @param nouvelleAdresse
	 */
	@Override
	public void changerAdresse(Adresse nouvelleAdresse) {
		this.adresse = nouvelleAdresse;
	}
}
