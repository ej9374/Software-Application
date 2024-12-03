package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sign_up_recipe")
@Getter
@Setter
@NoArgsConstructor
public class SignUpRecipeEntity {

    @Id
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "minutes")
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
}
