package kui.com.greenkiondo;

/**
 * Created by Kui on 12/11/2017.
 */

public class Ingredient {

    private String ingredient;
    private String recipe_title_ingredient;

    public Ingredient(){}

    public Ingredient(String ingredient){
        this.ingredient = ingredient;
    }

    public Ingredient(String ingredient,String recipe_title_ingredient){
        this.ingredient=ingredient;
        this.recipe_title_ingredient=recipe_title_ingredient;
    }


    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getRecipe_title_ingredient() {
        return recipe_title_ingredient;
    }

    public void setRecipe_title_ingredient(String recipe_title_ingredient) {
        this.recipe_title_ingredient = recipe_title_ingredient;
    }
}
