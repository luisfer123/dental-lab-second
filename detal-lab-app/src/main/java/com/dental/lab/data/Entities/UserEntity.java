package com.dental.lab.data.Entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@ToString(exclude = {"addresses"})
@Entity
@Table(name = "User")
@NamedEntityGraphs({
	@NamedEntityGraph(
			name = "UserEntity.AddressesPhones",
			attributeNodes = {
					@NamedAttributeNode(value = "addresses"),
					@NamedAttributeNode(value = "phones")
			})
})
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column
	private String email;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "profile_picture", columnDefinition = "MEDIUMBLOB")
	private byte[] profilePicture;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "second_last_name")
	private String secondLastName;
	
	/**
	 * The id of the {@code AddressEntity} to be used as default address.
	 */
	@Column(name = "address_id")
	private Long defaultAddressId;
	
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE}
	)
	@JoinTable(
			name = "User_has_Authority",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id"))
	@Builder.Default
	private Set<AuthorityEntity> authorities = new HashSet<>();
	
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<AddressEntity> addresses;
	
	@OneToMany(mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<PhoneEntity> phones;

	public UserEntity(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void addAuthority(AuthorityEntity auth) {
		this.authorities.add(auth);
		auth.getUsers().add(this);
	}
	
	public void removeAuthority(AuthorityEntity auth) {
		this.authorities.remove(auth);
		auth.getUsers().remove(this);
	}
	
	public void addAddress(AddressEntity address) {
		this.getAddresses().add(address);
		address.setUser(this);
	}
	
	public void removeAddress(AddressEntity address) {
		this.getAddresses().remove(address);
		address.setUser(null);
	}
	
	public void addPhone(PhoneEntity phone) {
		this.getPhones().add(phone);
		phone.setUser(this);
	}
	
	public void removePhone(PhoneEntity phone) {
		this.getPhones().remove(phone);
		phone.setUser(null);
	}
	
	public Set<AuthorityEntity> getAuthorities() {
		if(authorities == null)
			authorities = new HashSet<>();
		
		return authorities;
	}
	
	public Set<AddressEntity> getAddresses() {
		if(addresses == null)
			addresses = new HashSet<>();
		
		return addresses;
	}
	
	public Set<PhoneEntity> getPhones() {
		if(phones == null)
			phones = new HashSet<>();
		
		return phones;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		
		if(!(o instanceof UserEntity))
			return false;
		
		UserEntity other = (UserEntity) o;
		
		return id != null &&
				id.equals(other.getId());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	
}
