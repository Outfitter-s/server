package fr.paillaugue.outfitter.clothingItem;

import fr.paillaugue.outfitter.clothingItem.dto.ClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.dto.CreateClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.dto.UpdateClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.clothingItem.exceptions.ClothingItemNotFoundException;
import fr.paillaugue.outfitter.common.paging.PageDTO;
import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class ClothingItemService {

    private final ClothingItemRepository clothingItemRepository;
    private final UserRepository userRepository;

    public ClothingItemService(ClothingItemRepository clothingItemRepository, UserRepository userRepository) {
        this.clothingItemRepository = clothingItemRepository;
        this.userRepository = userRepository;
    }

    public PageDTO<ClothingItemDTO> getForUser(UserDTO user, Pageable pageable) {
        var page = clothingItemRepository.findByOwnerId(UUID.fromString(user.id()), pageable)
                .map(ClothingItemDTO::fromEntity);
        return PageDTO.fromPage(page);
    }

    @Transactional
    public ClothingItemDTO createClothingItem(UserDTO user, @Valid CreateClothingItemDTO createClothingItemDTO) {
        var managedUser = userRepository.findById(UUID.fromString(user.id())).orElseThrow(UserNotFoundException::new);
        var clothingItem = new ClothingItem(createClothingItemDTO.type(), createClothingItemDTO.color(), createClothingItemDTO.name(), createClothingItemDTO.description(), managedUser);
        var managedClothingItem = clothingItemRepository.save(clothingItem);
        return ClothingItemDTO.fromEntity(managedClothingItem);
    }

    public ClothingItemDTO getItem(UserDTO user, UUID id) {
        var clothingItem = getClothingItemForUser(user, id);
        return ClothingItemDTO.fromEntity(clothingItem);
    }

    @Transactional
    public void deleteItem(UserDTO user, UUID id) {
        var clothingItem = getClothingItemForUser(user, id);
        clothingItemRepository.delete(clothingItem);
    }

    @Transactional
    public ClothingItemDTO updateClothingItem(UserDTO user, UUID id, @Valid UpdateClothingItemDTO updateClothingItemDTO) {
        var clothingItem = getClothingItemForUser(user, id);
        updateClothingItemDTO.type().ifPresent(clothingItem::setType);
        updateClothingItemDTO.color().ifPresent(clothingItem::setColor);
        updateClothingItemDTO.name().ifPresent(clothingItem::setName);
        updateClothingItemDTO.description().ifPresent(clothingItem::setDescription);
        var managedClothingItem = clothingItemRepository.save(clothingItem);
        return ClothingItemDTO.fromEntity(managedClothingItem);
    }

    private ClothingItem getClothingItemForUser(UserDTO user, UUID id) {
        var clothingItem = clothingItemRepository.findById(id).orElseThrow(() -> new ClothingItemNotFoundException(id));
        if (!clothingItem.getOwner().getId().equals(UUID.fromString(user.id()))) {
            throw new ClothingItemNotFoundException(id);
        }
        return clothingItem;
    }

}
