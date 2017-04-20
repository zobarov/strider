package com.gav.quiz.strider.dto;

/**
 * @author alex.gera
 */
public class StridesResponseVO {
	private Integer minimumStridesAmount;
	private String comment;

	public Integer getMinimumStridesAmount() {
		return minimumStridesAmount;
	}
	public void setMinimumStridesAmount(Integer minimumStridesAmount) {
		this.minimumStridesAmount = minimumStridesAmount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
