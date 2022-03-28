package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
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

    @GetMapping("/getAll")
    public DataResult<List<BrandListDto>> getAll() {
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {

        return this.brandService.add(createBrandRequest);
    }

    @GetMapping("/getById")
    public DataResult<BrandByIdDto> getById(@RequestParam(required = true) int brandId) throws BusinessException {
        return this.brandService.getById(brandId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
        return this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestBody DeleteBrandRequest deleteBrandRequest) throws BusinessException {

        return this.brandService.deleteById(deleteBrandRequest);
    }


}
