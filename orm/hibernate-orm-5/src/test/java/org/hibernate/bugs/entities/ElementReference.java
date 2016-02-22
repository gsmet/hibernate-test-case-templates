package org.hibernate.bugs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.NaturalId;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ElementReference<ER extends ElementReference<?>> implements Serializable {

	private static final long serialVersionUID = 494451886995557059L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	@NaturalId
	private String code;
	
	protected ElementReference() {
	}
	
	protected ElementReference(String code) {
		this.code = code;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
