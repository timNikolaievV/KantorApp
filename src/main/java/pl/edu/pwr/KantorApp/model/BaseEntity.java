package pl.edu.pwr.KantorApp.model;

import java.time.LocalDateTime;
import java.util.UUID;

//To jest klassa od której dziedzici User i Trade
public class BaseEntity {
	protected UUID id;
	protected LocalDateTime createdDate;

//Używamy tego konstruktopa dla tworzenia nowego objekta
	public BaseEntity() {
		this.id = UUID.randomUUID();
		this.createdDate = LocalDateTime.now();
	}

//(Desirilize)
	/**
	 * @param id
	 * @param createdDate
	 */
	public BaseEntity(UUID id, LocalDateTime createdDate) {
		super();
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
