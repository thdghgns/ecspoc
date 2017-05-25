package com.thdghgns.ecspoc.entity.secondary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="USER")
@Data
public class User implements Serializable{
	private static final long serialVersionUID = -3300949356038006964L;

	@Id
	@GeneratedValue
	long id;
	
	@Column(name="name")
	String name;
}
