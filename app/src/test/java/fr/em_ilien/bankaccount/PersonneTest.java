package fr.em_ilien.bankaccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.em_ilien.bankaccount.adresse.AdresseCloneur;
import fr.em_ilien.bankaccount.adresse.AdresseImpl;
import fr.em_ilien.bankaccount.compte.Compte;
import fr.em_ilien.bankaccount.compte.CompteImpl;
import fr.em_ilien.bankaccount.personne.Personne;
import fr.em_ilien.bankaccount.personne.PersonneImpl;

class PersonneTest {
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
	void testChangerAdressePersonne() throws Exception {
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
	void testNomEtPrenomCorrectsPersonne() throws Exception {
		// When
		compte = new CompteImpl(accountId, titulaire);
		// Then
		assertEquals("Cosson", compte.titulaire().nom());
		assertEquals("Emilien", compte.titulaire().prenom());
	}

}
