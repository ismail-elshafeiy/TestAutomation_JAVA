package examples.api.rest.TAU.examples;


import java.io.File;

import io.restassured.path.json.JsonPath;

public class SyntaxForJsonArray {

    public static void main(String[] args) {

        File jsonArrayFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\jsonArraySyntaxDemo.json");
        JsonPath jsonPath = JsonPath.from(jsonArrayFile);

        /*
         * To get a specific field value of an indexed element [0] will give you first
         * element in an array and using dot operator we are retrieving value of
         * firstName
         */
        System.out.println("First name is first employee :" + jsonPath.getString("[0].first_name"));

        // To get whole indexed element
        System.out.println("All details of first employee : " + jsonPath.getJsonObject("[0]"));

        /*
         * To get first names of all employees Since it is a JSON array and we are not
         * specifying index then it will pick firstName from each element and return as
         * a list.
         */
        System.out.println("First name of all employees" + jsonPath.getList("first_name"));

        /* To get all first name of all females only
         * If we want to filter records based on conditions we can use find or findAll expression.
         * findAll will iterate through each element in array and match condition. "it" represent current element.
         * For each element it will check if gender is "female". If yes then take firstName of current element.
         * findAl returns a List. */
        System.out.println("First name of all female employees : " + jsonPath.getList("findAll{it.gender == 'Female'}.first_name"));
        System.out.println("First name of all female employees : " + jsonPath.getList("findAll{it -> it.gender == 'Female'}.first_name"));

        /* To get first female name
         * If we want to get firstName of first female employee we can use find expression.*/
        System.out.println("First name of first female employee : " + jsonPath.getString("find{it.gender == 'Female'}.first_name"));

        /*
         * We can also use relational operator like first name of all employees whose id is 5 or more
         */
        System.out.println("First name of all employees whose id is 5 or more : " + jsonPath.getList("findAll{it.id >= 5}.first_name"));

        // we can use use and (&) operator - logical
        System.out.println("First name of all employees whose id is 5 or more but less than 8 : " + jsonPath.getList("findAll{it.id >= 5 & it.id < 8}.first_name"));

        // We can also use or (|) operator
        System.out.println("First name of all employees whose id is greater than 9 or gender is female : " + jsonPath.getList("findAll{it.id >= 9 | it.gender == 'Female'}.first_name"));

        // We can get size of array using size() or size
        System.out.println("Total number of employees : " + jsonPath.getString("size()"));


    }


}

