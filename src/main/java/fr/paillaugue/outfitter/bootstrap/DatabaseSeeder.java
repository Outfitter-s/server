package fr.paillaugue.outfitter.bootstrap;

import fr.paillaugue.outfitter.clothingItem.ClothingItemRepository;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Component
@Profile("develop")
public class DatabaseSeeder{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClothingItemRepository clothingItemRepository;

    public final int NB_USERS = 1;
    public final int NB_CLOTHING_ITEMS = 1;

    private User user1;
    private ClothingItem clothingItem1;

    public DatabaseSeeder(UserRepository userRepository, ClothingItemRepository clothingItemRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clothingItemRepository = clothingItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        try {
            doInitUsers();
            doInitClothingItems();
        } catch (RuntimeException e) {
            IO.println("Bootstrapping failed: " + e);
        }
    }

    public void doInitUsers() {
        if(this.userRepository.count() >= NB_USERS) {
            return;
        }
        var rawPassword = "password";
        var hashedPassword = passwordEncoder.encode(rawPassword);
        this.user1 = new User("Angus", "angus@paillaugue.fr", hashedPassword);
        this.user1.setId(UUID.fromString("8cfd6a2b-40a3-4ddc-89b9-9a708a0921fa"));
        this.userRepository.save(this.user1);
    }

    public void doInitClothingItems() {
        if(this.clothingItemRepository.count() >= NB_CLOTHING_ITEMS) {
            return;
        }
        this.clothingItem1 = new ClothingItem(ClothingItemType.SHIRT, ClothingItemColor.RED, "Red Shirt", "A bright red shirt", this.user1);
        this.clothingItem1.setId(UUID.fromString("9e0ed1f7-f2a8-41c6-a2d6-3e339f19fb29"));
        this.clothingItemRepository.save(this.clothingItem1);
    }
}
