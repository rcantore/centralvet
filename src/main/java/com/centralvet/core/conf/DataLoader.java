package com.centralvet.core.conf;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.Pet;
import com.centralvet.core.entities.repositories.ClinicRepository;
import com.centralvet.core.entities.repositories.CustomerRepository;
import com.centralvet.core.entities.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataLoader {

    private ClinicRepository clinicRepository;

    private CustomerRepository customerRepository;

    private PetRepository petRepository;

    @Autowired
    public DataLoader(ClinicRepository clinicRepository, CustomerRepository customerRepository, PetRepository petRepository) {
        this.clinicRepository = clinicRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public void loadData() {
        loadClinics();
        loadCustomers();
        loadPets();
    }

    public void loadClinics() {
        List<Clinic> clinics = (List<Clinic>) clinicRepository.findAll();

        if (clinics.size() == 0) {
            Clinic clinic = new Clinic();
            clinic.setAddress("Fake address 123");
            clinic.setName("Pet's Ahoy");
            clinic.setId(1L);

            clinicRepository.save(clinic);

            clinic = new Clinic();
            clinic.setAddress("evergreen terrace");
            clinic.setName("Wizard of owls");
            clinic.setId(2l);

            clinicRepository.save(clinic);
        }

    }

    public void loadCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        if(customers.size() == 0) {
            addCustomerToClinic("Willowtree", "Anibal Smith", 1L, 1L);
            addCustomerToClinic("Sleepy Hollow", "Jane Depp", 2L, 1L);
            addCustomerToClinic("SFo FOO", "Christoph Polopolopus", 3L, 1L);
            addCustomerToClinic("Arkon 2", "Meridith Brainiac", 4L, 1L);
            addCustomerToClinic("Elm Street", "Lauren Doe", 5L, 1L);

            addCustomerToClinic("Hellm's deep", "Meredith Brooks", 6L, 2L);
            addCustomerToClinic("OneH Wonder", "Jennifer Gardner", 7L, 2L);
            addCustomerToClinic("Highlands", "Christopher Lambert", 8L, 2L);
            addCustomerToClinic("Butterfly Wings road", "Billy Corgan", 9L, 2L);
            addCustomerToClinic("Henrand Blvd.", "Lou Vega", 10L, 2L);
        }

    }

    private void addCustomerToClinic(String address, String name, long customerId, long clinicId) {
        Optional<Clinic> clinicOptional = clinicRepository.findById(clinicId);

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName(name);
        customer.setId(customerId);

        Clinic clinic = clinicOptional.orElse(new Clinic());
        customer.setClinic(clinic);

        Customer savedCustomer = customerRepository.save(customer);
        clinic.getCustomers().add(savedCustomer);
        clinicRepository.save(clinic);
    }

    public void loadPets() {
        List<Pet> pets = (List<Pet>) petRepository.findAll();
        if(pets.size() == 0) {
            addNewPetToCustomer("Chihuahua", "Pepe", 1L);
            addNewPetToCustomer("Siamese", "Gordian", 1L);

            addNewPetToCustomer("Halfblood", "Prince", 2L);
            addNewPetToCustomer("Russian Hermit", "Volshoi", 2L);

            addNewPetToCustomer("Chow chow", "Aynose", 3L);
            addNewPetToCustomer("Nigerian Unguete", "Ungue", 3L);

            addNewPetToCustomer("Cuskit Dog", "Chimuelo", 4L);
            addNewPetToCustomer("Galgo", "Shemp", 4L);

            addNewPetToCustomer("Siamese", "Larry", 5L);
            addNewPetToCustomer("Egipcian Sun", "Moe", 5L);

            addNewPetToCustomer("Poddle", "Henriette", 6L);
            addNewPetToCustomer("Poddle", "Madame Antoine", 6L);

            addNewPetToCustomer("iguana", "fix", 7L);
            addNewPetToCustomer("python", "IA learner", 7L);

            addNewPetToCustomer("european cat", "July", 8L);
            addNewPetToCustomer("european cat", "Simon", 8L);

            addNewPetToCustomer("Boxer", "Trumper", 9L);
            addNewPetToCustomer("Pitbull", "Bitter", 9L);

            addNewPetToCustomer("Doberman", "Hans", 10L);
            addNewPetToCustomer("German Sheppards", "Moleman", 10L);

        }

    }

    private Customer addNewPetToCustomer(String breed, String name, long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        Pet pet = new Pet();
        pet.setBreed(breed);
        pet.setName(name);

        Customer customer = optionalCustomer.orElse(new Customer());
        pet.setCustomer(customer);

        customer.getPets().add(pet);
        customerRepository.save(customer);
        return customer;
    }
}
