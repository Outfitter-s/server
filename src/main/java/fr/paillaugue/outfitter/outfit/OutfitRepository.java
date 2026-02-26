package fr.paillaugue.outfitter.outfit;

import fr.paillaugue.outfitter.outfit.entities.Outfit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, UUID> {
    Page<Outfit> findByOwnerId(UUID userId, Pageable pageable);
}