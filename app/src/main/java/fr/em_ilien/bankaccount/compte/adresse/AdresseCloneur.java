package fr.em_ilien.bankaccount.compte.adresse;

public class AdresseCloneur {
	public String numeroDeVoie;
	public String typeDeVoie;
	public String nomDeVoie;
	public String batiment;
	public String lieuDit;
	public String commune;
	public String codePostal;
	public String pays;

	/**
	 * Copier une adresse existante pour en créer une nouvelle
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

	public Adresse construire() {
		return new AdresseImpl(numeroDeVoie, typeDeVoie, nomDeVoie, batiment, lieuDit, commune, codePostal, pays);
	}
}