package com.shehabsalah.securespringstarter.base;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * The `{@code BaseEntityAudit}` class extends the `{@link BaseEntity}` class and includes
 * auditing fields for tracking the creation and last modification dates of entities.
 *
 * <p>
 * This class is annotated with `{@code @MappedSuperclass}` to indicate that it is a base class
 * for JPA entities and should not be mapped to its own database table. Instead, it provides
 * common fields that can be inherited by other entities.
 *
 * <p>
 * The `{@code BaseEntityAudit}` class introduces two additional fields, `createdAt` and `updatedAt`,
 * which are automatically populated by the Spring Data JPA auditing mechanisms. The `createdAt`
 * field records the date and time when an entity is initially created, while the `updatedAt`
 * field records the date and time of the most recent modification.
 *
 * <p>
 * By extending the `{@link BaseEntity}` class and including these auditing fields, entities that inherit
 * from `{@code BaseEntityAudit}` gain built-in support for auditing and can track when records were
 * created and updated.
 *
 * <p>
 * The class includes getter and setter methods for the `createdAt` and `updatedAt` fields,
 * making it easy to access and manipulate these values when working with entities.
 *
 * @see BaseEntity
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityAudit extends BaseEntity {
    @CreatedDate
    protected LocalDateTime createdAt;
    @LastModifiedDate
    protected LocalDateTime updatedAt;
}
