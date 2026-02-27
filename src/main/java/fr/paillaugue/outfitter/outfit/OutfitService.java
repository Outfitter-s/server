package fr.paillaugue.outfitter.outfit;

import fr.paillaugue.outfitter.clothingItem.ClothingItemRepository;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.clothingItem.exceptions.ClothingItemNotFoundException;
import fr.paillaugue.outfitter.common.paging.PageDTO;
import fr.paillaugue.outfitter.outfit.dto.OutfitDTO;
import fr.paillaugue.outfitter.outfit.entities.Outfit;
import fr.paillaugue.outfitter.outfit.exceptions.OutfitNotFoundException;
import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final UserRepository userRepository;
    private final ClothingItemRepository clothingItemRepository;

    public OutfitService(OutfitRepository outfitRepository, UserRepository userRepository, ClothingItemRepository clothingItemRepository) {
        this.outfitRepository = outfitRepository;
        this.userRepository = userRepository;
        this.clothingItemRepository = clothingItemRepository;
    }

    public PageDTO<OutfitDTO> getForUser(UserDTO user, Pageable pageable) {
        var page = outfitRepository.findByOwnerId(UUID.fromString(user.id()), pageable)
                .map(OutfitDTO::fromEntity);
        return PageDTO.fromPage(page);
    }

    @Transactional
    public OutfitDTO createForUser(UserDTO user, @Size(min = 1) Collection<UUID> clothingItemIds) {
        var managedUser = userRepository.findById(UUID.fromString(user.id()))
                .orElseThrow(() -> new UserNotFoundException(user.id()));

        Collection<ClothingItem> clothingItems = clothingItemRepository.findAllById(clothingItemIds);

        Set<UUID> foundIds = clothingItems.stream()
                .map(ClothingItem::getId)
                .collect(Collectors.toSet());

        Set<UUID> missingIds = clothingItemIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new ClothingItemNotFoundException(missingIds);
        }

        var outfit = new Outfit(managedUser);
        outfit.setClothingItems(clothingItems);
        var savedOutfit = outfitRepository.save(outfit);
        managedUser.addOutfit(savedOutfit);
        return OutfitDTO.fromEntity(savedOutfit);
    }

    public OutfitDTO getForUserById(UserDTO user, UUID outfitId) {
        var outfit = getOutfitForUser(user, outfitId);
        return OutfitDTO.fromEntity(outfit);
    }

    @Transactional
    public void deleteForUserById(UserDTO user, UUID outfitId) {
        var outfit = getOutfitForUser(user, outfitId);
        outfitRepository.delete(outfit);
    }

    private Outfit getOutfitForUser(UserDTO user, UUID id) {
        var outfit = outfitRepository.findById(id).orElseThrow(() -> new OutfitNotFoundException(id));
        if (!outfit.getOwner().getId().equals(UUID.fromString(user.id()))) {
            throw new OutfitNotFoundException(id);
        }
        return outfit;
    }
}
