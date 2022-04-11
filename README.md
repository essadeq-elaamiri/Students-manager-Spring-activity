# Students-manager-Spring-activity
### Enoncé 
>Créer une application Web basée sur Spring MVC, Spring Data JPA et Spring Security qui permet de gérer des étudiants.
Chaque étudiant est défini par:
- Son id
- Son nom
- Son prénom
- Son email
- Sa date naissance
- Son genre : MASCULIN ou FEMININ
- Un attribut qui indique si il est en règle ou non
  L'application doit offrir les fonctionnalités suivantes :
- Chercher des étudiants par nom
- Faire la pagination
- Supprimer des étudiants en utilisant la méthode (DELETE au lieu de GET)
- Saisir et Ajouter des étudiants avec validation des formulaires
- Editer et mettre à jour des étudiants
- Créer une page template
- Sécuriser l'accès à l'application avec un système d'authentification basé sur Spring security en utilisant la stratégie UseDetails Service
- Ajouter d'autres fonctionnalités supplémentaires


### Enoncé 
### entité Student
````java
@Data
@Entity
public class Student {
    
    // TODO: validations
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
  @Temporal(TemporalType.DATE)
    private Date birth;
    private GENDER gender;
    private boolean inRule; // an règle

}
````

