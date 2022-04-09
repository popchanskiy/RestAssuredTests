package api.tests.users;

import api.congig.*;
import api.entityes.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

@Tag("API")
public class ListUsersTest {
    @Test
    public void checkGet20OK() {
        Specification.setSpecification(Specification.requestSpec("https://reqres.in"));
        get("/api/users?page=2")
                .then().
                assertThat().statusCode(200);
    }

    @Test
    public void validateJSONScheme() {
        Specification.setSpecification(Specification.requestSpec("https://reqres.in"), Specification.responseSpecOK200());
        get("/api/users?page=2")
                .then().
                assertThat()
                .body(matchesJsonSchemaInClasspath("json_scheme/event_0.json"));
    }

    @Test
    public void validateUsersFieldsNotEmpty() {
        Specification.setSpecification(Specification.requestSpec("https://reqres.in"), Specification.responseSpecOK200());
        List<UsersData> users = given()
                .when()
                .get("api/users?page=2")
                .then()
                .extract()
                .body().jsonPath().getList("data", UsersData.class);
        for (int i = 0; i < users.size(); i++) {
            Assertions.assertThat(users.get(i).getId().toString()).isNotEmpty();
            Assertions.assertThat(users.get(i).getAvatar().toString()).isNotEmpty();
            Assertions.assertThat(users.get(i).getEmail().toString()).isNotEmpty();
            Assertions.assertThat(users.get(i).getFirst_name().toString()).isNotEmpty();
            Assertions.assertThat(users.get(i).getLast_name().toString()).isNotEmpty();


        }

    }

    @Test
    public void validateDataResultNotEmpty() {
        Specification.setSpecification(Specification.requestSpec("https://reqres.in"), Specification.responseSpecOK200());
        given()
                .when()
                .get("api/users?page=2")
                .then()
                .assertThat()
                .body("per_page", not(blankOrNullString()),
                        "per_page", not(blankOrNullString()),
                        "total", not(blankOrNullString()),
                        "total_pages", not(blankOrNullString())
                );


    }

}