package fr.em_ilien.bankaccount.compte;

import fr.em_ilien.bankaccount.exceptions.DebitMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.DecouvertMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;
import fr.em_ilien.bankaccount.personne.Personne;

/**
 * Représente un compte bancaire. Est identifié par un numéro de compte unique.
 * L'unicité n'est pas gérée par ce programme.
 */
public interface Compte {
	public static final double DEFAULT_DECOUVERT_MAX_AUTORIZED = 800;
	public static final double DEFAULT_DEBIT_MAX_AUTORIZED = 1000;

	/**
	 * Change le montant de découvert maximal autorisé
	 * 
	 * @param montant montant maximal de découvert autorisé. Nombre positif.
	 * @throws ValeurPositiveRequiseException si le montant est négatif.
	 */
	public void changerDecouvertMaximalAutorise(Double montant) throws ValeurPositiveRequiseException;

	/**
	 * Change le montant de débit maximal autorisé
	 * 
	 * @param montant montant maximal de débit autorisé. Nombre positif.
	 * @throws ValeurPositiveRequiseException si le montant est négatif.
	 * 
	 */
	public void changerDebitMaximalAutorise(Double montant) throws ValeurPositiveRequiseException;

	/**
	 * Ajoute le montant au solde du compte
	 * 
	 * @param montant Nombre décimal positif en euros.
	 * @throws ValeurPositiveRequiseException si le montant est négatif.
	 * 
	 */
	public void crediterSolde(double montant) throws ValeurPositiveRequiseException;

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
	public void debiterSolde(double montant) throws DecouvertMaximalAutoriseAtteintException,
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
	public void executerVirement(Compte compteCredite, double montant) throws DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException, ValeurPositiveRequiseException;

	/**
	 * 
	 * @return true ou false selon que le solde est strictement négatif ou pas
	 */
	public boolean estADecouvert();

	/**
	 * 
	 * @return le montant de découvert courant en valeur absolue si le compte est à
	 *         découvert ; 0 sinon.
	 */
	public double decouvert();

	/**
	 * 
	 * @return le titulaire du compte
	 */
	public Personne titulaire();

	/**
	 * 
	 * @return le numéro unique attribué au compte à son ouverture
	 */
	public int numero();

	/**
	 * 
	 * @return le solde dispobile sur le compte
	 */
	public double solde();

	/**
	 * 
	 * @return le solde minimum que le compte est autorisé à avoir
	 */
	public double decouvertMaximalAutorise();

	/**
	 * 
	 * @return le montant maximum qu'il est possible de débiter en un seul débit
	 */
	public double debitMaximalAutorise();

	/**
	 * 
	 * @return le montant maximum qu'il est actuellement possible de débiter sur le
	 *         compte, en fonction du solde courant et du découvert maximal autorisé, ainsi que du débit maximal autorisé.
	 */
	public double montantMaximumDebitAutorise();
}
