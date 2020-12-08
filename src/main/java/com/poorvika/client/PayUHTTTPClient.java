package com.poorvika.client;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Singleton
public class PayUHTTTPClient {
	@Client("${payUbiz.url}")
	@Inject
	private RxHttpClient httpClient;
	@Value("${payUbiz.key}")
	private String key;
	@Value("${payUbiz.Salt}")
	private String Salt;
	@Value("${payUbiz.paymentPath}")
	private String paymentPath;

	public Maybe<String> getHash(String txnid, String amount, String productinfo, String firstname, String email,
			String phone, String surl, String furl, String Hash) {
		try {
			Map<String, String> data = new LinkedHashMap<>();
			data.put("key", key);
			data.put("hash", Hash);
			data.put("txnid", txnid);
			data.put("amount", amount);
			data.put("firstname", firstname);
			data.put("email", email);
			data.put("phone", phone);
			data.put("productinfo", productinfo);
			data.put("surl", surl);
			data.put("furl", furl);

			HttpRequest<?> req = HttpRequest.POST(new URI(paymentPath), data).contentType(MediaType.APPLICATION_FORM_URLENCODED);
			Flowable flowable = httpClient.retrieve(req, String.class);
			return (Maybe<String>) flowable.firstElement();
		} catch (Exception ex) {
			return Maybe.just("ERROR:" + ex.getMessage());
		}
	}

}
