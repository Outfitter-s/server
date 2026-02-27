package fr.paillaugue.outfitter.user.entities;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.entities.Outfit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(
    name = "\"user\"",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"username"}
        ),
        @UniqueConstraint(
            columnNames = {"email"}
        )
    }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String passwordHash;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "owner")
    private Collection<ClothingItem> clothingItems;

    @OneToMany(mappedBy = "owner")
    private Collection<Outfit> outfits;

    public User() {
    }

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addOutfit(Outfit savedOutfit) {
        if(this.outfits == null) {
            this.outfits = new ArrayList<>();
        }
        this.outfits.add(savedOutfit);
    }

    public Collection<Outfit> getOutfits() {
        return outfits;
    }

    public Collection<ClothingItem> getClothingItems() {
        return clothingItems;
    }
}
