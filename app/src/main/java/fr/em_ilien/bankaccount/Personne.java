package fr.em_ilien.bankaccount;

/**
 * Représente un titulaire de compte. Peut être lié à un ou plusieurs comptes.
 */
abstract class Personne {
	private String nom;
	private String prenom;
	private Adresse adresse;

	/**
	 * Créer une personne
	 * 
	 * @param nom     le nom de famille
	 * @param prenom  le prénom
	 * @param adresse l'adresse postale
	 */
	public Personne(String nom, String prenom, Adresse adresse) {
		// TODO
	}

	public abstract String nom();

	public abstract String prenom();

	public abstract Adresse adresse();

	/**
	 * Changer l'adresse de la personne
	 * 
	 * @param nouvelleAdresse
	 */
	public abstract void changerAdresse(Adresse nouvelleAdresse);

	public abstract class Adresse {
		private String numeroDeVoie;
		private String typeDeVoie;
		private String nomDeVoie;
		private String batiment;
		private String lieuDit;
		private String commune;
		private String codePostal;
		private String pays;

		/**
		 * Créer une nouvelle adresse
		 * 
		 * @param numeroDeVoie ex. "55"
		 * @param typeDeVoie   ex. "rue"
		 * @param nomDeVoie    ex. "du Faubourd St Honoré"
		 * @param batiment     ex. null
		 * @param lieuDit      ex. null
		 * @param commune      ex. "Paris"
		 * @param codePostal   ex. "75000"
		 * @param pays         ex. "France"
		 */
		public Adresse(String numeroDeVoie, String typeDeVoie, String nomDeVoie, String batiment, String lieuDit,
				String commune, String codePostal, String pays) {
			// TODO
		}

		/**
		 * Copier une adresse existante pour en créer une nouvelle
		 * 
		 * @param ancienneAdresse
		 */
		public Adresse(Adresse ancienneAdresse) {
			// TODO
		}

		public abstract String numeroDeVoie();

		public abstract String typeDeVoie();

		public abstract String nomDeVoie();

		public abstract String batiment();

		public abstract String lieuDit();

		public abstract String commune();

		public abstract String codePostal();

		public abstract String pays();
	}

}