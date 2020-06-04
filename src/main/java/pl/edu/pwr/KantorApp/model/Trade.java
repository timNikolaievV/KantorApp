package pl.edu.pwr.KantorApp.model;

import pl.edu.pwr.KantorApp.utils.HttpConnection;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Trade extends BaseEntity {
	protected String currency1;//decrease amount of balance
	protected String currency2;//increase amount of balance
	protected LocalDateTime tradeDate;
	protected double amount1;
	protected double rate;

	public Trade(String currency1, String currency2, LocalDateTime tradeDate, double amount1, double rate) {
		super();
		this.currency1 = currency1;
		this.currency2 = currency2;
		this.tradeDate = tradeDate;
		this.amount1 = amount1;
		this.rate = rate;
	}

	public Trade(UUID id, LocalDateTime createdDate, String currency1, String currency2, LocalDateTime tradeDate,
			double amount1, double rate) {
		super(id, createdDate);
		this.currency1 = currency1;
		this.currency2 = currency2;
		this.tradeDate = tradeDate;
		this.amount1 = amount1;
		this.rate = rate;
	}

	public Trade() {
		super();
	}

	/**
	 * @return the currency1
	 */
	public String getCurrency1() {
		return currency1;
	}

	/**
	 * @param currency1 the currency1 to set
	 */
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	/**
	 * @return the currency2
	 */
	public String getCurrency2() {
		return currency2;
	}

	/**
	 * @param currency2 the currency2 to set
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	/**
	 * @return the tradeDate
	 */
	public LocalDateTime getTradeDate() {
		return tradeDate;
	}

	/**
	 * @param tradeDate the tradeDate to set
	 */
	public void setTradeDate(LocalDateTime tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @return the amount1
	 */
	public double getAmount1() {
		return amount1;
	}

	/**
	 * @param amount1 the amount1 to set
	 */
	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return the amount2
	 */
	@JsonIgnore
	public double getAmount2() {

		return this.amount1 * this.rate;
	}
//tu jest przeciążenie
	@Override
	public String toString() {
		return "Trade [currency1=" + currency1 + ", currency2=" + currency2 + ", tradeDate=" + tradeDate + ", amount1="
				+ amount1 + ", rate=" + rate + "]";
	}

}