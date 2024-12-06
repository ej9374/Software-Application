package Software.SoftwareApplication.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recommendations")
public class RecommendationsEntity {

    @Id
    @Column(name = "recipe_id")
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "first", referencedColumnName = "recipe_id")
    private RecipeEntity first;

    @ManyToOne
    @JoinColumn(name = "second", referencedColumnName = "recipe_id")
    private RecipeEntity second;

    @ManyToOne
    @JoinColumn(name = "third", referencedColumnName = "recipe_id")
    private RecipeEntity third;

    @ManyToOne
    @JoinColumn(name = "forth", referencedColumnName = "recipe_id")
    private RecipeEntity forth;

    @ManyToOne
    @JoinColumn(name = "fifth", referencedColumnName = "recipe_id")
    private RecipeEntity fifth;


}

