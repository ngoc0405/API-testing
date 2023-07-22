package demo;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class Example2Test {
	private Response response; // lưu response của API
	private ResponseBody<?> resBody; //body của response
	private JsonPath bodyJson; //body của response đã được convert sang JSON
	int userId;
	
	@BeforeClass
	public void init() {
		userId = 2;
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "api/users";
		
		RequestSpecification req = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.pathParam("userId", userId);
		
		response = req.get("/{userId}");
		resBody = response.getBody();
		bodyJson = resBody.jsonPath();
	}
	
	@Test
	public void T01_StatusCodeTest() {
		//Kiểm tra HTTP status có = 404 hay không
		assertEquals(404, response.getStatusCode(), "Status Check Failed!");
	}
	@Test
	public void T03_MessageChecked() {
		//Kiểm tra phản hồi có chưa message hay không
		assertEquals(true, resBody.asString().contains("message"), "Message field check Failed!");
	}
	@Test
	public void T03_verifyOnMessageContainName() {
		//Kiểm tra nội dung message có phải là "User not found!" ?
		String expectMessage = "User not found!";
		String actualMessage = bodyJson.get("message");
		assertEquals(expectMessage, actualMessage);
	}
	
	@AfterClass
	public void afterTest() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
}
