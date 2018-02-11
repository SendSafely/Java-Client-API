package com.sendsafely.enums;

/*
 * @description Enum providing two letter character country codes for convenience when using the SendSafely.addRecipientPhonenumber(String, String, String, CountryCode) method. Use OTHER when passing the full phone number with numeric country code prefix (i.e. +82).   
 */
public enum CountryCode {
	US, GB, AU, AT, BE, BR, CN, DK, FI, FR, DE, IN, IQ, IE, IT, JO, KW, LY, NL, NZ, OM, PL, PT, QA, RU, SA, ES, SE, CH, AE, OTHER
}
