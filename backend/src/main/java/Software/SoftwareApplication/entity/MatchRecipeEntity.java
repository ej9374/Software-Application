package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "match_recipe")
public class MatchRecipeEntity {

    @Id
    @Column(name = "new_recipe_id", nullable = false)
    private Integer newRecipeId; // Primary key for the `match_recipe` table

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-One relationship with the `recipe` table
    @JoinColumn(name = "recipe_id", nullable = false, referencedColumnName = "recipe_id")
    private RecipeEntity recipe; // Foreign key reference to the `recipe` table

    public MatchRecipeEntity(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}