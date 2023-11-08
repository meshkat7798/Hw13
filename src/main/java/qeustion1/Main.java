package qeustion1;


import qeustion1.mockdata.MockData;
import qeustion1.model.Person;
import qeustion1.model.PersonSummary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;
@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        List<Person> people = MockData.getPeople();
  //      System.out.println(people);

        //1
//        assert people != null;
//        List<Person> youngPeople =filterOverAge(people);
//        System.out.println(youngPeople);

        //2
//        List<Person> sortedUserNameList = sortedUserName(people);
//        System.out.println(sortedUserNameList);

        //3
//        List<Person> sortedAgeAndLastNameList = sortedAgeAndLastName(people);
//        System.out.println(sortedAgeAndLastNameList);

        //4
//        assert people != null;
//        List<String> ipv4PoepleList = getIpv4People(people);
//        System.out.println(ipv4PoepleList);

        //5
//        Map<String,Person> map = filterAndMapPeople(people);
//        for (Map.Entry<String, Person> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "   " + entry.getValue());
//       }

        //6
        System.out.println(correctDateOfBirthAndFilterMales(people));
    }


    //1
    public static List<Person> filterOverAge(List<Person> people) {
        return people.stream().filter(person -> person.getAge() < 50).collect(Collectors.toList());
    }


    //2
    public static List<Person> sortedUserName(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(Person::getUsername))
                .collect(Collectors.toList());
    }


    //3
    public static List<Person> sortedAgeAndLastName(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(Person::getLastName))
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());
    }


    //4
    public static List<String> getIpv4People(List<Person> people) {
        return people.stream()
                .map(Person::getIpv4).collect(Collectors.toList());
    }


    //5
    public static Map<String, Person> filterAndMapPeople(List<Person> people) {

        return people.stream()
                .sorted(Comparator.comparing(Person::getLastName))
                .filter(person -> person.getGender().equals("Female"))
                .filter(person -> person.getAge() > 40)
                .dropWhile(person -> person.getFirstName().startsWith("A"))
                .skip(5)
                .limit(100)
                .collect(Collectors.toMap
                        (
                                person -> person.getFirstName()+" "+person.getLastName(),
                                Function.identity(),(first , second) -> first
                        )
                );
    }


    //6
    public static OptionalDouble correctDateOfBirthAndFilterMales(List<Person> persons) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return persons.stream()
                .filter(person -> person.getGender().equals("Male"))
                .map(person -> {
                    try {
                        return new PersonSummary(person.getId(), person.getFirstName(), person.getLastName(),2023-( dateFormat.parse(person.getBirthDate()).getYear()+1900),  dateFormat.parse(person.getBirthDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(PersonSummary::getAge)
                .mapToDouble(Integer::doubleValue).average();
    }


}