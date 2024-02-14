package fr.em_ilien.bankaccount.compte;

import fr.em_ilien.bankaccount.exceptions.DebitMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.DecouvertMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;

public class CompteImpl extends Compte {

	public CompteImpl(int id, Personne titulaire) throws ValeurPositiveRequiseException {
		super(id, titulaire);
	}

	public CompteImpl(int id, Personne titulaire, double soldeInitial) throws ValeurPositiveRequiseException {
		super(id, titulaire, soldeInitial);

	}

	public CompteImpl(int id, Personne titulaire, double soldeInitial, Double decouvertMaximalAutorise,
			Double debitMaximalAutorise) throws ValeurPositiveRequiseException {
		super(id, titulaire, soldeInitial, decouvertMaximalAutorise, debitMaximalAutorise);

	}

	@Override
	public void changerDecouvertMaximalAutorise(Double montant) throws ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant du découvert maximal autorisé doit être positif.");
		this.decouvertMaximalAutorise = montant;
	}

	@Override
	public void changerDebitMaximalAutorise(Double montant) throws ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant du débit maximal autorisé doit être positif.");
		this.debitMaximalAutorise = montant;
	}

	@Override
	public void crediterSolde(double montant) throws ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant à créditer doit être positif.");
		this.solde += montant;
	}

	@Override
	public void debiterSolde(double montant) throws DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException, ValeurPositiveRequiseException {
		if (montant < 0)
			throw new ValeurPositiveRequiseException("Le montant à débiter doit être positif.");
		if (this.solde - montant < -this.decouvertMaximalAutorise)
			throw new DecouvertMaximalAutoriseAtteintException("Le montant du découvert maximal autorisé est dépassé.");
		if (montant > this.debitMaximalAutorise)
			throw new DebitMaximalAutoriseAtteintException("Le montant du débit maximal autorisé est dépassé.");
		this.solde -= montant;
	}

	@Override
	public void executerVirement(Compte compteCredite, double montant) throws DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException, ValeurPositiveRequiseException {
		this.debiterSolde(montant);
		compteCredite.crediterSolde(montant);
	}

	@Override
	public boolean estADecouvert() {
		return this.solde < 0;
	}

	@Override
	public double decouvert() {
		if (estADecouvert())
			return Math.abs(this.solde);
		else
			return 0;
	}

	@Override
	public Personne titulaire() {
		return titulaire;
	}

	@Override
	public int numero() {
		return id;
	}

	@Override
	public double solde() {
		return solde;
	}

	@Override
	public double decouvertMaximalAutorise() {
		return decouvertMaximalAutorise;
	}

	@Override
	public double debitMaximalAutorise() {
		return debitMaximalAutorise;
	}

	@Override
	public double montantMaximumDebitAutorise() {
		return Math.min(this.solde + this.decouvertMaximalAutorise, this.debitMaximalAutorise);
	}
}
