package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private String title;
    private String annotation;
    private String description;
    @CreationTimestamp
    private LocalDateTime created;
    private LocalDateTime published;
    private LocalDateTime eventDate;
    private Float lat;
    private Float lon;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @Enumerated(value = EnumType.STRING)
    private State state;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        Event event = (Event) obj;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
