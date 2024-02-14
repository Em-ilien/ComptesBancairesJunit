package fr.em_ilien.bankaccount.compte;

import fr.em_ilien.bankaccount.exceptions.DebitMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.DecouvertMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;

/**
 * Représente un compte bancaire. Est identifié par un numéro de compte unique.
 * L'unicité n'est pas gérée par ce programme.
 */
public abstract class Compte {
	public static final double DEFAULT_DECOUVERT_MAX_AUTORIZED = 800;
	public static final double DEFAULT_DEBIT_MAX_AUTORIZED = 1000;

	protected int id;
	protected Personne titulaire;
	protected double solde;
	protected double decouvertMaximalAutorise;
	protected double debitMaximalAutorise;

	/**
	 * Crée un nouveau compte avec un solde initial nul, un découvert maximal
	 * autorisé de 800 € et un débit maximal autorisé de 1 000 €
	 * 
	 * @param id        numéro du compte. Doit être unique.
	 * @param titulaire personne qui détient le compte
	 * @throws ValeurPositiveRequiseException si l'id est négatif.
	 */
	public Compte(int id, Personne titulaire) throws ValeurPositiveRequiseException {
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
	 */
	public Compte(int id, Personne titulaire, double soldeInitial) throws ValeurPositiveRequiseException {
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
	 */
	public Compte(int id, Personne titulaire, double soldeInitial, Double decouvertMaximalAutorise,
			Double debitMaximalAutorise) throws ValeurPositiveRequiseException {
		if (decouvertMaximalAutorise == null)
			decouvertMaximalAutorise = Compte.DEFAULT_DECOUVERT_MAX_AUTORIZED;
		if (debitMaximalAutorise == null)
			debitMaximalAutorise = Compte.DEFAULT_DEBIT_MAX_AUTORIZED;

		this.id = id;
		this.titulaire = titulaire;
		if (soldeInitial < 0 || decouvertMaximalAutorise < 0 || debitMaximalAutorise < 0)
			throw new ValeurPositiveRequiseException(
					"Le solde initial, le découvert maximal autorisé et le débit maximal autorisé doivent être positifs.");
		this.solde = soldeInitial;
		this.decouvertMaximalAutorise = decouvertMaximalAutorise;
		this.debitMaximalAutorise = debitMaximalAutorise;
	}

	/**
	 * Change le montant de découvert maximal autorisé
	 * 
	 * @param montant montant maximal de découvert autorisé. Nombre positif.
	 * @throws ValeurPositiveRequiseException si le montant est négatif.
	 */
	public abstract void changerDecouvertMaximalAutorise(Double montant) throws ValeurPositiveRequiseException;

	/**
	 * Change le montant de débit maximal autorisé
	 * 
	 * @param montant montant maximal de débit autorisé. Nombre positif.
	 * @throws ValeurPositiveRequiseException si le montant est négatif.
	 * 
	 */
	public abstract void changerDebitMaximalAutorise(Double montant) throws ValeurPositiveRequiseException;

	/**
	 * Ajoute le montant au solde du compte
	 * 
	 * @param montant Nombre décimal positif en euros.
	 * @throws ValeurPositiveRequiseException si le montant est négatif.
	 * 
	 */
	public abstract void crediterSolde(double montant) throws ValeurPositiveRequiseException;

	/**
	 * Débite le montant au solde du compte
	 * 
	 * @param montant Nombre décimal positif en euros.
	 * @throws DecouvertMaximalAutoriseAtteintException si le découvert maximal
	 *                                                  autorisé du compte est
	 *                                                  dépasse
	 * @throws DebitMaximalAutoriseAtteintException     si le débit maximal autorisé
	 *                                                  est dépassé
	 * @throws ValeurPositiveRequiseException           si le montant est négatif.
	 */
	public abstract void debiterSolde(double montant) throws DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException, ValeurPositiveRequiseException;

	/**
	 * Virer de l'argent du compte courant à un autre
	 * 
	 * @param compteDebite  le compte qui sera débité
	 * @param compteCredite le compte à créditer
	 * @param montant       le montant à virer en euros
	 * @throws DecouvertMaximalAutoriseAtteintException si le découvert maximal
	 *                                                  autorisé du compte est
	 *                                                  dépasse
	 * @throws DebitMaximalAutoriseAtteintException     si le débit maximal autorisé
	 *                                                  est dépassé
	 * @throws ValeurPositiveRequiseException           si le montant est négatif.
	 */
	public abstract void executerVirement(Compte compteCredite, double montant)
			throws DecouvertMaximalAutoriseAtteintException, DebitMaximalAutoriseAtteintException,
			ValeurPositiveRequiseException;

	/**
	 * 
	 * @return true ou false selon que le solde est strictement négatif ou pas
	 */
	public abstract boolean estADecouvert();

	/**
	 * 
	 * @return le montant de découvert courant en valeur absolue si le compte est à
	 *         découvert ; 0 sinon.
	 */
	public abstract double decouvert();

	public abstract Personne titulaire();

	public abstract int numero();

	public abstract double solde();

	public abstract double decouvertMaximalAutorise();

	public abstract double debitMaximalAutorise();

	/**
	 * 
	 * @return le montant maximum qu'il est actuellement possible de débiter sur le
	 *         compte, en fonction du solde courant et du débit maximal autorisé.
	 */
	public abstract double montantMaximumDebitAutorise();
}
