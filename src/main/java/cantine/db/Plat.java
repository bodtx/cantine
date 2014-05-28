package cantine.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class Plat implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 20959505325353612L;

    @Id
    private String nom;

    @Column
    private boolean accompagnement;

}
