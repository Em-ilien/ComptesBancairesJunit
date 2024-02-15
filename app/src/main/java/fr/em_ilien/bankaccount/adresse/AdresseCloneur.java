package fr.em_ilien.bankaccount.adresse;

/**
 * Permet de cloner une adresse pour en créer une nouvelle.
 */
public class AdresseCloneur {

	/**
	 * Initialiser les propriétés du cloneur avec celles de l'ancienne adresse
	 * 
	 * @param ancienneAdresse
	 */
	public AdresseCloneur(Adresse ancienneAdresse) {
		this.numeroDeVoie = ancienneAdresse.numeroDeVoie();
		this.typeDeVoie = ancienneAdresse.typeDeVoie();
		this.nomDeVoie = ancienneAdresse.nomDeVoie();
		this.batiment = ancienneAdresse.batiment();
		this.lieuDit = ancienneAdresse.lieuDit();
		this.commune = ancienneAdresse.commune();
		this.codePostal = ancienneAdresse.codePostal();
		this.pays = ancienneAdresse.pays();
	}

	/**
	 * Construit l'adresse
	 * 
	 * @return l'adresse obtenue à partir des propriétés
	 */
	public Adresse construire() {
		return new AdresseImpl(numeroDeVoie, typeDeVoie, nomDeVoie, batiment, lieuDit, commune, codePostal, pays);
	}

	/**
	 * ex. "55"
	 */
	public String numeroDeVoie;
	/**
	 * ex. "rue"
	 */
	public String typeDeVoie;
	/**
	 * ex. "du Faubourg St Honoré"
	 */
	public String nomDeVoie;
	/**
	 * ex. null
	 */
	public String batiment;
	/**
	 * ex. null
	 */
	public String lieuDit;
	/**
	 * ex. "Paris"
	 */
	public String commune;
	/**
	 * ex. "75000"
	 */
	public String codePostal;
	/**
	 * ex. "France"
	 */
	public String pays;
}