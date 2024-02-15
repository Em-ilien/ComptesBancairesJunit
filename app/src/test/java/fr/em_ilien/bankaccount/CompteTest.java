package fr.em_ilien.bankaccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import fr.em_ilien.bankaccount.adresse.AdresseImpl;
import fr.em_ilien.bankaccount.compte.Compte;
import fr.em_ilien.bankaccount.compte.CompteImpl;
import fr.em_ilien.bankaccount.exceptions.CompteSansTitulaireException;
import fr.em_ilien.bankaccount.exceptions.DebitMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.DecouvertMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;
import fr.em_ilien.bankaccount.personne.Personne;
import fr.em_ilien.bankaccount.personne.PersonneImpl;

class CompteTest {
	private static int accountId = 0;

	private Personne titulaire;
	private Compte compte;

	@BeforeEach
	void initialisation() throws Exception {
		accountId++;

		titulaire = new PersonneImpl("Cosson", "Emilien", new AdresseImpl("52", "rue",
				"des Docteurs Calmette et Guérin", "I.U.T. de Laval", "", "Laval", "53000", "France"));
		compte = null;
	}

	@Test
	void testCreerUnCompteAvecUnTitulaire() throws Exception {
		// Given
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertEquals(0, compte.solde());
		assertEquals(titulaire, compte.titulaire());
		assertEquals(Compte.DEFAULT_DECOUVERT_MAX_AUTORIZED, compte.decouvertMaximalAutorise());
		assertEquals(Compte.DEFAULT_DEBIT_MAX_AUTORIZED, compte.debitMaximalAutorise());
	}

	@Test
	void testCreerUnCompteSansTitulaire() {
		// When - Then
		assertThrows(CompteSansTitulaireException.class, () -> compte = new CompteImpl(accountId, null));
	}

	@Test
	void testCreerUnCompteAvecIdNegatif() throws Exception {
		// When - Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte = new CompteImpl(-1, titulaire));
	}

	@Test
	void testCreerUnCompteAvecDepotInitial() throws Exception {
		// Given
		final double depotInitial = 1000;
		// When
		compte = new CompteImpl(accountId, titulaire, depotInitial);
		// Then
		assertEquals(depotInitial, compte.solde());
	}

	@Test
	void testCreerUnCompteAvecTousLesParametres() throws Exception {
		// Given
		final double depotInitial = 1000;
		final double decouvertMaximalAutorisePersonnalise = 500;
		final double debitMaximalAutorisePErsonnalise = 2000;
		// When
		compte = new CompteImpl(accountId, titulaire, depotInitial, decouvertMaximalAutorisePersonnalise,
				debitMaximalAutorisePErsonnalise);
		// Then
		assertEquals(decouvertMaximalAutorisePersonnalise, compte.decouvertMaximalAutorise());
		assertEquals(debitMaximalAutorisePErsonnalise, compte.debitMaximalAutorise());
		assertEquals(depotInitial, compte.solde());
	}

