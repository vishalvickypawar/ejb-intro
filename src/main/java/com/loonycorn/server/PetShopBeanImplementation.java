package com.loonycorn.server;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Singleton
@Startup
//@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class PetShopBeanImplementation implements PetShopBeanInterface{

    private final Map<String, List<String>> petTypeMap = new HashMap<String, List<String>>();

    @PostConstruct
    public void initialize() {

        List<String> dogBreeds = new ArrayList<String>();
        dogBreeds.add("Golden Retriever");
        dogBreeds.add("Labrador");
        dogBreeds.add("German Shepherd");
        dogBreeds.add("Bulldog");
        dogBreeds.add("Beagle");
        dogBreeds.add("Indie");

        petTypeMap.put("Dog", dogBreeds);
    }

    @Lock(LockType.READ)
    @Override
    public List<String> getBreeds(String petType) {
        return petTypeMap.get(petType);
    }

    @Lock(LockType.WRITE)
    @Override
    public void setBreeds(String petType, List<String> breeds) {
        petTypeMap.put(petType, breeds);
    }

}
