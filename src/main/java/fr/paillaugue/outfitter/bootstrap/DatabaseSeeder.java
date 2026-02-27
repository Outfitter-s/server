package fr.paillaugue.outfitter.bootstrap;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("develop")
public class DatabaseSeeder{

    private final InitService initService;

    public DatabaseSeeder(InitService initService) {
        this.initService = initService;
    }

    @PostConstruct
    public void init() {
        try {
            initService.doInitUsers();
            initService.doInitClothingItems();
            initService.doInitOutfits();
        } catch (RuntimeException e) {
            System.err.println("Bootstrapping failed: " + e);
        }
    }

    public InitService getInitService() {
        return initService;
    }
}
