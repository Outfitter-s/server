package fr.paillaugue.outfitter.clothingItem.entities;

import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class    ClothingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private ClothingItemType type;

    @NotNull
    private ClothingItemColor color;

    @NotBlank
    private String name;

    @Size(max=255)
    @Nullable
    private String description;

    @Nullable
    @Formula("(select max(o.created_at) from outfit o join outfit_clothing_items oci on o.id = oci.outfit_id where oci.clothing_items_id = id)")
    private ZonedDateTime lastWornAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @ManyToOne
    private User owner;

    public ClothingItem() {}

    public ClothingItem(ClothingItemType type, ClothingItemColor color, String name, @Nullable String description, User owner) {
        this.type = type;
        this.color = color;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public ClothingItemType getType() {
        return type;
    }

    public void setType(ClothingItemType type) {
        this.type = type;
    }

    public ClothingItemColor getColor() {
        return color;
    }

    public void setColor(ClothingItemColor color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public ZonedDateTime getLastWornAt() {
        return lastWornAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
