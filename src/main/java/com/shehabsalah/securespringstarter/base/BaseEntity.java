package com.shehabsalah.securespringstarter.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The `BaseEntity` class serves as the foundation for all entities in the application.
 * It is a mapped superclass that defines common fields and behavior shared by entities,
 * such as the unique identifier (ID) for each entity.
 *
 * <p>
 * This class includes the `id` field, which is automatically generated and serves as the
 * primary key for database records. By using the `@MappedSuperclass` annotation, this class
 * is not mapped to its own database table but provides common fields that can be inherited
 * by other entity classes.
 *
 * <p>
 * The `BaseEntity` class includes getter and setter methods for the `id` field and constructors
 * for creating instances of subclasses.
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