	@Test
	void testChangerDecouvertMaximalAutorise() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		final double decouvertMaximalAutorisePersonnalise = 500;
		// When
		compte.changerDecouvertMaximalAutorise(decouvertMaximalAutorisePersonnalise);
		// Then
		assertEquals(decouvertMaximalAutorisePersonnalise, compte.decouvertMaximalAutorise());
	}

	@Test
	void testChangerDebitMaximalAutorise() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		final double debitMaximalAutorisePErsonnalise = 2000;
		// When
		compte.changerDebitMaximalAutorise(debitMaximalAutorisePErsonnalise);
		// Then
		assertEquals(debitMaximalAutorisePErsonnalise, compte.debitMaximalAutorise());
	}

	@Test
	void testCrediterSolde() throws Exception {
		// Given
		final double depotInitial = 1000;
		compte = new CompteImpl(accountId, titulaire, depotInitial);
		final double montantCredite = 500;
		// When
		compte.crediterSolde(montantCredite);
		// Then
		assertEquals(depotInitial + montantCredite, compte.solde());
	}

	@Test
	void testDebiterSolde() throws Exception {
		// Given
		final double depotInitial = 1000;
		final double montant = 500;
		compte = new CompteImpl(accountId, titulaire, depotInitial);
		// When
		compte.debiterSolde(montant);
		// Then
		assertEquals(depotInitial - montant, compte.solde());
	}

	@Test
	void testExecuterVirement() throws Exception {
		// Given
		final double depotInitial = 1000;
		final double montant = 500;
		Compte compteDebite = new CompteImpl(accountId, titulaire, depotInitial);
		Compte compteCredite = new CompteImpl(accountId + 1, titulaire);
		// When
		compteDebite.executerVirement(compteCredite, montant);
		// Then
		assertEquals(depotInitial - montant, compteDebite.solde());
		assertEquals(montant, compteCredite.solde());
	}

	@Test
	void testEstADecouvert() throws Exception {
		// Given
		final double depotInitial = 500;
		compte = new CompteImpl(accountId, titulaire, depotInitial);
		// When
		compte.debiterSolde(600);
		// Then
		assertTrue(compte.estADecouvert());
	}

	@Test
	void testNEstPasADecouvert() throws Exception {
		// Given
		final double depotInitial = 700;
		compte = new CompteImpl(accountId, titulaire, depotInitial);
		// When
		compte.debiterSolde(600);
		// Then
		assertFalse(compte.estADecouvert());
	}

	@Test
	void testDecouvert() throws Exception {
		// Given
		final double depotInitial = 500;
		compte = new CompteImpl(accountId, titulaire, depotInitial);
		// When
		compte.debiterSolde(600);
		// Then
		assertEquals(100, compte.decouvert());
	}

	@ParameterizedTest(name = "Solde:{0} DebitMax:{1} DecouvertMax:{2}, Expected:{3}")
	@CsvSource(value = { "0,default,default,800", "300,default,default,1000", "0,10,default,10",
			"0,10,5,5" }, nullValues = { "default" })
	void testMontantMaximumDebitAutorise(final double solde, final Double debitMax, final Double decouvertMax,
			final double expected) throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire, solde, decouvertMax, debitMax);
		// Then
		assertEquals(expected, compte.montantMaximumDebitAutorise());
	}

	@Test
	void testMontantMaximumDebitAutorisePersonnalise() throws Exception {
		// Given
		final double decouvertMaximalAutorisePersonalise = 10;
		compte = new CompteImpl(accountId, titulaire, 0, null, decouvertMaximalAutorisePersonalise);
		// Then
		assertEquals(decouvertMaximalAutorisePersonalise, compte.montantMaximumDebitAutorise());
	}

	@Test
	void testRecupererNumeroCompte() throws Exception {
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertEquals(accountId, compte.numero());
	}

	@Test
	void testDecouvertDeZeroSiCompteNonDecouvert() throws Exception {
		// When
		compte = new CompteImpl(accountId, titulaire, 10);
		// Then
		assertEquals(0, compte.decouvert());
	}

	@Test
	void testDecouvertZeroSiSoldeEgalZero() throws Exception {
		// When
		compte = new CompteImpl(accountId, titulaire, 0);
		// Then
		assertEquals(0, compte.decouvert());
	}

	@Test
	void testDebiterMontantNegatifNeDoitPasMarcher() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// When+Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.debiterSolde(-10));
	}

	@Test
	void testDebiterPlusQueLaLimiteDeDebitLautoriseNeDoitPasMarcher() throws Exception {
		// Given
		final int soldeInitial = 1000000000;
		compte = new CompteImpl(accountId, titulaire, soldeInitial);
		// When
		assertThrows(DebitMaximalAutoriseAtteintException.class, () -> compte.debiterSolde(soldeInitial));
	}

	@Test
	void testDebiterPlusQueLaBanqueNautoriseDeDecouvertNeDoitPasMarcher() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// When
		assertThrows(DecouvertMaximalAutoriseAtteintException.class,
				() -> compte.debiterSolde(Compte.DEFAULT_DECOUVERT_MAX_AUTORIZED + 10));
	}

	@Test
	void testChangerDecouvertMaximalAutoriseMontantNegatifNeDoitPasMarcher() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.changerDecouvertMaximalAutorise(-100.0));
	}

	@Test
	void testChangerDebitMaximalAutoriseMontantNegatifNeDoitPasMarcher() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.changerDebitMaximalAutorise(-100.0));
	}

	@Test
	void testCrediterSoldeMontantNegatifNeDoitPasMarcher() throws Exception {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.crediterSolde(-100.0));
	}

	@Test
	void testOuvrirUnCompteAvecUnSoldeNegatifNeDoitPasMarcher() {
		// When
		assertThrows(ValeurPositiveRequiseException.class, () -> compte = new CompteImpl(accountId, titulaire, -1));
	}

	@Test
	void testOuvrirUnCompteAvecUnMontantDeDebitMaximumAutoriseNegatifNeDoitPasMarcher() {
		// When
		assertThrows(ValeurPositiveRequiseException.class,
				() -> compte = new CompteImpl(accountId, titulaire, 1, null, -10.0));
	}

	@Test
	void testOuvrirUnCompteAvecUnMontantDeDecouvertMaximumAutoriseNegatifNeDoitPasMarcher() {
		// When
		assertThrows(ValeurPositiveRequiseException.class,
				() -> compte = new CompteImpl(accountId, titulaire, 1, -10.0, null));
	}

	@Test
	void testMontantMaxDebitAutoriseParDefautEstDeMilleEuros() throws Exception {
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertEquals(1000, compte.debitMaximalAutorise());
	}

	@Test
	void testMontantMaxDecouvertAutoriseParDefautEstDeHuitCentEuros() throws Exception {
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertEquals(800, compte.decouvertMaximalAutorise());
	}

}
