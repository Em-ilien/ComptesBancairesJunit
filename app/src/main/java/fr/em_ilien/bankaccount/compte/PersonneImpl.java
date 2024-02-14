package fr.em_ilien.bankaccount.compte;

import fr.em_ilien.bankaccount.compte.adresse.Adresse;

public class PersonneImpl extends Personne {

	/**
	 * Créer une personne
	 *
	 * @param nom     le nom de famille
	 * @param prenom  le prénom
	 * @param adresse l'adresse postale
	 */
	public PersonneImpl(String nom, String prenom, Adresse adresse) {
		super(nom, prenom, adresse);
	}

	@Override
	public String nom() {
		return nom;
	}

	@Override
	public String prenom() {
		return prenom;
	}

	@Override
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
