import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import fr.em_ilien.bankaccount.compte.Compte;
import fr.em_ilien.bankaccount.compte.CompteImpl;
import fr.em_ilien.bankaccount.compte.Personne;
import fr.em_ilien.bankaccount.compte.PersonneImpl;
import fr.em_ilien.bankaccount.compte.adresse.AdresseCloneur;
import fr.em_ilien.bankaccount.compte.adresse.AdresseImpl;
import fr.em_ilien.bankaccount.exceptions.DebitMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.DecouvertMaximalAutoriseAtteintException;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;

class CompteTest {
	private static int accountId = 0;

	private Personne titulaire;
	private Compte compte;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		accountId++;

		titulaire = new PersonneImpl("Cosson", "Emilien", new AdresseImpl("52", "rue",
				"des Docteurs Calmette et Guérin", "I.U.T. de Laval", "", "Laval", "53000", "France"));
		compte = null;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateCompteWithHolder() throws ValeurPositiveRequiseException {
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
	void testCreateCompteWithInitialBalance() throws ValeurPositiveRequiseException {
		// Given
		double initialBalance = 1000;
		// When
		compte = new CompteImpl(accountId, titulaire, initialBalance);
		// Then
		assertEquals(initialBalance, compte.solde());
	}

	@Test
	void testCreateCompteWithCustomParameters() throws ValeurPositiveRequiseException {
		// Given
		double initialBalance = 1000;
		double customDecouvertMaxAutorized = 500;
		double customDebitMaxAutorized = 2000;
		// When
		compte = new CompteImpl(accountId, titulaire, initialBalance, customDecouvertMaxAutorized,
				customDebitMaxAutorized);
		// Then
		assertEquals(customDecouvertMaxAutorized, compte.decouvertMaximalAutorise());
		assertEquals(customDebitMaxAutorized, compte.debitMaximalAutorise());
	}

	@Test
	void testChangerDecouvertMaximalAutorise() throws ValeurPositiveRequiseException {
		// Given
		double customDecouvertMaxAutorized = 500;
		compte = new CompteImpl(accountId, titulaire);
		// When
		compte.changerDecouvertMaximalAutorise(customDecouvertMaxAutorized);
		// Then
		assertEquals(customDecouvertMaxAutorized, compte.decouvertMaximalAutorise());
	}

	@Test
	void testChangerDebitMaximalAutorise() throws ValeurPositiveRequiseException {
		// Given
		double customDebitMaxAutorized = 2000;
		compte = new CompteImpl(accountId, titulaire);
		// When
		compte.changerDebitMaximalAutorise(customDebitMaxAutorized);
		// Then
		assertEquals(customDebitMaxAutorized, compte.debitMaximalAutorise());
	}

	@Test
	void testCrediterSolde() throws ValeurPositiveRequiseException {
		// Given
		double initialBalance = 1000;
		double amount = 500;
		compte = new CompteImpl(accountId, titulaire, initialBalance);
		// When
		compte.crediterSolde(amount);
		// Then
		assertEquals(initialBalance + amount, compte.solde());
	}

	@Test
	void testDebiterSolde() throws ValeurPositiveRequiseException, DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException {
		// Given
		double initialBalance = 1000;
		double amount = 500;
		compte = new CompteImpl(accountId, titulaire, initialBalance);
		// When
		compte.debiterSolde(amount);
		// Then
		assertEquals(initialBalance - amount, compte.solde());
	}

	@Test
	void testExecuterVirement() throws ValeurPositiveRequiseException, DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException {
		// Given
		double initialBalance = 1000;
		double amount = 500;
		Compte compteDebite = new CompteImpl(accountId, titulaire, initialBalance);
		Compte compteCredite = new CompteImpl(accountId + 1, titulaire);
		// When
		compteDebite.executerVirement(compteCredite, amount);
		// Then
		assertEquals(initialBalance - amount, compteDebite.solde());
		assertEquals(amount, compteCredite.solde());
	}

	@Test
	void testEstADecouvert() throws ValeurPositiveRequiseException, DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException {
		// Given
		double initialBalance = 500;
		compte = new CompteImpl(accountId, titulaire, initialBalance);
		// When
		compte.debiterSolde(600);
		// Then
		assertTrue(compte.estADecouvert());
	}

	@Test
	void testDecouvert() throws ValeurPositiveRequiseException, DecouvertMaximalAutoriseAtteintException,
			DebitMaximalAutoriseAtteintException {
		// Given
		double initialBalance = 500;
		compte = new CompteImpl(accountId, titulaire, initialBalance);
		// When
		compte.debiterSolde(600);
		// Then
		assertEquals(100, compte.decouvert());
	}

	@Test
	void testMontantMaximumDebitAutorise() throws ValeurPositiveRequiseException {
		// Given
		double initialBalance = 1000;
		compte = new CompteImpl(accountId, titulaire, initialBalance);
		// Then
		assertEquals(initialBalance, compte.montantMaximumDebitAutorise());
	}

	@Test
	void testRecupererNumeroCompte() throws ValeurPositiveRequiseException {
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertTrue(compte.numero() > 0);
	}

	@Test
	void testDecouvertZeroIFNotDecouvert() throws ValeurPositiveRequiseException {
		// When
		compte = new CompteImpl(accountId, titulaire, 10);
		// Then
		assertEquals(0, compte.decouvert());
	}

	@Test
	void testDecouvertZeroIfSoldeZero() throws ValeurPositiveRequiseException {
		// When
		compte = new CompteImpl(accountId, titulaire, 0);
		// Then
		assertEquals(0, compte.decouvert());
	}

	@Test
	void testDebiterNegativeMontantShouldNotWorks() throws ValeurPositiveRequiseException {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// When+Then
		assertThrowsExactly(ValeurPositiveRequiseException.class, () -> compte.debiterSolde(-10));
	}

	@Test
	void testDebiterMoreThanAuthorized() throws ValeurPositiveRequiseException {
		// Given
		compte = new CompteImpl(accountId, titulaire, 1000000000);
		// When
		assertThrowsExactly(DebitMaximalAutoriseAtteintException.class, () -> compte.debiterSolde(1000000000));
	}

	@Test
	void testDebiterMoreThanWeAreAllowedToBeInDecouvert() throws ValeurPositiveRequiseException {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// When
		assertThrowsExactly(DecouvertMaximalAutoriseAtteintException.class,
				() -> compte.debiterSolde(Compte.DEFAULT_DECOUVERT_MAX_AUTORIZED + 10));
	}

	@Test
	void testChangerDecouvertMaximalAutorise_NegativeAmount() throws ValeurPositiveRequiseException {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.changerDecouvertMaximalAutorise(-100.0));
	}

