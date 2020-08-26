import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Football {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;

    @Before
    public void setUp(){

        RestAssured.baseURI = "https://api.football-data.org";
        RestAssured.basePath = "/v2/teams/12";
        requestSpecification = new RequestSpecBuilder().setAccept(ContentType.JSON).build();
        responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();

        response = given().spec(requestSpecification).header("X-Auth-Token", "e051043b86624518a57a263f9388d198")
                .when().get().then().spec(responseSpecification).extract().response();


    }

    @Test
    public void verify403(){

        responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(403).build();

    }

    @Test
    public void getReq(){

        JsonPath jsonPath = response.jsonPath();
        List<Map<String,Object>> squads = jsonPath.getList("squad");
        Assert.assertTrue("Squad less than 20",squads.size()>20);
        System.out.println(squads.size());


    }


}
