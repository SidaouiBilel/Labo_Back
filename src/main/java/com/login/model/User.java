package com.login.model;
import javax.persistence.*;


import com.login.enums.Message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
@Entity
@Table(name = "users")
public class User implements Serializable {
	

	
	@Id @GeneratedValue
	    private Long id;
	    private String password;
	     //  private Set<Role> roles;
	    private String email;
	    private String firstname;
	    private String lastname;
	    private String registrarId;
	   
		private String status;
	    @OneToMany(mappedBy="user",fetch=FetchType.LAZY)
		private Collection<Article> articles;
        private boolean registered;
        @OneToMany(mappedBy="user",fetch=FetchType.LAZY)
    	private Collection<Post> posts;
        @OneToMany(mappedBy="user",fetch=FetchType.LAZY)
        private Collection<Commentaire> commentaires;
        @OneToMany(mappedBy="user",fetch=FetchType.LAZY)
        private Collection<Message> msg_e;
        @OneToMany(mappedBy="user",fetch=FetchType.LAZY)
        private Collection<Message> msg_r;
        
	    public User( String email, boolean registered) {
		super();
		
		this.email = email;
		this.registered = registered;
	}
		public User(Long id, String password,// Set<Role> roles
	    		String email, String firstname,
				String lastname, String registrarId, String status) {
			super();
			this.id = id;
			this.password = password;
			//this.roles = roles;
			this.email = email;
			this.firstname = firstname;
			this.lastname = lastname;
			this.registrarId = registrarId;
			this.status = status;
		}
	    public Boolean isRegistered() {
	        return registered;
	    }

	    public void setRegistered(Boolean registered) {
	        this.registered = registered;
	    }
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getRegistrarId() {
			return registrarId;
		}

		public void setRegistrarId(String registrarId) {
			this.registrarId = registrarId;
		}

		

		public User() {
			super();
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public User(String email, Boolean registered) {
			super();
			this.email = email;
			this.registered = registered;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public Date getModifiedDate() {
			return modifiedDate;
		}

		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}

		
	    private Date createdDate;
	    private Date modifiedDate;
	    
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }



	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password)  {
	      
	   
	        this.password = password;
	    }

	   

	  /*  @ManyToMany
	    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	    public Set<Role> getRoles() {
	        return roles;
	    }

	    public void setRoles(Set<Role> roles) {
	        this.roles = roles;
	    }*/

}
