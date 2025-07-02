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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id", nullable = false) // 외래 키 매핑
    private RecipeEntity recipe;

}
