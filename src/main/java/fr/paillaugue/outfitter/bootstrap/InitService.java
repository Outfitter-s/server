package fr.paillaugue.outfitter.bootstrap;

import fr.paillaugue.outfitter.clothingItem.ClothingItemRepository;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.OutfitRepository;
import fr.paillaugue.outfitter.outfit.entities.Outfit;
import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InitService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClothingItemRepository clothingItemRepository;
    private final OutfitRepository outfitRepository;

    public final int NB_USERS = 3;
    public final int NB_CLOTHING_ITEMS = 6;
    public final int NB_OUTFITS = 2;

    private User user1;
    private User user2;
    private User user3;

    public ClothingItem clothingItem1;
    public ClothingItem clothingItem2;
    public ClothingItem clothingItem3;
    public ClothingItem clothingItem4;
    public ClothingItem clothingItem5;
    public ClothingItem clothingItem6;
    public Outfit outfit1;
    public Outfit outfit2;

    public InitService(PasswordEncoder passwordEncoder, UserRepository userRepository, ClothingItemRepository clothingItemRepository, OutfitRepository outfitRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.clothingItemRepository = clothingItemRepository;
        this.outfitRepository = outfitRepository;
    }

    @Transactional
    public void doInitUsers() {
        if(this.userRepository.count() >= NB_USERS) {
            this.user1 = this.userRepository.findByUsername("Angus").orElseThrow();
            this.user2 = this.userRepository.findByUsername("Yann").orElseThrow();
            this.user3 = this.userRepository.findByUsername("Paqui").orElseThrow();
        }else {
            var rawPassword = "password";
            var hashedPassword = passwordEncoder.encode(rawPassword);

            this.user1 = new User("Angus", "angus@paillaugue.fr", hashedPassword);
            this.userRepository.save(this.user1);

            this.user2 = new User("Yann", "yann@lacaze.fr", hashedPassword);
            this.userRepository.save(this.user2);

            this.user3 = new User("Paqui", "paqui@esther.fr", hashedPassword);
            this.userRepository.save(this.user3);
        }
    }

    @Transactional
    public void doInitClothingItems() {
        if(this.clothingItemRepository.count() >= NB_CLOTHING_ITEMS) {
            this.clothingItem1 = this.clothingItemRepository.findByNameAndOwner("Red Shirt", this.user1);
            this.clothingItem2 = this.clothingItemRepository.findByNameAndOwner("Blue Jeans", this.user1);
            this.clothingItem3 = this.clothingItemRepository.findByNameAndOwner("Black Shoes", this.user1);
            this.clothingItem4 = this.clothingItemRepository.findByNameAndOwner("Green Shirt", this.user2);
            this.clothingItem5 = this.clothingItemRepository.findByNameAndOwner("Black Pants", this.user2);
            this.clothingItem6 = this.clothingItemRepository.findByNameAndOwner("White Shoes", this.user3);
        } else {
            this.clothingItem1 = new ClothingItem(ClothingItemType.SHIRT, ClothingItemColor.RED, "Red Shirt", "A bright red shirt", this.user1);
            this.clothingItemRepository.save(this.clothingItem1);

            this.clothingItem2 = new ClothingItem(ClothingItemType.PANTS, ClothingItemColor.BLUE, "Blue Jeans", "Classic blue jeans", this.user1);
            this.clothingItemRepository.save(this.clothingItem2);

            this.clothingItem3 = new ClothingItem(ClothingItemType.SHOES, ClothingItemColor.BLACK, "Black Shoes", "Elegant black shoes", this.user1);
            this.clothingItemRepository.save(this.clothingItem3);

            this.clothingItem4 = new ClothingItem(ClothingItemType.SHIRT, ClothingItemColor.GREEN, "Green Shirt", "A bright green shirt", this.user2);
            this.clothingItemRepository.save(this.clothingItem4);

            this.clothingItem5 = new ClothingItem(ClothingItemType.PANTS, ClothingItemColor.BLACK, "Black Pants", "Classic black pants", this.user2);
            this.clothingItemRepository.save(this.clothingItem5);

            this.clothingItem6 = new ClothingItem(ClothingItemType.SHOES, ClothingItemColor.WHITE, "White Shoes", "Elegant white shoes", this.user3);
            this.clothingItemRepository.save(this.clothingItem6);
        }

    }

    @Transactional
    public void doInitOutfits() {
        // Refetch users to ensure they're attached to the current session
        this.user1 = this.userRepository.findById(this.user1.getId()).orElseThrow();
        this.user2 = this.userRepository.findById(this.user2.getId()).orElseThrow();

        if(this.user1.getOutfits() != null && !this.user1.getOutfits().isEmpty() && this.user2.getOutfits() != null && !this.user2.getOutfits().isEmpty()) {
            this.outfit1 = this.user1.getOutfits().stream().filter(outfit -> outfit.getClothingItems().contains(this.clothingItem1)).findFirst().orElseThrow();
            this.outfit2 = this.user2.getOutfits().stream().filter(outfit -> outfit.getClothingItems().contains(this.clothingItem4)).findFirst().orElseThrow();
        } else {
            this.outfit1 = new Outfit(this.user1);
            this.outfit1.setClothingItems(List.of(
                    this.clothingItem1,
                    this.clothingItem2,
                    this.clothingItem3
            ));
            this.user1.addOutfit(this.outfit1);
            this.outfitRepository.save(this.outfit1);

            this.outfit2 = new Outfit(this.user2);
            this.outfit2.setClothingItems(List.of(
                    this.clothingItem4,
                    this.clothingItem5
            ));
            this.user2.addOutfit(this.outfit2);
            this.outfitRepository.save(this.outfit2);
        }
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public User getUser3() {
        return user3;
    }

    public ClothingItem getClothingItem1() {
        return clothingItem1;
    }

    public ClothingItem getClothingItem2() {
        return clothingItem2;
    }

    public ClothingItem getClothingItem3() {
        return clothingItem3;
    }

    public ClothingItem getClothingItem4() {
        return clothingItem4;
    }

    public ClothingItem getClothingItem5() {
        return clothingItem5;
    }

    public ClothingItem getClothingItem6() {
        return clothingItem6;
    }

    public Outfit getOutfit1() {
        return outfit1;
    }

    public Outfit getOutfit2() {
        return outfit2;
    }

}
