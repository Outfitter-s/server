package fr.paillaugue.outfitter.outfit;

import fr.paillaugue.outfitter.clothingItem.dto.ClothingItemDTO;
import fr.paillaugue.outfitter.common.paging.PageDTO;
import fr.paillaugue.outfitter.common.security.SecurityUtil;
import fr.paillaugue.outfitter.outfit.dto.CreateOutfitDTO;
import fr.paillaugue.outfitter.outfit.dto.OutfitDTO;
import fr.paillaugue.outfitter.outfit.entities.OutfitStyle;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationService;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.weather.entities.WeatherType;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.List.copyOf;

@RestController
@RequestMapping("/outfits")
@Validated
public class OutfitController {

    private final SecurityUtil securityUtil;
    private final OutfitService outfitService;
    private final OutfitGenerationService outfitGenerationService;

    public OutfitController(SecurityUtil securityUtil, OutfitService outfitService, OutfitGenerationService outfitGenerationService) {
        this.securityUtil = securityUtil;
        this.outfitService = outfitService;
        this.outfitGenerationService = outfitGenerationService;
    }

    @GetMapping()
    public PageDTO<OutfitDTO> getMyOutfits(Pageable pageable) {
        var user = securityUtil.getCurrentUser();
        return outfitService.getForUser(user, pageable);
    }

    @PostMapping
    public OutfitDTO createOutfit(
            @RequestBody @Valid CreateOutfitDTO createOutfitDTO
    ) {
        var user = securityUtil.getCurrentUser();
        return outfitService.createForUser(user, createOutfitDTO.clothingItemIds());
    }

    @GetMapping("/{id}")
    public OutfitDTO getOutfit(
            @PathVariable UUID id
    ) {
        var user = securityUtil.getCurrentUser();
        return outfitService.getForUserById(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOutfit(
        @PathVariable UUID id
    ) {
        var user = securityUtil.getCurrentUser();
        outfitService.deleteForUserById(user, id);
    }

    @GetMapping("/generate")
    public Collection<Collection<ClothingItemDTO>> generateOutfit(
            Optional<OutfitStyle> style,
            WeatherType weather,
            Optional<Integer> amount
    ) {
        var user = securityUtil.getCurrentUser();
        var scoredOutfits = outfitGenerationService.generateOutfitsForUser(user, weather, style.orElse(OutfitStyle.DEFAULT), amount.orElse(5));
        var outfits = scoredOutfits.stream().map(ClothingItemDTO::fromEntities).toList();
        return copyOf(outfits);
    }
}
