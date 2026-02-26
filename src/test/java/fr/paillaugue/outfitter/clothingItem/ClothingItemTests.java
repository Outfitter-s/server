package fr.paillaugue.outfitter.clothingItem;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClothingItemTests {

    private ClothingItem clothingItem;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.clothingItem = new ClothingItem(
            ClothingItemType.SHIRT,
            ClothingItemColor.RED,
            "Red Shirt",
            "A bright red shirt",
            null
        );
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("ClothingItem should be valid with correct fields")
    public void testValidClothingItem() {
        var violations = validator.validate(clothingItem);
        assert (violations.isEmpty());
    }

    @Test
    @DisplayName("ClothingItem should be invalid with blank name")
    public void testBlankName() {
        clothingItem.setName("");
        var violations = validator.validate(clothingItem);
        assert (!violations.isEmpty());
    }

    @Test
    @DisplayName("ClothingItem should be valid with blank description")
    public void testBlankDescription() {
        clothingItem.setDescription("");
        var violations = validator.validate(clothingItem);
        assert (violations.isEmpty());
    }

    @Test
    @DisplayName("ClothingItem should be valid with null description")
    public void testNullDescription() {
        clothingItem.setDescription(null);
        var violations = validator.validate(clothingItem);
        assert (violations.isEmpty());
    }

    @Test
    @DisplayName("ClothingItem should be invalid with null type")
    public void testNullType() {
        clothingItem.setType(null);
        var violations = validator.validate(clothingItem);
        assert (!violations.isEmpty());
    }

    @Test
    @DisplayName("ClothingItem should be invalid with null color")
    public void testNullColor() {
        clothingItem.setColor(null);
        var violations = validator.validate(clothingItem);
        assert (!violations.isEmpty());
    }

}
