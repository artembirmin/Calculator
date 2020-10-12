package com.example.domain.calculatorslist;

import com.example.data.repositories.CalculatorsListRepositoryImpl;
import com.example.models.Calculator;

import java.util.Collection;

public class CalculatorsListInteractorImpl implements CalculatorsListInteractor {

    CalculatorsListRepository calculatorsListRepository;

    public CalculatorsListInteractorImpl() {
        calculatorsListRepository = new CalculatorsListRepositoryImpl();
    }

    @Override
    public Calculator getCalculator(int position) {
        return calculatorsListRepository.getCalculator(position);
    }

    @Override
    public void deleteAll() {
        calculatorsListRepository.deleteAll();
    }

    @Override
    public Collection<Calculator> getCalculators() {
        return calculatorsListRepository.getCalculators();
    }
}
