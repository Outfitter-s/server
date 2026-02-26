package fr.paillaugue.outfitter.outfit;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.entities.Outfit;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class OutfitTests {

    private Outfit outfit;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.outfit = new Outfit();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Outfit should be invalid if it has no clothing items")
    public void outfitShouldBeInvalidWithoutClothingItems() {
        var violations = this.validator.validate(this.outfit);
        assert !violations.isEmpty();
    }

    @Test
    @DisplayName("Outfit should be valid if it has at least one clothing item")
    public void outfitShouldBeValidWithClothingItems() {
        var clothingItem = new ClothingItem();
        clothingItem.setName("T-Shirt");
        clothingItem.setType(ClothingItemType.SHIRT);
        this.outfit.setClothingItems(new ArrayList<>());
        this.outfit.getClothingItems().add(clothingItem);
        var violations = this.validator.validate(this.outfit);
        assert violations.isEmpty();
    }

    @Test
    @DisplayName("Outfit should have an owner associated when creating it using the constructor with owner")
    public void outfitShouldHaveOwnerWhenCreatedWithConstructor() {
        var owner = new User();
        var outfitWithOwner = new Outfit(owner);
        assert outfitWithOwner.getOwner() == owner;
    }
}
