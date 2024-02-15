package fr.em_ilien.bankaccount.compte;

import fr.em_ilien.bankaccount.exceptions.CompteSansTitulaireException;
import fr.em_ilien.bankaccount.exceptions.DebitMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.DecouvertMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;
import fr.em_ilien.bankaccount.personne.Personne;

public class CompteImpl implements Compte {
	private int id;
	private Personne titulaire;
	private double solde;
	private double decouvertMaximalAutorise;
	private double debitMaximalAutorise;

	/**
	 * Crée un nouveau compte avec un solde initial nul, un découvert maximal
	 * autorisé de 800 € et un débit maximal autorisé de 1 000 €
	 * 
	 * @param id        numéro du compte. Doit être unique.
	 * @param titulaire personne qui détient le compte
	 * @throws ValeurPositiveRequiseException si l'id est négatif.
	 * @throws CompteSansTitulaireException   si le titulaire est null
	 */
	public CompteImpl(int id, Personne titulaire) throws ValeurPositiveRequiseException, CompteSansTitulaireException {
		this(id, titulaire, 0);
	}

	/**
	 * Crée un nouveau compte en déposant un montant initial
	 * 
	 * @param id           numéro du compte. Doit être unique et positif.
	 * @param titulaire    personne qui détient le compte
	 * @param soldeInitial montant du dépôt initial. Peut être nul ou positif.
	 * @throws ValeurPositiveRequiseException si l'un des paramètres fournis qui
	 *                                        attend une valeur positive est
	 *                                        négative.
	 * @throws CompteSansTitulaireException   si le titulaire est null
	 */
	public CompteImpl(int id, Personne titulaire, double soldeInitial)
			throws ValeurPositiveRequiseException, CompteSansTitulaireException {
		this(id, titulaire, soldeInitial, null, null);
	}

	/**
	 * Crée un nouveau compte en réglant les paramètres de débit maximal autorisé et
	 * de découvert maximal autorisé
	 * 
	 * @param id                       numéro du compte. Doit être unique et
	 *                                 positif.
	 * @param titulaire                personne qui détient le compte
	 * @param soldeInitial             montant du dépôt initial. Peut être nul ou
	 *                                 positif.
	 * @param decouvertMaximalAutorise montant maximal de découvert autorisé. Nombre
	 *                                 positif. Passer null pour laisser la valeur
	 *                                 par défaut (800 €).
	 * @param debitMaximalAutorise     montant maximal de débit autorisé. Nombre
	 *                                 positif. Passer null pour laisser la valeur
	 *                                 par défaut (1 000 €).
	 * @throws ValeurPositiveRequiseException si l'un des paramètres fournis qui
	 *                                        attend une valeur positive est
	 *                                        négative.
	 * @throws CompteSansTitulaireException   si le titulaire est null
	 */
	public CompteImpl(int id, Personne titulaire, double soldeInitial, Double decouvertMaximalAutorise,
			Double debitMaximalAutorise) throws ValeurPositiveRequiseException, CompteSansTitulaireException {
		if (decouvertMaximalAutorise == null)
			decouvertMaximalAutorise = Compte.DEFAULT_DECOUVERT_MAX_AUTORIZED;
		if (debitMaximalAutorise == null)
			debitMaximalAutorise = Compte.DEFAULT_DEBIT_MAX_AUTORIZED;

		if (id < 0)
			throw new ValeurPositiveRequiseException("L'id du compte doit être positive.");
		if (soldeInitial < 0)
			throw new ValeurPositiveRequiseException("Le solde initial doit être positif.");
		if (decouvertMaximalAutorise < 0)
			throw new ValeurPositiveRequiseException("Le découvert maximal autorisé doit être positif.");
		if (debitMaximalAutorise < 0)
			throw new ValeurPositiveRequiseException("Le débit maximal autorisé doit être positif.");
		if (titulaire == null)
			throw new CompteSansTitulaireException("Un compte doit avoir un titulaire.");

		this.id = id;
		this.titulaire = titulaire;
		this.solde = soldeInitial;
		this.decouvertMaximalAutorise = decouvertMaximalAutorise;
		this.debitMaximalAutorise = debitMaximalAutorise;
	}

	public void changerDecouvertMaximalAutorise(Double montant) throws ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant du découvert maximal autorisé doit être positif.");
		this.decouvertMaximalAutorise = montant;
	}

	public void changerDebitMaximalAutorise(Double montant) throws ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant du débit maximal autorisé doit être positif.");
		this.debitMaximalAutorise = montant;
	}

	public void crediterSolde(double montant) throws ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant à créditer doit être positif.");
		this.solde += montant;
	}

	public void debiterSolde(double montant) throws DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException, ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant à débiter doit être positif.");
		if (soldePlusDecouvertAutorise() < montant)
			throw new DecouvertMaximalAutoriseAtteintException("Le montant du découvert maximal autorisé est dépassé.");
		if (montant > this.debitMaximalAutorise)
			throw new DebitMaximalAutoriseAtteintException("Le montant du débit maximal autorisé est dépassé.");
		this.solde -= montant;
	}

	public void executerVirement(Compte compteCredite, double montant) throws DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException, ValeurPositiveRequiseException {
		this.debiterSolde(montant);
		compteCredite.crediterSolde(montant);
	}

	public boolean estADecouvert() {
		return this.solde < 0;
	}

	public double decouvert() {
		if (estADecouvert())
			return Math.abs(this.solde);
		else
			return 0;
	}

	public Personne titulaire() {
		return titulaire;
	}

	public int numero() {
		return id;
	}

	public double solde() {
		return solde;
	}

	public double decouvertMaximalAutorise() {
		return decouvertMaximalAutorise;
	}

	public double debitMaximalAutorise() {
		return debitMaximalAutorise;
	}

	public double montantMaximumDebitAutorise() {
		return Math.min(soldePlusDecouvertAutorise(), this.debitMaximalAutorise);
	}

	private double soldePlusDecouvertAutorise() {
		return this.solde + this.decouvertMaximalAutorise;
	}
}
