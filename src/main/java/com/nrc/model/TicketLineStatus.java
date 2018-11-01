package com.nrc.model;

public class TicketLineStatus {
	private int[] numbers;
	private int result;

	public TicketLineStatus(TicketLine line) {
		super();
		this.numbers = line.getNumbers();
		this.result = line.getResult();
	}

	public TicketLineStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
