package fr.em_ilien.bankaccount.compte.adresse;

public abstract class Adresse {
	protected String numeroDeVoie;
	protected String typeDeVoie;
	protected String nomDeVoie;
	protected String batiment;
	protected String lieuDit;
	protected String commune;
	protected String codePostal;
	protected String pays;

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
	public Adresse(String numeroDeVoie, String typeDeVoie, String nomDeVoie, String batiment, String lieuDit,
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

	public abstract String numeroDeVoie();

	public abstract String typeDeVoie();

	public abstract String nomDeVoie();

	public abstract String batiment();

	public abstract String lieuDit();

	public abstract String commune();

	public abstract String codePostal();

	public abstract String pays();
}