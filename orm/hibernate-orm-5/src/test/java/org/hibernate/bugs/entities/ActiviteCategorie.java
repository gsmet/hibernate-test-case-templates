package org.hibernate.bugs.entities;

import javax.persistence.Entity;

@Entity
public class ActiviteCategorie extends ElementReference<ActiviteCategorie> {
	private static final long serialVersionUID = -8756438063411318267L;
	
	public ActiviteCategorie() {
		super();
	}
	
	public ActiviteCategorie(String code) {
		super(code);
	}

}
