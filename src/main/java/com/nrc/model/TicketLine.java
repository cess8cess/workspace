package com.nrc.model;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ticketLines")
@SequenceGenerator(name = "ticketLinesSeq")
public class TicketLine {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	@JsonIgnore
	private long id;

	private int[] numbers;

	@JsonIgnore
	private int result;

	public TicketLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TicketLine(int[] numbers) {
		super();
		this.numbers = numbers;
	}

	public TicketLine(IntSupplier is) {
		this.numbers = IntStream.generate(is).limit(3).toArray();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

}
