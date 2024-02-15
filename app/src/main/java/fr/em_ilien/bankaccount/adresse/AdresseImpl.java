package fr.em_ilien.bankaccount.adresse;

public class AdresseImpl implements Adresse {
	private String numeroDeVoie;
	private String typeDeVoie;
	private String nomDeVoie;
	private String batiment;
	private String lieuDit;
	private String commune;
	private String codePostal;
	private String pays;

	/**
	 * Créer une nouvelle adresse
	 * 
	 * @param numeroDeVoie ex. "55"
	 * @param typeDeVoie   ex. "rue"
	 * @param nomDeVoie    ex. "du Faubourd St Honoré"
	 * @param batiment     ex. null
	 * @param lieuDit      ex. null
	 * @param commune      ex. "Paris"
	 * @param codePostal   ex. "75000"
	 * @param pays         ex. "France"
	 */
	public AdresseImpl(String numeroDeVoie, String typeDeVoie, String nomDeVoie, String batiment, String lieuDit,
			String commune, String codePostal, String pays) {
		this.numeroDeVoie = numeroDeVoie;
		this.typeDeVoie = typeDeVoie;
		this.nomDeVoie = nomDeVoie;
		this.batiment = batiment;
		this.lieuDit = lieuDit;
		this.commune = commune;
		this.codePostal = codePostal;
		this.pays = pays;
	}

	public String numeroDeVoie() {
		return numeroDeVoie;
	}

	public String typeDeVoie() {
		return typeDeVoie;
	}

	public String nomDeVoie() {
		return nomDeVoie;
	}

	public String batiment() {
		return batiment;
	}

	public String lieuDit() {
		return lieuDit;
	}

	public String commune() {
		return commune;
	}

	public String codePostal() {
		return codePostal;
	}

	public String pays() {
		return pays;
	}
}
