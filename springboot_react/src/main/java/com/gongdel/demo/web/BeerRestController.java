package com.gongdel.demo.web;

import com.gongdel.demo.domain.beer.Beer;
import com.gongdel.demo.domain.beer.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BeerRestController {

    private final BeerRepository beerRepository;

    @GetMapping("/good-beers")
    @CrossOrigin(origins = "http://localhost:3000")
    public Collection<Beer> goodBears() {
        return beerRepository.findAll()
                .stream()
                .filter(this :: isGreat)
                .collect(Collectors.toList());
    }

    private boolean isGreat(Beer beer) {
        return !beer.getName().equals("Budweiser") &&
                !beer.getName().equals("Coors Light") &&
                !beer.getName().equals("PBR");
    }


}

