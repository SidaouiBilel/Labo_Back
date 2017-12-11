/*package com.login.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements  Serializable {
    private Long id;
    private String name;
    private Set<User> users;

    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public Role(){}

	public Role(Long id, String roleName, Set<User> users) {
		super();
		this.id = id;
		this.name = roleName;
		this.users = users;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
*/