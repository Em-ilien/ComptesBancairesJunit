package fr.em_ilien.bankaccount.adresse;

public interface Adresse {

	public abstract String numeroDeVoie();

	public abstract String typeDeVoie();

	public abstract String nomDeVoie();

	public abstract String batiment();

	public abstract String lieuDit();

	public abstract String commune();

	public abstract String codePostal();

	public abstract String pays();
}