	@Test
	void testChangerDebitMaximalAutorise_NegativeAmount() throws ValeurPositiveRequiseException {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.changerDebitMaximalAutorise(-100.0));
	}

	@Test
	void testCrediterSolde_NegativeAmount() throws ValeurPositiveRequiseException {
		// Given
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertThrows(ValeurPositiveRequiseException.class, () -> compte.crediterSolde(-100.0));
	}

	@Test
	void testOuvrirUnCompteAvecUnSoldeNegatifNeDoitPasMarcher() {
		// When
		assertThrowsExactly(ValeurPositiveRequiseException.class,
				() -> compte = new CompteImpl(accountId, titulaire, -1));
	}

	@Test
	void testOuvrirUnCompteAvecUnMontantDeDebitMaximumAutoriseNegatifNeDoitPasMarcher() {
		// When
		assertThrowsExactly(ValeurPositiveRequiseException.class,
				() -> compte = new CompteImpl(accountId, titulaire, 1, null, -10.0));
	}

	@Test
	void testOuvrirUnCompteAvecUnMontantDeDecouvertMaximumAutoriseNegatifNeDoitPasMarcher() {
		// When
		assertThrowsExactly(ValeurPositiveRequiseException.class,
				() -> compte = new CompteImpl(accountId, titulaire, 1, -10.0, null));
	}

}
