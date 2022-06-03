package org.acme.vlille.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StationDTO implements Serializable {

	public StationDTO() {
		super();
	}



	private int index;
	private String nom;
	private Integer nbvelosdispo;

}
