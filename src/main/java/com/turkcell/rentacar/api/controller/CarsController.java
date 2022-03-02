package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.requests.dtos.CarByIdDto;
import com.turkcell.rentacar.business.requests.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cars")

public class CarsController {
    private CarService carService;

    public CarsController(CarService carService) {

        this.carService = carService;
    }

    @GetMapping("/getall")
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarRequest createcarRequest) throws BusinessException {

        return this.carService.add(createcarRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<CarByIdDto> getById(@RequestParam(required = true) int carId) {
        return this.carService.getById(carId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCarRequest updatecarRequest) throws BusinessException {
        return this.carService.update(updatecarRequest);
    }

    @PostMapping("/deletebyid")
    public Result deleteById(int carId) {

        return this.carService.deleteById(carId);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        return this.carService.getAllPaged(pageNo, pageSize);
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {
        return this.carService.getAllSorted(ascOrDesc);
    }

    @GetMapping("/sortAllByDailyPrice")
    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
        return this.carService.getByDailyPriceIsLessThanEqual(dailyPrice);
    }
    
    @GetMapping("/sortByModelYear")
    public DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear) {
        return this.carService.getByModelYearIsLessThanEqual(modelYear);
    }
    

}
