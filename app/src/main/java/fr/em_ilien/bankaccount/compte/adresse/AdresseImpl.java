package fr.em_ilien.bankaccount.compte.adresse;

public class AdresseImpl extends Adresse {

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
		super(numeroDeVoie, typeDeVoie, nomDeVoie, batiment, lieuDit, commune, codePostal, pays);
	}

	@Override
	public String numeroDeVoie() {
		return numeroDeVoie;
	}

	@Override
	public String typeDeVoie() {
		return typeDeVoie;
	}

	@Override
	public String nomDeVoie() {
		return nomDeVoie;
	}

	@Override
	public String batiment() {
		return batiment;
	}

	@Override
	public String lieuDit() {
		return lieuDit;
	}

	@Override
	public String commune() {
		return commune;
	}

	@Override
	public String codePostal() {
		return codePostal;
	}

	@Override
	public String pays() {
		return pays;
	}
}
