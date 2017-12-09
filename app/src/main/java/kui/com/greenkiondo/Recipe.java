package kui.com.greenkiondo;

/**
 * Created by Kui on 12/9/2017.
 */

import java.util.ArrayList;

/*count: Number of recipes in result (Max 30)
        recipes: List of Recipe Parameters ->
        image_url: URL of the image
        source_url: Original Url of the recipe on the publisher's site
        f2f_url: Url of the recipe on Food2Fork.com
        title: Title of the recipe
        publisher: Name of the Publisher
        publisher_url: Base url of the publisher
        page: The page number that is being returned (To keep track of concurrent requests)
        social_rank: The Social Ranking of the Recipe (As determined by our Ranking Algorithm)*/

public class Recipe {

    private String recipe_title, thumbnailUrl,source_url,publisher,recipe_id;


    public Recipe(){}

    public Recipe(String recipe_title,String thumbnailUrl,String source_url,String publisher,String recipe_id){
        this.recipe_title = recipe_title;
        this.thumbnailUrl= thumbnailUrl;
        this.source_url = source_url;
        this.publisher = publisher;
        this.recipe_id = recipe_id;
    }

    public String getRecipe_title() {
        return recipe_title;
    }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }
}
