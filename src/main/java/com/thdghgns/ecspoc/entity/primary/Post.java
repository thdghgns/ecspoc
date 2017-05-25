package com.thdghgns.ecspoc.entity.primary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="POST")
@Data
public class Post implements Serializable {
	private static final long serialVersionUID = 79720125892324918L;

	@Id
	@GeneratedValue
	long id;
	
	@Column(name="subject")
	String subject;
	
	@Column(name="content", length = 100000000)
	String content;
}
