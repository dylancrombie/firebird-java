package com.oasis.firebird.communication;

import java.io.Serializable;
import java.util.Map;

@Deprecated
public interface CommunicationInterface {
	
	public static String EMAIL = "Email";
	public static String PASSWORD = "Password";
	public static String AUTHORIZATION = "AUTHORIZATION";
	public static String TOKEN = "X-Auth-Token";
	public static String APPLICATION = "X-Application";
	public static String VERSION = "versionCode";
	public static String TENANT = "X-Tenant";

	void setupConfigurationSettings(String ip);
	
	<T> T put(Serializable webRequest, Class<T> clazz, String... paths) throws ClientException;
	<T> T delete(Class<T> clazz, String ... paths) throws ClientException;
	<T> T post(Serializable webRequest, Class<T> clazz, String ... paths) throws ClientException;
	<T> T get(Map<String, String> parameters, Class<T> clazz, String ... paths) throws ClientException;
	<T> T get(Class<T> clazz, String ... paths) throws ClientException;
	
	<T> T putRequest(Serializable webRequest, Class<T> clazz, Map<String, String> headers, String... paths) throws ClientException;
	<T> T deleteRequest(Class<T> clazz, Map<String, String> headers, String ... paths) throws ClientException;
	<T> T postRequest(Serializable webRequest, Class<T> clazz, Map<String, String> headers, String ... paths) throws ClientException;
	<T> T getRequest(Map<String, String> parameters, Class<T> clazz, Map<String, String> headers, String ... paths) throws ClientException;
	<T> T getRequest(Class<T> clazz, Map<String, String> headers, String ... paths) throws ClientException;
	
	<T> T getEntityFromString(Class<T> clazz, String value) throws ClientException;
	
}
