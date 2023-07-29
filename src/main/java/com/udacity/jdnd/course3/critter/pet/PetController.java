package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private PetService petService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        Long cId = petDTO.getOwnerId();
        if (petDTO.getOwnerId() > 0) {
            pet.setCustomer(customerService.findCustomerById(cId));
        }
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        petDTO.setId(petService.create(pet,cId));
        return convertPetToPetDTO(pet);

//        Pet pet = convertPetDTOToPet(petDTO);
//        petDTO.setId(petService.create(pet,pet.getCustomer().getId()));
//        return petDTO;
//        throw new UnsupportedOperationException();
    }

    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setId(pet.getId());
        if (pet.getCustomer() != null){
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setId(petDTO.getId());
        pet.setCustomer(customerService.findCustomerById(petDTO.getOwnerId()));

        return pet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet p= petService.getPetbyId(petId);
        PetDTO petDTO = convertPetToPetDTO(p);
        return petDTO;
//        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getAllPets();
        List<PetDTO> petDTOList = new ArrayList<>();
        if (petList!=null){
            for (Pet p: petList){
                petDTOList.add(convertPetToPetDTO(p));
            }
        }
        return petDTOList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.GetPetsByCustomer(ownerId);
        List<PetDTO> petDTOList = new ArrayList<>();
        if (petList!=null){
            for (Pet p: petList){
                petDTOList.add(convertPetToPetDTO(p));
            }
        }
        return petDTOList;
//        throw new UnsupportedOperationException();
    }
}
