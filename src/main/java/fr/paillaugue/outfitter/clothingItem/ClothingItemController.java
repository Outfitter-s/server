package fr.paillaugue.outfitter.clothingItem;

import fr.paillaugue.outfitter.clothingItem.dto.ClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.dto.CreateClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.dto.UpdateClothingItemDTO;
import fr.paillaugue.outfitter.common.paging.PageDTO;
import fr.paillaugue.outfitter.common.security.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clothingItems")
@Validated
public class ClothingItemController {

    private final SecurityUtil securityUtil;
    private final ClothingItemService clothingItemService;

    public ClothingItemController(SecurityUtil securityUtil, ClothingItemService clothingItemService) {
        this.securityUtil = securityUtil;
        this.clothingItemService = clothingItemService;
    }

    @GetMapping
    public PageDTO<ClothingItemDTO> getClothingItems(
        Pageable pageable
    ) {
        var user = securityUtil.getCurrentUser();
        return clothingItemService.getForUser(user, pageable);
    }

    @PostMapping
    public ClothingItemDTO createClothingItem(
        @RequestBody @Valid CreateClothingItemDTO createClothingItemDTO
    ) {
        var user = securityUtil.getCurrentUser();
        return clothingItemService.createClothingItem(user, createClothingItemDTO);
    }

    @GetMapping("/{id}")
    public ClothingItemDTO getClothingSingleItem(
        @PathVariable UUID id
    ) {
        var user = securityUtil.getCurrentUser();
        return clothingItemService.getItem(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteClothingItem(
        @PathVariable UUID id
    ) {
        var user = securityUtil.getCurrentUser();
        clothingItemService.deleteItem(user, id);
    }

    @PatchMapping("/{id}")
    public ClothingItemDTO updateClothingItem(
        @PathVariable UUID id,
        @RequestBody @Valid UpdateClothingItemDTO updateClothingItemDTO
    ){
        var user = securityUtil.getCurrentUser();
        return clothingItemService.updateClothingItem(user, id, updateClothingItemDTO);
    }
}
