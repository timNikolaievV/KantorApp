package pl.edu.pwr.KantorApp.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntity {
	protected UUID id;
	protected LocalDateTime createdDate;

	public BaseEntity() {
		this.id = UUID.randomUUID();
		this.createdDate = LocalDateTime.now();
	}

	public BaseEntity(UUID id, LocalDateTime createdDate) {
		this.id = id;
		this.createdDate = createdDate;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

}
