package fr.paillaugue.outfitter.clothingItem;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ClothingItemRepository extends JpaRepository<ClothingItem, UUID> {
    Page<ClothingItem> findByOwnerId(UUID userId, Pageable pageable);
    Collection<ClothingItem> findAllByOwnerId(UUID userId);
}