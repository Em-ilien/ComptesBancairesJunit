package fr.em_ilien.bankaccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.em_ilien.bankaccount.compte.Compte;
import fr.em_ilien.bankaccount.compte.CompteImpl;
import fr.em_ilien.bankaccount.compte.Personne;
import fr.em_ilien.bankaccount.compte.PersonneImpl;
import fr.em_ilien.bankaccount.compte.adresse.AdresseCloneur;
import fr.em_ilien.bankaccount.compte.adresse.AdresseImpl;
import fr.em_ilien.bankaccount.exceptions.ValeurPositiveRequiseException;

class PersonneTest {
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
	void testChangePersonAddress() throws ValeurPositiveRequiseException {
		// Given
		final String paris = "Paris";
		compte = new CompteImpl(accountId, titulaire);
		// When
		AdresseCloneur adresseCloneur = new AdresseCloneur(compte.titulaire().adresse());
		adresseCloneur.commune = paris;
		compte.titulaire().changerAdresse(adresseCloneur.construire());
		// Then
		assertEquals(paris, compte.titulaire().adresse().commune());
	}

	@Test
	void testPersonneNomEtPrenomCorrects() throws ValeurPositiveRequiseException {
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertEquals("Cosson", compte.titulaire().nom());
		assertEquals("Emilien", compte.titulaire().prenom());
	}

}
