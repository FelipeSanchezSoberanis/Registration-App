package com.fiuady.registrationApp.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomUtils<T> {
    public static <T> Set<T> getRandomElementsFromSet(Set<T> elements, int noElements) {
        List<T> elementsList = elements.stream().collect(Collectors.toList());
        Set<T> randomElements = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < noElements; i++) {
            int rand = random.nextInt(elementsList.size());

            randomElements.add(elementsList.get(rand));
            elementsList.remove(rand);
        }

        return randomElements;
    }
}
