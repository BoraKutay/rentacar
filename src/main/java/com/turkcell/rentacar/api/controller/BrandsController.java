package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/brands")

public class BrandsController {

    private BrandService brandService;

    public BrandsController(BrandService brandService) {
        super();
        this.brandService = brandService;
    }

    @GetMapping("/getall")
    public DataResult<List<BrandListDto>> getAll() {
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {

        return this.brandService.add(createBrandRequest);
    }

    @GetMapping("/getbyid")
    public DataResult<BrandByIdDto> getById(@RequestParam(required = true) int brandId) {
        return this.brandService.getById(brandId);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
        return this.brandService.update(updateBrandRequest);
    }

    @PostMapping("/deletebyid")
    public Result deleteById(int brandId) {

        return this.brandService.deleteById(brandId);
    }


}
