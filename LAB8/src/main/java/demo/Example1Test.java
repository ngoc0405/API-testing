package demo;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class Example1Test {
	private Response response; // lưu response của API
	private ResponseBody<?> resBody; //body của response
	private JsonPath bodyJson; //body của response đã được convert sang JSON
	int userId;
	
	@BeforeClass
	public void init() {
		userId = 2;
		RestAssured.baseURI = "https://reqres.in/";
		RestAssured.basePath = "api/users";
		
		RequestSpecification req = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.pathParam("userId", userId);
		
		response = req.get("/{userId}");
		resBody = response.getBody();
		bodyJson = resBody.jsonPath();
		System.out.println(response.asPrettyString());
	}
	
	@Test
	public void T01_StatusCodeTest() {
		//Kiểm tra HTTP status có = 200 hay không
		System.out.println(response.getStatusCode());
		assertEquals(200, response.getStatusCode(), "Status Check Failed!");
	}
	@Test
	public void T02_IdChecked() {
	//Kiểm tra phản hồi có chưa trường id hay không
		assertTrue(resBody.asString().contains("id"),"id field check Failed!");
	}
	@Test
	public void T03_EmailChecked() {
		//Kiểm tra phản hồi có chưa trường email hay không
		assertTrue(resBody.asString().contains("email"),"email field check Failed!");
	}
	@Test
	public void T04_FirstNameChecked() {
		//Kiểm tra phản hồi có chưa trường firstname hay không
		assertTrue(resBody.asString().contains("first_name"),"firstname field check Failed!");
	}
	@Test
	public void T05_LastNameChecked() {
		//Kiểm tra phản hồi có chưa trường lastname hay không
		assertTrue(resBody.asString().contains("last_name"),"lastname field check Failed!");
	}
	@Test
	public void T06_AvatarChecked() {
		//Kiểm tra phản hồi có chưa trường avatar hay không
		assertTrue(resBody.asString().contains("avatar"),"avatar field check Failed!");
	}
	@Test
	public void T07_verifyOnMatchingUserId() {
		int id = bodyJson.getInt("data.id");
		assertEquals(userId, id);
	}
	
//	@AfterClass
//	public void afterTest() {
//		RestAssured.baseURI = null;
//		RestAssured.basePath = null;
//	}
}