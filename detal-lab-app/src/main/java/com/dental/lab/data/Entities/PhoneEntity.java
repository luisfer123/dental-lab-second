package com.dental.lab.data.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dental.lab.data.enums.EPhoneType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Phone")
public class PhoneEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column(name = "phone_number")
	private int phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "phone_type", columnDefinition = "ENUM('HOME', 'CELLPHONE', 'OFFICE', 'LABORATORY', 'DENTIST_OFFICE')")
	private EPhoneType phoneType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(!(o instanceof PhoneEntity))
			return false;
		
		PhoneEntity other = (PhoneEntity) o;
		return id != null &&
				id.equals(other.getId());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
