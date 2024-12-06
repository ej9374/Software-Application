package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeEntity {

    @Id
    @Column(name = "recipe_id")
    private Long recipeId; // Primary Key

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "minutes", nullable = false)
    private Integer minutes;

    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    @Column(name = "nutrition", columnDefinition = "TEXT")
    private String nutrition;

    @Column(name = "n_steps")
    private Integer nSteps;

    @Column(name = "steps", columnDefinition = "TEXT")
    private String steps;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ingredients", columnDefinition = "TEXT")
    private String ingredients;

    @Column(name = "n_ingredients")
    private Integer nIngredients;

    public RecipeEntity(String name, Integer minutes, String tags, String nutrition, Integer nSteps,
                        String steps, String description, String ingredients, Integer nIngredients) {
        this.name = name;
        this.minutes = minutes;
        this.tags = tags;
        this.nutrition = nutrition;
        this.nSteps = nSteps;
        this.steps = steps;
        this.description = description;
        this.ingredients = ingredients;
        this.nIngredients = nIngredients;
    }
}
