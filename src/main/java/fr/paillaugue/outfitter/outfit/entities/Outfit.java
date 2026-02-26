package fr.paillaugue.outfitter.outfit.entities;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Outfit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER)
    @Size(min = 1)
    @NotNull
    private Collection<ClothingItem> clothingItems;

    @ManyToOne
    private User owner;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    public Outfit(){}
    public Outfit(User owner){
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public Collection<ClothingItem> getClothingItems() {
        return clothingItems;
    }

    public void setClothingItems(Collection<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    public User getOwner() {
        return owner;
    }
}
