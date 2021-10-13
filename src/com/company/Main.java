package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Long minorPerson = persons.stream()              // 1. Несовершеннолетние
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println(minorPerson);

        List<String> conscript = persons.stream()        // 2. Призывники
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println(conscript);


        List<String> workable = persons.stream()         // 3. Работоспособные
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> (person.getSex().equals(Sex.MAN)
                        && person.getAge() >= 18 && person.getAge() <= 65)
                        || (person.getSex().equals(Sex.WOMAN)
                        && person.getAge() >= 18 && person.getAge() <= 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::toString)
                .collect(Collectors.toList());
        System.out.println(workable);
    }
}
