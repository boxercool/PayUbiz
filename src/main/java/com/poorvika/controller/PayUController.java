package com.poorvika.controller;

import java.util.Map;

import javax.inject.Inject;

import com.poorvika.client.HashAlgorithm;
import com.poorvika.client.PayUHTTTPClient;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.http.hateoas.JsonError;
import io.reactivex.Maybe;


@Controller("/welcome")
public class PayUController {
	@Value("${payUbiz.key}")
	private String key;
	@Value("${payUbiz.Salt}")
	private String Salt;
    @Value("${payUbiz.key}")
	private String Salt22;

	@Inject
	private HashAlgorithm hashAlgorithm;
	@Inject
    private PayUHTTTPClient payUHTTTPClient;

	@Inject
	private Environment environment;

	@Get("/home")
	public String app(){
		return "Welcome Home";
	}

	@Get("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String index() {
		//String message = environment.getProperty("payUbiz.Salt", String.class).get();
		return Salt22;
	}

	@Post
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Maybe<String> payUHome(@Body Map<String, String> requestBody) {
		String Hashsequence = key + "|" + requestBody.get("txnid") + "|" + requestBody.get("amount") + "|"
				+ requestBody.get("productinfo") + "|" + requestBody.get("firstname") + "|" + requestBody.get("email")
				+ "|" + requestBody.get("phone") + "|" + requestBody.get("surl") + "|" +requestBody.get("furl") +"|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + Salt;

		
		//String Hash="Welcome";
		String Hash = hashAlgorithm.hashCal2(Hashsequence);
		return this.payUHTTTPClient.getHash(requestBody.get("txnid"), requestBody.get("amount"),requestBody.get("productinfo"), requestBody.get("firstname"), requestBody.get("email"),requestBody.get("phone"),requestBody.get("surl"),requestBody.get("furl"),Hash);

		// Hash calculation
		// Hash =
		// sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||beneficiarydetail|SALT)

		// return HttpResponse.ok(Hashsequence);
	}

	@Error(exception = HttpClientException.class)
	public HttpResponse<?> onHTTPClientFailed(HttpRequest request, HttpClientException ex) {
		JsonError error = new JsonError(ex.getMessage());
		return HttpResponse.<JsonError>serverError().body(error);
	}

	@Error(status = HttpStatus.NOT_FOUND)
	public HttpResponse<JsonError> notFound(HttpRequest request) {
		JsonError error = new JsonError("Resource Not Found");
		return HttpResponse.<JsonError>notFound().body(error);
	}
	/*
	 * @Post(value = "/value") public String home(@PathVariable("key")String
	 * key,@PathVariable("txnid")String txnid,
	 * 
	 * @PathVariable("amount")String amount,@PathVariable("productinfo")String
	 * productinfo ,
	 * 
	 * @PathVariable("firstname")String firstname) { System.out.println(key+""+txnid
	 * +""+amount+""+productinfo); String Store = key+""+txnid
	 * +""+amount+""+productinfo; return Store; }
	 * 
	 * @Get() public String message(){ return "Welcome to Micronaut Framework 6"; }
	 */


}