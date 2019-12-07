package org.acme.vlille.domain;

public class Station {

	/**
	 * IDx.
	 */
	private String nom;

	/**
	 * Number of bikes.
	 */
	private String nbvelosdispo;

	public String getNom() {
		return nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public String getNbvelosdispo() {
		return nbvelosdispo;
	}

	public void setNbvelosdispo(final String nbvelosdispo) {
		this.nbvelosdispo = nbvelosdispo;
	}

	@Override
	public String toString() {
		return "Station [nom=" + nom + ", nbvelosdispo=" + nbvelosdispo + "]";
	}
}
