package org.acme.vlille.dto;

import java.io.Serializable;

public class StationDTO implements Serializable {

	public StationDTO() {
		super();
	}



	private int index;
	private String nom;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nbvelosdispo == null) ? 0 : nbvelosdispo.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final StationDTO other = (StationDTO) obj;
		if (nbvelosdispo == null) {
			if (other.nbvelosdispo != null) {
				return false;
			}
		} else if (!nbvelosdispo.equals(other.nbvelosdispo)) {
			return false;
		}
		if (nom == null) {
			if (other.nom != null) {
				return false;
			}
		} else if (!nom.equals(other.nom)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "StationDTO [nom=" + nom + ", nbvelosdispo=" + nbvelosdispo + "]";
	}
}
