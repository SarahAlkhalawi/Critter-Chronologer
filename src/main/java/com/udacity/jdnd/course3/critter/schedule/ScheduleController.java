package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        scheduleDTO.setId(scheduleService.createSchedule(schedule));
        return scheduleDTO;
//        throw new UnsupportedOperationException();
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setId(schedule.getId());

        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();
        List<Employee> employeeList = schedule.getEmployees();
        List<Pet> petList =schedule.getPets();

        if (employeeList!=null){
            for (Employee e: employeeList){
                employeeIds.add(e.getId());
            }
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        if (petList!=null){
            for (Pet pet: petList){
                petIds.add(pet.getId());
            }
        }
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setId(scheduleDTO.getId());

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();

        if (employeeIds!=null){
            for (Long id: employeeIds){
                employeeList.add(employeeService.getById(id));
            }
        }
        schedule.setEmployees(employeeList);

        if (petIds!=null){
            for (Long id: petIds){
                petList.add(petService.getPetbyId(id));
            }
        }
        schedule.setPets(petList);

        return schedule;
    }


    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        if (scheduleList!=null){
            for (Schedule s: scheduleList){
                scheduleDTOList.add(convertScheduleToScheduleDTO(s));
            }
        }
        return scheduleDTOList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.FindScheduleByPet(petId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        if (scheduleList!=null){
            for (Schedule s: scheduleList){
                scheduleDTOList.add(convertScheduleToScheduleDTO(s));
            }
        }
        return scheduleDTOList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.FindScheduleByEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        if (scheduleList!=null){
            for (Schedule s: scheduleList){
                scheduleDTOList.add(convertScheduleToScheduleDTO(s));
            }
        }
        return scheduleDTOList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.findScheduleByCustomer(customerId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        if (scheduleList!=null){
            for (Schedule s: scheduleList){
                scheduleDTOList.add(convertScheduleToScheduleDTO(s));
            }
        }
        return scheduleDTOList;
//        throw new UnsupportedOperationException();
    }
}
