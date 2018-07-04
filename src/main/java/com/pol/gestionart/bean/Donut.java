package com.pol.gestionart.bean;

public class Donut {
	private String label;
	private int value;
	public Donut() {
		super();
	}
	public Donut(String label, int value) {
		super();
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Donut [label=" + label + ", value=" + value + "]";
	}
	
	

}
