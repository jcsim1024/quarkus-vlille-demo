package org.acme.vlille.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StationDTO implements Serializable {

	public StationDTO() {
		super();
	}

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

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNbvelosdispo() {
		return nbvelosdispo;
	}

	public void setNbvelosdispo(String nbvelosdispo) {
		this.nbvelosdispo = nbvelosdispo;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StationDTO other = (StationDTO) obj;
		if (nbvelosdispo == null) {
			if (other.nbvelosdispo != null)
				return false;
		} else if (!nbvelosdispo.equals(other.nbvelosdispo))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StationDTO [nom=" + nom + ", nbvelosdispo=" + nbvelosdispo + "]";
	}
}
